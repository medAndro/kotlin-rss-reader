package rss.data

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import rss.domain.Post
import rss.domain.TechBlog
import rss.util.toPost
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

class PostRepository {
    fun fetchLatestPosts(techBlogs: List<TechBlog>): List<Post> {
        val builder: DocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()

        return techBlogs.flatMap { techBlog: TechBlog ->
            try {
                builder.getPost(techBlog)
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    private fun DocumentBuilder.getPost(techBlog: TechBlog): List<Post> {
        val document: Document = parse(techBlog.url)
        val channelNode: Node = document.getElementsByTagName("channel").item(0) ?: return emptyList()

        val items: List<Element> =
            List(channelNode.childNodes.length) { channelNode.childNodes.item(it) }
                .filterIsInstance<Element>()
                .filter { it.tagName == "item" }

        return items.map { it.toPost(techBlog.blogName) }
    }
}
