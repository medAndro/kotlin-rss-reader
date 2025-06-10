package rss.domain

import rss.data.PostRepository

class RssReader(
    private val techBlogs: List<TechBlog>,
    private val postRepository: PostRepository,
    private val useCoroutine: Boolean = true,
) {
    suspend fun keyWordFilteredLatestPosts(
        keyWord: String?,
        limit: Int = Int.MAX_VALUE,
    ): List<Post> =
        when {
            keyWord == null -> getAllLatestPosts(techBlogs).sortAndLimitedPost(limit)
            else -> getFilteredAllLatestPosts(techBlogs, keyWord).sortAndLimitedPost(limit)
        }

    private suspend fun getFilteredAllLatestPosts(
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

    private suspend fun getAllLatestPosts(techBlogs: List<TechBlog>): List<Post> =
        when (useCoroutine) {
            true -> postRepository.fetchLatestPosts(techBlogs)

            false -> postRepository.fetchLatestPostsSequential(techBlogs)
        }

    private fun List<Post>.sortAndLimitedPost(limit: Int): List<Post> = sortedByDescending { post -> post.publicationDate }.take(limit)
}
