package rss.view

import org.w3c.dom.Element
import rss.util.textOf
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ConsoleView : RssView {
    override fun readKeyword(): String? {
        println("검색어를 입력하세요 (없으면 전체 출력):")
        val keyword: String? = readlnOrNull()
        return keyword
    }

    override fun showPost(posts: List<Element>) {
        posts.forEachIndexed { index: Int, element: Element ->
            val title: String = element.textOf("title")
            val link: String = element.textOf("link")
            val publishedDate: LocalDateTime =
                LocalDateTime.parse(element.textOf("pubDate"), DateTimeFormatter.RFC_1123_DATE_TIME)

            println("[${index + 1}] $title (${publishedDate.toLocalDate()}) - $link")
        }
    }
}
