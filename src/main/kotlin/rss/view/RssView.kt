package rss.view

import rss.domain.Post

interface RssView {
    fun readKeyword(): String?

    fun showPost(posts: List<Post>)
}
