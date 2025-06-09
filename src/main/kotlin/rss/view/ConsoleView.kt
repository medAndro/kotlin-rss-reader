package rss.view

import rss.domain.Post

class ConsoleView : RssView {
    override fun readKeyword(): String? {
        println("검색어를 입력하세요 (없으면 전체 출력):")
        val keyword: String? = readlnOrNull()
        return keyword
    }

    override fun showPost(posts: List<Post>) {
        posts.forEachIndexed { index: Int, post: Post ->
            println("[${index + 1}] ${post.postTitle} (${post.publicationDate.toLocalDate()}) - ${post.blogName}(${post.url})")
        }
    }
}
