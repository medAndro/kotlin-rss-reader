package rss.data

import rss.domain.TechBlog

object LocalTechBlogDataSource : TechBlogDataSource {
    override val value =
        listOf(
            TechBlog("카카오", "https://tech.kakao.com/feed/"),
            TechBlog("정석준님의 블로그", "https://dino-dev.tistory.com/rss"),
            TechBlog("서준수님의 블로그", "https://brunch.co.kr/rss/@@2Kn8"),
            TechBlog("우아한형제들", "https://techblog.woowahan.com/feed/"),
            TechBlog("라인", "https://engineering.linecorp.com/ko/feed/index.html"),
            TechBlog("컬리", "https://helloworld.kurly.com/feed.xml"),
            TechBlog("뱅크샐러드", "https://blog.banksalad.com/rss.xml"),
            TechBlog("넷마블", "https://netmarble.engineering/feed/"),
            TechBlog("NHN클라우드", "https://meetup.toast.com/rss"),
            TechBlog("카카오엔터프라이즈", "https://tech.kakaoenterprise.com/feed"),
        )
}
