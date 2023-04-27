package github

import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.httpGet
import github.exceptions.InvalidPrUrlException
import github.exceptions.NonexistentPRException
import github.exceptions.InvalidCommitUrlException
import github.exceptions.UsageLimitException
import input.Settings
import org.antlr.v4.runtime.CharStreams
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import parsing.ParsedFile
import java.net.URI

// for now its like this until a proper error handling system is in place
@SuppressWarnings("RethrowCaughtException", "ThrowsCount", "MagicNumber")
sealed class GithubUtil {

    companion object {

        /**
         * Extract data from a pull request url
         *
         * @param url the pr url
         * @return an instance of PullRequestData
         */
        fun getPullRequestInfo(url: String): GhPullRequestData {

            val (userName, repoName, number) = parseUrl(url)
            val repo = GhRepo(userName, repoName)

            // create request
            val request = "http://api.github.com/repos/${repo.user}/${repo.name}/pulls/$number".httpGet()

            // add auth header if provided
            if (Settings.TOKEN != null) request.appendHeader("authorization" to "token ${Settings.TOKEN}")

            // send request
            val (_, response, result) = request.responseString()

            if (response.statusCode == 403) throw UsageLimitException()
            if (!response.isSuccessful) throw NonexistentPRException(url)

            val json = JSONParser().parse(result.component1()) as JSONObject

            var toCommit = createCommitDescriptor(json["head"] as JSONObject, repo)
            if (!validateCommitDescriptor(toCommit)){
                toCommit = createCommitDescriptor(json["head"] as JSONObject, repo, true)
            }
            val fromCommit = createCommitDescriptor(json["base"] as JSONObject, repo)

            // download diff file
            val diffFile = try {
                downloadDiffFile(url)
            } catch (e: NonexistentPRException) {
                throw e
            }

            return GhPullRequestData(toCommit, fromCommit, repo, number, diffFile)
        }

        /**
         * Download the diff file for a pull request
         *
         * @param url the PR url
         * @return the diff file text for the pr
         */
        @Throws(NonexistentPRException::class)
        fun downloadDiffFile(url: String): String {

            // download diff file
            val (_, response, result) = "$url.diff".httpGet().responseString()

            if (response.isSuccessful) {
                return result.component1()!!
            } else throw NonexistentPRException(url)
        }

        /**
         * Create a github commit descriptor from the json of head/base
         *
         * @param json the json object, head or base
         * @param repo the name of the repository this PR belongs to
         * @param useBaseRepo enforce the use of main repo for the base commit
         * @return a pair containing the target repo and target branch
         */
        private fun createCommitDescriptor(json: JSONObject, repo: GhRepo, useBaseRepo: Boolean = false): GhCommitData {

            val label = json["label"].toString()
            val sha = json["sha"].toString()

            return if (label.contains(":") && !useBaseRepo) {
                // if the ":" is present the parent repo is different
                val split = label.split(":")
                GhCommitData(GhRepo(split[0], repo.name), sha)
            } else {
                // if the ":" is not present the parent repo is the one that the PR belongs to
                GhCommitData(repo, sha)
            }
        }
        /**
         * validate a github commit descriptor to see if it exists
         *
         * @param commit the GhCommitData object
         * @return boolean that indicates whether it is valid
         */
        private fun validateCommitDescriptor(commit:GhCommitData): Boolean {
            val url = "http://raw.githubusercontent.com/${commit.repo.user}/${commit.repo.name}/${commit.sha}"
            val (_, response, _) = url.httpGet().responseString()
            return response.isSuccessful
        }

        /**
         * Check if the PR url meets the expected format
         *
         * @param url the PR url
         * @return the user, repo name and pr number for the pr url
         */
        @Throws(InvalidPrUrlException::class)
        fun parseUrl(url: String): Triple<String, String, Int> {

            // parse the url path
            val path = URI(url).path.split("/")

            // if PR does not meet certain requirements throw an exception
            val correctLength = path.size == 5
            val containsPull = path[3] == "pull"
            val containsNum = path[4].matches(Regex("[0-9]+"))
            if (!correctLength || !containsPull || !containsNum) throw InvalidPrUrlException(url)

            val repoName = path[2]
            val userName = path[1]
            val number = path[4].toInt()

            return Triple(userName, repoName, number)
        }

        /**
         * Download a file from github
         *
         * @param commit the commit descriptor
         * @param filepath the file path
         * @return a ParsedFile for the specified file, null if not found
         */
        fun downloadAndParseFile(commit: GhCommitData, filepath: String): ParsedFile? {

            val url = "http://raw.githubusercontent.com/${commit.repo.user}/${commit.repo.name}/${commit.sha}/$filepath"
            val (_, response, result) = url.httpGet().responseString()
            return if (response.isSuccessful) {
                val charStream = CharStreams.fromString(result.component1()!!)
                val parsedFile = ParsedFile(charStream)
                parsedFile.name = filepath
                parsedFile
            } else {
                throw InvalidCommitUrlException(url)
            }
        }
    }
}
