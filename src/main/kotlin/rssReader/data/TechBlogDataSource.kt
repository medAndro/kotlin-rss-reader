package rssReader.data

import rssReader.domain.TechBlog

interface TechBlogDataSource {
    val value: List<TechBlog>
}
