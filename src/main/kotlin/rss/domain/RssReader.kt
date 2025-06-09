package rss.domain

import kotlinx.coroutines.runBlocking
import rss.data.PostRepository

class RssReader(
    private val postRepository: PostRepository,
    private val useCoroutine: Boolean = true,
) {
    fun keyWordFilteredLatestPosts(
        techBlogs: List<TechBlog>,
        keyWord: String?,
        limit: Int,
    ): List<Post> =
        when {
            keyWord == null -> getAllLatestPosts(techBlogs).sortAndLimitedPost(limit)
            else -> getFilteredAllLatestPosts(techBlogs, keyWord).sortAndLimitedPost(limit)
        }

    private fun getFilteredAllLatestPosts(
        techBlogs: List<TechBlog>,
        keyWord: String,
    ): List<Post> {
        val nonFilteredPosts = getAllLatestPosts(techBlogs)
        val filteredPost =
            nonFilteredPosts.mapNotNull { post ->
                when (keyWord in post.postTitle) {
                    true -> post
                    false -> null
                }
            }
        return filteredPost
    }

    private fun getAllLatestPosts(techBlogs: List<TechBlog>): List<Post> =
        when (useCoroutine) {
            true ->
                runBlocking {
                    postRepository.fetchLatestPosts(techBlogs)
                }

            false -> postRepository.fetchLatestPostsSequential(techBlogs)
        }

    private fun List<Post>.sortAndLimitedPost(limit: Int): List<Post> = sortedByDescending { post -> post.publicationDate }.take(limit)
}
