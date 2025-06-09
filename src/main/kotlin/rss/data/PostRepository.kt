package rss.data

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import rss.domain.TechBlog
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

class PostRepository {
    fun fetchLatestPosts(techBlogs: List<TechBlog>): List<Element> {
        val builder: DocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()

        return techBlogs.flatMap { techBlog: TechBlog -> builder.getElements(techBlog.url) }
    }

    private fun DocumentBuilder.getElements(url: String): List<Element> {
        val document: Document = parse(url)
        val channel: Node = document.getElementsByTagName("channel").item(0)

        val items: List<Element> =
            List(channel.childNodes.length) { channel.childNodes.item(it) }
                .filterIsInstance<Element>()
                .filter { it.tagName == "item" }

        return items
    }
}
