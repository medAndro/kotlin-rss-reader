package rss.data

import rss.domain.TechBlog

interface TechBlogDataSource {
    val value: List<TechBlog>
}
