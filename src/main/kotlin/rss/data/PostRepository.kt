package rss.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import rss.domain.Post
import rss.domain.TechBlog
import rss.util.toPost
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

class PostRepository(
    private val withLog: Boolean = false,
) {
    private val timeFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())

    suspend fun fetchLatestPosts(techBlogs: List<TechBlog>): List<Post> =
        coroutineScope {
            log("🚀 Starting ${techBlogs.size} RSS feeds (Coroutine)")
            val startTime = System.currentTimeMillis()

            val results =
                techBlogs
                    .map { techBlog ->
                        async(Dispatchers.IO) {
                            fetchSingleBlog(techBlog, "[CO]")
                        }
                    }.awaitAll()
                    .flatten()

            val duration = System.currentTimeMillis() - startTime
            log("🏁 Completed in ${duration}ms - ${results.size} posts (Coroutine)")
            results
        }

    fun fetchLatestPostsSequential(techBlogs: List<TechBlog>): List<Post> {
        log("🔄 Starting ${techBlogs.size} RSS feeds (Sequential)")
        val startTime = System.currentTimeMillis()
        val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()

        val results =
            techBlogs.flatMap { techBlog ->
                fetchSingleBlogSync(techBlog, builder, "[SEQ]")
            }

        val duration = System.currentTimeMillis() - startTime
        log("🏁 Completed in ${duration}ms - ${results.size} posts (Sequential)")
        return results
    }

    private suspend fun fetchSingleBlog(
        techBlog: TechBlog,
        prefix: String,
    ): List<Post> {
        val startTime = System.currentTimeMillis()
        log("$prefix ${techBlog.blogName} - Start")

        return try {
            val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            val posts =
                withContext(Dispatchers.IO) {
                    builder.parseRss(techBlog)
                }

            val duration = System.currentTimeMillis() - startTime
            log("$prefix ${techBlog.blogName} - ✅ ${duration}ms (${posts.size} posts)")
            posts
        } catch (e: Exception) {
            val duration = System.currentTimeMillis() - startTime
            log("$prefix ${techBlog.blogName} - ❌ ${duration}ms: ${e.message}")
            emptyList()
        }
    }

    private fun fetchSingleBlogSync(
        techBlog: TechBlog,
        builder: DocumentBuilder,
        prefix: String,
    ): List<Post> {
        val startTime = System.currentTimeMillis()
        log("$prefix ${techBlog.blogName} - Start")

        return try {
            val posts = builder.parseRss(techBlog)
            val duration = System.currentTimeMillis() - startTime
            log("$prefix ${techBlog.blogName} - ✅ ${duration}ms (${posts.size} posts)")
            posts
        } catch (e: Exception) {
            val duration = System.currentTimeMillis() - startTime
            log("$prefix ${techBlog.blogName} - ❌ ${duration}ms: ${e.message}")
            emptyList()
        }
    }

    private fun DocumentBuilder.parseRss(techBlog: TechBlog): List<Post> {
        val document: Document = parse(techBlog.url)
        val channelNode: Node = document.getElementsByTagName("channel").item(0) ?: return emptyList()

        val items: List<Element> =
            List(channelNode.childNodes.length) {
                channelNode.childNodes.item(it)
            }.filterIsInstance<Element>()
                .filter { it.tagName == "item" }

        return items.map { it.toPost(techBlog.blogName) }
    }

    private fun log(message: String) {
        if (withLog) {
            println("[${timeFormat.format(Date())}] $message")
        }
    }
}
