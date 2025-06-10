package rss.controller

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import rss.domain.Post
import rss.domain.RssReader
import rss.domain.SubscribeService
import rss.view.RssView

class RssController(
    private val rssView: RssView,
    private val rssReader: RssReader,
    private val subscribeService: SubscribeService,
) {
    fun startRssReader(postLimit: Int) {
        runBlocking {
            launch(Dispatchers.IO) {
                readRssLoop(postLimit)
            }
            launch(Dispatchers.IO) {
                subscribePost()
            }
        }
    }

    private suspend fun readRssLoop(postLimit: Int) {
        while (true) {
            readRss(postLimit)
        }
    }

    private suspend fun readRss(postLimit: Int) {
        val keyWord = rssView.readKeyword()

        val posts = rssReader.keyWordFilteredLatestPosts(keyWord, postLimit)
        rssView.showPost(posts)
    }

    private suspend fun subscribePost() {
        subscribeService.startRssPostSubscribe(::processNewPosts)
    }

    private fun processNewPosts(newPosts: Collection<Post>) {
        rssView.showNewPostAlert(newPosts.toList())
    }
}
