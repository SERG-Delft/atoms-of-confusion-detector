package github

import github.exceptions.InvalidPrUrlException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

internal class GithubUtilTest {

    @Test
    fun testUrlParsing() {
        val url = "https://github.com/kittinunf/fuel/pull/811"
        val (user, repo) = GithubUtil.parseUrl(url)
        assertEquals("kittinunf", user)
        assertEquals("fuel", repo)
    }

    @Test
    fun testUrlParsingInvalid() {
        val url = "https://github.com/kittinunf/fuel/pull/811/files"
        assertThrows<InvalidPrUrlException> {
            GithubUtil.parseUrl(url)
        }
    }
}
