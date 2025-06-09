package rss.util

import org.w3c.dom.Element
import rss.domain.Post
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Element.textOf(tagName: String): String = getElementsByTagName(tagName).item(0)?.textContent.orEmpty()

fun Element.toPost(blogName: String): Post =
    Post(
        blogName = blogName,
        postTitle = this.textOf("title"),
        publicationDate = LocalDateTime.parse(this.textOf("pubDate"), DateTimeFormatter.RFC_1123_DATE_TIME),
        url = this.textOf("link"),
    )
