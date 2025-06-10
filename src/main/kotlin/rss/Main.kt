package rss

import rss.controller.RssController
import rss.data.LocalTechBlogDataSource
import rss.data.PostRepository
import rss.domain.RssReader
import rss.domain.SubscribeService
import rss.view.ConsoleView

fun main() {
    val techBlogs = LocalTechBlogDataSource.value
    val rssReader = RssReader(techBlogs, PostRepository(withLog = false), useCoroutine = true)
    val subscribeService = SubscribeService(rssReader, 10)
    val controller = RssController(ConsoleView(), rssReader, subscribeService)

    controller.startRssReader(postLimit = 10)
}
