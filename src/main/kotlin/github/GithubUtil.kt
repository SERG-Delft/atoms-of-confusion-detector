package github

import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.httpGet
import github.exceptions.InvalidPrHtmlException
import github.exceptions.InvalidPrUrlException
import github.exceptions.NonexistentPRException
import org.antlr.v4.runtime.CharStreams
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import parsing.ParsedFile
import java.net.MalformedURLException
import java.net.SocketTimeoutException
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
        fun getPullRequestInfo(url: String): PullRequestData {

            val (userName, repoName) = parseUrl(url)
            val repo = GhRepo(userName, repoName)

            // download the html of the pr site to get the source and target branch descriptors
            val doc = try {
                Jsoup.connect(url).get()
            } catch (e: MalformedURLException) {
                throw e
            } catch (e: HttpStatusException) {
                throw e
            } catch (e: SocketTimeoutException) {
                throw e
            }

            // get the source and target repos from the html
            val (sourceTxt, targetTxt) = try {
                extractBranchDescriptors(doc)
            } catch (e: InvalidPrHtmlException) {
                throw e
            }

            val source = parseBranchDescriptor(sourceTxt, repo)
            val target = parseBranchDescriptor(targetTxt, repo)

            // download patch file
            val patchFile = try {
                downloadPatchFile(url)
            } catch (e: NonexistentPRException) {
                throw e
            }

            return PullRequestData(source, target, repo, patchFile)
        }

        /**
         * Download the patch file for a pull request
         *
         * @param url the PR url
         * @return the patch file text for the pr
         */
        @Throws(NonexistentPRException::class)
        fun downloadPatchFile(url: String): String {

            // download patch file
            val (_, response, result) = "$url.patch".httpGet().responseString()

            if (response.isSuccessful) {
                return result.component1()!!
            } else throw NonexistentPRException(url)
        }

        /**
         * Read a JSoup document for a github PR and extract the source and target descriptors
         *
         * @param doc the JSoup document for the pr site
         * @return the source and target branch descriptors
         */
        @Throws(InvalidPrHtmlException::class)
        fun extractBranchDescriptors(doc: Document): Pair<String, String> {
            val elements = doc.select(".commit-ref")
            if (elements.size <= 1) throw InvalidPrHtmlException()
            val target = elements[0].text()
            val source = elements[1].text()
            return Pair(source, target)
        }

        /**
         * Parse a github branch descriptor, this is what is displayed
         * at the top of a PR and is of the format "branch", or "repo:branch"
         *
         * @param branchDescriptor the branch descriptor
         * @param repo the name of the repository this PR belongs to
         * @return a pair containing the target repo and target branch
         */
        fun parseBranchDescriptor(branchDescriptor: String, repo: GhRepo): GhBranchDescriptor {
            return if (branchDescriptor.contains(":")) {
                // if the ":" is present the parent repo is different
                val split = branchDescriptor.split(":")
                GhBranchDescriptor(GhRepo(split[0], repo.name), split[1])
            } else {
                // if the ":" is not present the parent repo is the one that the PR belongs to
                GhBranchDescriptor(repo, branchDescriptor)
            }
        }

        /**
         * Check if the PR url meets the expected format
         *
         * @param url the PR url
         * @return the user and reponame in the url
         */
        @Throws(InvalidPrUrlException::class)
        fun parseUrl(url: String): Pair<String, String> {

            // parse the url path
            val path = URI(url).path.split("/")

            // if PR does not meet certain requirements throw an exception
            val correctLength = path.size == 5
            val containsPull = path[3] == "pull"
            val containsNum = path[4].matches(Regex("[0-9]+"))
            if (!correctLength || !containsPull || !containsNum) throw InvalidPrUrlException(url)

            val repoName = path[2]
            val userName = path[1]

            return Pair(userName, repoName)
        }

        /**
         * Get a list of all changed .java files in a patch
         *
         * @param patch the patch file contents
         * @return the file paths of the .java files affected in the patch
         */
        @Suppress("MagicNumber")
        fun getChangedJavaFiles(patch: String): List<String> = patch.split("\n")
            .filter { it.length >= 3 && it.slice(0 until 3) == "+++" } // get lines starting with +++
            .map { it.split(" ")[1] } // get the path, following the plus signs
            .map { it.slice(2 until it.length) } // remove the b/ from each path
            .filter { it.matches(Regex(".*\\.java")) }

        /**
         * Download a file from github
         *
         * @param user
         * @param repo
         * @param branch
         * @param filePath
         * @return a GitFile for the specified file, null if not found
         */
        fun downloadFile(user: String, repo: String, branch: String, filePath: String): ParsedFile? {

            val url = "http://raw.githubusercontent.com/$user/$repo/$branch/$filePath"
            val (_, response, result) = url.httpGet().responseString()
            return if (response.isSuccessful) {
                val charStream = CharStreams.fromString(result.component1()!!)
                val parsedFile = ParsedFile(charStream)
                parsedFile.name = filePath
                parsedFile
            } else {
                null
            }
        }
    }
}
