package rss.controller

import rss.data.LocalTechBlogDataSource
import rss.domain.RssReader
import rss.view.RssView

class RssController(
    private val rssView: RssView,
    private val rssReader: RssReader,
) {
    fun startRssReader(postLimit: Int) {
        val keyWord = rssView.readKeyword()
        val blogs = LocalTechBlogDataSource.value
        val posts = rssReader.keyWordFilteredLatestPosts(blogs, keyWord, postLimit)
        rssView.showPost(posts)
    }
}
