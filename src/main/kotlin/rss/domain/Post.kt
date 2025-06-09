package rss.domain

import java.time.LocalDateTime

data class Post(
    val blogName: String,
    val postTitle: String,
    val publicationDate: LocalDateTime,
    val url: String,
)
