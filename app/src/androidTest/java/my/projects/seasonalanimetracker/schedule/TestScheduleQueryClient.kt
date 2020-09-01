package my.projects.seasonalanimetracker.schedule

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Response
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import my.projects.seasonalanimetracker.SchedulesPageInfoQuery
import my.projects.seasonalanimetracker.fragment.PageInfoFragment
import my.projects.seasonalanimetracker.schedule.domain.ScheduleQueryClient
import my.projects.seasonalanimetracker.schedule.domain.mapper.query.ScheduleQueryToDataMapper
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatcher
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import java.util.*

@RunWith(AndroidJUnit4::class)
class TestScheduleQueryClient {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockWebServer = MockWebServer()
    private lateinit var apolloClient: ApolloClient
    private lateinit var queryClient: ScheduleQueryClient

    private val pagesCount = 2

    @Before
    fun setUp() {
        mockWebServer.start()

        apolloClient = ApolloClient.builder().serverUrl(mockWebServer.url("/")).build()
        queryClient = ScheduleQueryClient(apolloClient, ScheduleQueryToDataMapper())
    }


    @Test
    fun getsPagesCount() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(getPageInfoJsonResponse()))

        val count = queryClient.getPagesCount(Calendar.getInstance(), Calendar.getInstance()).blockingGet()

        assertEquals(count, pagesCount)
    }

    @Test
    fun getsPageData() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(getSinglePageData()))

        val schedules = queryClient.getPage(Calendar.getInstance(), Calendar.getInstance(), 1).blockingGet()

        assertEquals(3, schedules.size)
    }

    private fun getPageInfoJsonResponse(): String {
        return """{
  "data": {
    "Page": {
      "__typename": "Page",
      "pageInfo": {
        "__typename": "PageInfo",
        "total": 89,
        "perPage": 50,
        "currentPage": 1,
        "lastPage": 2,
        "hasNextPage": true
      },
      "airingSchedules": [
        {
          "id": 288727,
          "__typename": "AiringSchedule"
        },
        {
          "id": 288791,
          "__typename": "AiringSchedule"
        },
        {
          "id": 288963,
          "__typename": "AiringSchedule"
        },
        {
          "id": 278490,
          "__typename": "AiringSchedule"
        },
        {
          "id": 286870,
          "__typename": "AiringSchedule"
        },
        {
          "id": 286822,
          "__typename": "AiringSchedule"
        },
        {
          "id": 290369,
          "__typename": "AiringSchedule"
        },
        {
          "id": 290419,
          "__typename": "AiringSchedule"
        },
        {
          "id": 285656,
          "__typename": "AiringSchedule"
        },
        {
          "id": 284134,
          "__typename": "AiringSchedule"
        },
        {
          "id": 288998,
          "__typename": "AiringSchedule"
        },
        {
          "id": 289011,
          "__typename": "AiringSchedule"
        },
        {
          "id": 290872,
          "__typename": "AiringSchedule"
        },
        {
          "id": 289119,
          "__typename": "AiringSchedule"
        },
        {
          "id": 289768,
          "__typename": "AiringSchedule"
        },
        {
          "id": 265032,
          "__typename": "AiringSchedule"
        },
        {
          "id": 288632,
          "__typename": "AiringSchedule"
        },
        {
          "id": 287176,
          "__typename": "AiringSchedule"
        },
        {
          "id": 287446,
          "__typename": "AiringSchedule"
        },
        {
          "id": 287213,
          "__typename": "AiringSchedule"
        },
        {
          "id": 284208,
          "__typename": "AiringSchedule"
        },
        {
          "id": 289852,
          "__typename": "AiringSchedule"
        },
        {
          "id": 284159,
          "__typename": "AiringSchedule"
        },
        {
          "id": 287858,
          "__typename": "AiringSchedule"
        },
        {
          "id": 288762,
          "__typename": "AiringSchedule"
        },
        {
          "id": 289042,
          "__typename": "AiringSchedule"
        },
        {
          "id": 289271,
          "__typename": "AiringSchedule"
        },
        {
          "id": 290958,
          "__typename": "AiringSchedule"
        },
        {
          "id": 289300,
          "__typename": "AiringSchedule"
        },
        {
          "id": 287795,
          "__typename": "AiringSchedule"
        },
        {
          "id": 287708,
          "__typename": "AiringSchedule"
        },
        {
          "id": 290442,
          "__typename": "AiringSchedule"
        },
        {
          "id": 287521,
          "__typename": "AiringSchedule"
        },
        {
          "id": 287684,
          "__typename": "AiringSchedule"
        },
        {
          "id": 288728,
          "__typename": "AiringSchedule"
        },
        {
          "id": 288928,
          "__typename": "AiringSchedule"
        },
        {
          "id": 288964,
          "__typename": "AiringSchedule"
        },
        {
          "id": 289383,
          "__typename": "AiringSchedule"
        },
        {
          "id": 290923,
          "__typename": "AiringSchedule"
        },
        {
          "id": 280972,
          "__typename": "AiringSchedule"
        },
        {
          "id": 278440,
          "__typename": "AiringSchedule"
        },
        {
          "id": 289171,
          "__typename": "AiringSchedule"
        },
        {
          "id": 289281,
          "__typename": "AiringSchedule"
        },
        {
          "id": 290995,
          "__typename": "AiringSchedule"
        },
        {
          "id": 291022,
          "__typename": "AiringSchedule"
        },
        {
          "id": 283902,
          "__typename": "AiringSchedule"
        },
        {
          "id": 283953,
          "__typename": "AiringSchedule"
        },
        {
          "id": 287112,
          "__typename": "AiringSchedule"
        },
        {
          "id": 290636,
          "__typename": "AiringSchedule"
        },
        {
          "id": 286243,
          "__typename": "AiringSchedule"
        }
      ]
    }
  }
}"""
    }

    private fun getSinglePageData(): String {
        return """{
  "data": {
    "Page": {
      "__typename": "Page",
      "pageInfo": {
        "__typename": "PageInfo",
        "total": 3,
        "perPage": 50,
        "currentPage": 1,
        "lastPage": 1,
        "hasNextPage": false
      },
      "airingSchedules": [
        {
          "id": 288727,
          "airingAt": 1596420000,
          "__typename": "AiringSchedule",
          "episode": 35,
          "media": {
            "__typename": "Media",
            "id": 119926,
            "type": "ANIME",
            "format": "ONA",
            "title": {
              "__typename": "MediaTitle",
              "romaji": "Wan Jie Chun Qiu",
              "native": "万界春秋",
              "english": null
            },
            "description": "On his way to Shang Shi province, at the request of his father, a young man will find himself involved in affairs between former clans.<br><br>(Source: Nautiljon, translated)",
            "coverImage": {
              "__typename": "MediaCoverImage",
              "large": "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx119926-jnUUFCoAUglZ.jpg"
            },
            "bannerImage": null,
            "episodes": 56,
            "genres": [
              "Action",
              "Fantasy"
            ],
            "averageScore": null,
            "meanScore": 73,
            "siteUrl": "https://anilist.co/anime/119926",
            "characters": {
              "__typename": "CharacterConnection",
              "edges": []
            },
            "staff": {
              "__typename": "StaffConnection",
              "edges": []
            },
            "studios": {
              "__typename": "StudioConnection",
              "nodes": [
                {
                  "__typename": "Studio",
                  "id": 6475,
                  "name": "Ruo Hong Culture"
                }
              ]
            }
          }
        },
        {
          "id": 288791,
          "airingAt": 1596420000,
          "__typename": "AiringSchedule",
          "episode": 25,
          "media": {
            "__typename": "Media",
            "id": 119924,
            "type": "ANIME",
            "format": "ONA",
            "title": {
              "__typename": "MediaTitle",
              "romaji": "Wu Shang Shen Di",
              "native": "无上神帝",
              "english": null
            },
            "description": null,
            "coverImage": {
              "__typename": "MediaCoverImage",
              "large": "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx119924-mHlNS3HujyKu.jpg"
            },
            "bannerImage": null,
            "episodes": 64,
            "genres": [
              "Action",
              "Fantasy"
            ],
            "averageScore": null,
            "meanScore": 82,
            "siteUrl": "https://anilist.co/anime/119924",
            "characters": {
              "__typename": "CharacterConnection",
              "edges": []
            },
            "staff": {
              "__typename": "StaffConnection",
              "edges": []
            },
            "studios": {
              "__typename": "StudioConnection",
              "nodes": [
                {
                  "__typename": "Studio",
                  "id": 6475,
                  "name": "Ruo Hong Culture"
                }
              ]
            }
          }
        },
        {
          "id": 288963,
          "airingAt": 1596420000,
          "__typename": "AiringSchedule",
          "episode": 17,
          "media": {
            "__typename": "Media",
            "id": 119927,
            "type": "ANIME",
            "format": "ONA",
            "title": {
              "__typename": "MediaTitle",
              "romaji": "Dubu Xiaoyao",
              "native": "独步逍遥",
              "english": null
            },
            "description": null,
            "coverImage": {
              "__typename": "MediaCoverImage",
              "large": "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx119927-y4o8s29KJANF.jpg"
            },
            "bannerImage": null,
            "episodes": 40,
            "genres": [
              "Action",
              "Romance"
            ],
            "averageScore": null,
            "meanScore": 66,
            "siteUrl": "https://anilist.co/anime/119927",
            "characters": {
              "__typename": "CharacterConnection",
              "edges": []
            },
            "staff": {
              "__typename": "StaffConnection",
              "edges": []
            },
            "studios": {
              "__typename": "StudioConnection",
              "nodes": []
            }
          }
        }
      ]
    }
  }
}"""
    }
}