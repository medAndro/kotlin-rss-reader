package rssReader

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

fun main() {
    while (true) {
        println("검색어를 입력하세요 (없으면 전체 출력):")
        val keyword: String? = readlnOrNull()
        showLatestPosts(keyword)
    }
}

private fun showLatestPosts(keyword: String? = null) {
    val builder: DocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
    val document: Document = builder.parse("https://tech.kakao.com/feed/")
    val channel: Node = document.getElementsByTagName("channel").item(0)

    val items: List<Element> =
        List(channel.childNodes.length) { channel.childNodes.item(it) }
            .filterIsInstance<Element>()
            .filter { it.tagName == "item" }

    items.forEachIndexed { index: Int, element: Element ->
        val title: String = element.textOf("title")
        val link: String = element.textOf("link")
        val publishedDate: LocalDateTime =
            LocalDateTime.parse(element.textOf("pubDate"), DateTimeFormatter.RFC_1123_DATE_TIME)

        println("[${index + 1}] $title (${publishedDate.toLocalDate()}) - $link")
    }
}

private fun Element.textOf(tagName: String): String = getElementsByTagName(tagName).item(0)?.textContent.orEmpty()
