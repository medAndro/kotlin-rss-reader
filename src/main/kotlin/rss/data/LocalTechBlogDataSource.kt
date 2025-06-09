package rss.data

import rss.domain.TechBlog

object LocalTechBlogDataSource : TechBlogDataSource {
    override val value =
        listOf(
            TechBlog("카카오", "https://tech.kakao.com/feed/"),
            TechBlog("우아한형제들", "https://techblog.woowahan.com/feed/"),
            TechBlog("네이버D2", "https://d2.naver.com/d2.atom"),
            TechBlog("라인", "https://engineering.linecorp.com/ko/feed/index.html"),
            TechBlog("토스", "https://toss.tech/rss.xml"),
            TechBlog("컬리", "https://helloworld.kurly.com/feed.xml"),
            TechBlog("뱅크샐러드", "https://blog.banksalad.com/rss.xml"),
            TechBlog("넷마블", "https://netmarble.engineering/feed/"),
            TechBlog("NHN클라우드", "https://meetup.toast.com/rss"),
            TechBlog("카카오엔터프라이즈", "https://tech.kakaoenterprise.com/feed"),
        )
}
