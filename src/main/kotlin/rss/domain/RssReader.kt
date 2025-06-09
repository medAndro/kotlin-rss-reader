package rss.domain

import org.w3c.dom.Element
import rss.data.PostRepository

class RssReader(
    private val postRepository: PostRepository,
) {
    fun keyWordFilteredLatestPosts(
        techBlogs: List<TechBlog>,
        keyWord: String?,
    ): List<Element> = postRepository.fetchLatestPosts(techBlogs)
}
