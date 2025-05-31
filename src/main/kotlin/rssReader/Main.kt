package rssReader

import org.w3c.dom.Element
import rssReader.data.PostRepository
import rssReader.domain.TechBlog
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main() {
    while (true) {
        println("검색어를 입력하세요 (없으면 전체 출력):")
        val keyword: String? = readlnOrNull()
        showLatestPosts()
    }
}

private fun showLatestPosts(
    techBlogs: List<TechBlog>,
    keyword: String? = null,
) {
    val items: List<Element> = PostRepository.fetchLatestPosts(techBlogs)

    items.forEachIndexed { index: Int, element: Element ->
        val title: String = element.textOf("title")
        val link: String = element.textOf("link")
        val publishedDate: LocalDateTime =
            LocalDateTime.parse(element.textOf("pubDate"), DateTimeFormatter.RFC_1123_DATE_TIME)

        println("[${index + 1}] $title (${publishedDate.toLocalDate()}) - $link")
    }
}

private fun Element.textOf(tagName: String): String = getElementsByTagName(tagName).item(0)?.textContent.orEmpty()
