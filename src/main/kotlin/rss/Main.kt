package rss

import rss.controller.RssController
import rss.data.PostRepository
import rss.domain.RssReader
import rss.view.ConsoleView

fun main() {
    val controller = RssController(ConsoleView(), RssReader(PostRepository(withLog = false), useCoroutine = true))
    val postLimit = 10
    controller.startRssReader(postLimit)
}
