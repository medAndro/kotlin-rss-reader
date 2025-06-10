package rss.domain

import kotlinx.coroutines.delay
import java.time.Duration

class SubscribeService(
    private val rssReader: RssReader,
    private val intervalMinute: Int,
) {
    suspend fun startRssPostSubscribe(onNewPost: (Collection<Post>) -> Unit) {
        var postsCache = rssReader.keyWordFilteredLatestPosts(null).subList(0, CACHE_SIZE)

        while (true) {
            delay(Duration.ofMinutes(intervalMinute.toLong()).toMillis())

            val fetchedPost = rssReader.keyWordFilteredLatestPosts(null).subList(0, CACHE_SIZE)
            val newPosts = fetchedPost.subtract(postsCache.toSet())

            if (newPosts.isNotEmpty()) {
                onNewPost(newPosts)
                postsCache = fetchedPost
            }
        }
    }

    companion object {
        private const val CACHE_SIZE = 100
    }
}
