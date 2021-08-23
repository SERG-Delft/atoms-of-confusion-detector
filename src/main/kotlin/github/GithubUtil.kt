package github

import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.httpGet
import github.exceptions.NonexistentPRException
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.jsoup.Jsoup
import java.net.URI
import kotlin.jvm.Throws

sealed class GithubUtil {
    companion object {

        /**
         * Extract data from a pull request url
         *
         * @param url the pr url
         * @return an instance of PullRequestData
         */
        @Throws(NonexistentPRException::class)
        fun getPullRequestInfo(url: String): PullRequestData {

            val uri = URI(url)
            val path = uri.path.split("/")

            val repo = path[2]

            val doc = Jsoup.connect(url).get()
            val elements = doc.select(".commit-ref")
            val target = elements[0].text().split(":")
            val source = elements[1].text().split(":")

            val (targetRepoUsername, targetBranch) = target[0] to target[1]
            val (sourceRepoUsername, sourceBranch) = source[0] to source[1]

            val (request, response, result) = "$url.patch".httpGet().responseString()

            if (response.isSuccessful) {
                val patch = result.component1()!!

                return PullRequestData(
                    targetRepoUsername,
                    targetBranch,
                    sourceRepoUsername,
                    sourceBranch,
                    repo,
                    patch
                )
            } else throw NonexistentPRException(url)
        }

        /**
         * Get a list of all changed files in a patch
         *
         * @param patch the patch file contents
         * @return the file paths of the changed files
         */
        @Suppress("MagicNumber")
        fun getChangedFiles(patch: String): List<String> {

            return patch.split("\n")
                .filter { it.length >= 3 && it.slice(0 until 3) == "+++" } // get lines starting with +++
                .map { it.split(" ")[1] } // get the path, following the plus signs
                .map { it.slice(2 until it.length) } // remove the b/ from each path
        }

        /**
         * Download a file from github
         *
         * @param user
         * @param repo
         * @param branch
         * @param file
         * @return a charstream for the specified file
         */
        fun downloadFile(user: String, repo: String, branch: String, file: String): CharStream? {

            val url = "http://raw.githubusercontent.com/$user/$repo/$branch/$file"
            val (_, response, result) = url.httpGet().responseString()
            return if (response.isSuccessful) {
                CharStreams.fromString(result.component1()!!)
            } else {
                null
            }
        }
    }
}
