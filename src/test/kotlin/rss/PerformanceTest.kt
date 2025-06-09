package rss

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import rss.data.LocalTechBlogDataSource
import rss.data.PostRepository
import kotlin.system.measureTimeMillis

class PerformanceTest {
    @Test
    fun `코루틴 vs 순차처리 성능 비교`() =
        runTest {
            val techBlogs = LocalTechBlogDataSource.value

            val repository = PostRepository(withLog = true)

            val sequentialTime =
                measureTimeMillis {
                    repository.fetchLatestPostsSequential(techBlogs)
                }

            val coroutineTime =
                measureTimeMillis {
                    repository.fetchLatestPosts(techBlogs)
                }

            println("=== 성능 비교 결과 ===")
            println("순차 처리: ${sequentialTime}ms")
            println("코루틴 처리: ${coroutineTime}ms")
            println("성능 향상: ${sequentialTime.toFloat() / coroutineTime}배")
        }
}
