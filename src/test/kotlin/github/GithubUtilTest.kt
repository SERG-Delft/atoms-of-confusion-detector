package github

import github.exceptions.InvalidPrHtmlException
import github.exceptions.InvalidPrUrlException
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File
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

    @Test
    fun testBranchDescParsingWithColon() {
        val descriptor = "user2:master"
        val parsed = GithubUtil.parseBranchDescriptor(descriptor, GhRepo("user1", "rep"))
        assertEquals(GhRepo("user2", "rep"), parsed.repo)
        assertEquals("master", parsed.branch)
    }

    @Test
    fun testBranchDescParsingNoColon() {
        val descriptor = "master"
        val parsed = GithubUtil.parseBranchDescriptor(descriptor, GhRepo("usr", "rep"))
        assertEquals(GhRepo("usr", "rep"), parsed.repo)
        assertEquals("master", parsed.branch)
    }

    @Test
    fun testExtractBranchDescriptors() {
        val doc = Jsoup.parse(File("./testdata/prs/fuel-811.html"), "utf-8")
        val (sourceTxt, targetTxt) = GithubUtil.extractBranchDescriptors(doc)
        assertEquals("kittinunf:master", targetTxt)
        assertEquals("jmfayard:refreshVersions", sourceTxt)
    }

    @Test
    fun testExtractBranchDescriptorsBadHtml() {
        val doc = Jsoup.parse(File("./testdata/prs/invalid.html"), "utf-8")
        assertThrows<InvalidPrHtmlException> {
            GithubUtil.extractBranchDescriptors(doc)
        }
    }
}
