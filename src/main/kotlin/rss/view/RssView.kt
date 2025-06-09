package rss.view

import org.w3c.dom.Element

interface RssView {
    fun readKeyword(): String?

    fun showPost(posts: List<Element>)
}
