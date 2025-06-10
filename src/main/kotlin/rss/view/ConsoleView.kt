package rss.view

import rss.domain.Post

class ConsoleView : RssView {
    override fun readKeyword(): String? {
        showKeywordInputMessage()
        val keyword: String? = readlnOrNull()
        return keyword
    }

    override fun showPost(posts: List<Post>) {
        posts.forEachIndexed { index: Int, post: Post ->
            println("[${index + 1}] ${post.postTitle} (${post.publicationDate.toLocalDate()}) - ${post.blogName}(${post.url})")
        }
        println()
    }

    override fun showNewPostAlert(newPosts: List<Post>) {
        println("\n새로운 글이 등록되었습니다!")

        newPosts.forEachIndexed { index: Int, post: Post ->
            println("[NEW] ${post.postTitle} (${post.publicationDate.toLocalDate()}) - ${post.blogName}(${post.url})")
        }
        println()
        showKeywordInputMessage()
    }

    private fun showKeywordInputMessage() {
        println("검색어를 입력하세요 (없으면 전체 출력):")
    }
}
