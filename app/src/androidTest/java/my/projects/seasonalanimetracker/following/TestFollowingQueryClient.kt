package my.projects.seasonalanimetracker.following

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.apollographql.apollo.ApolloClient
import junit.framework.Assert.assertEquals
import my.projects.seasonalanimetracker.following.domain.FollowingQueryClient
import my.projects.seasonalanimetracker.following.domain.mapper.query.FollowingQueryToDataMapper
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestFollowingQueryClient {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockWebServer = MockWebServer()
    private lateinit var apolloClient: ApolloClient

    private lateinit var queryClient: FollowingQueryClient

    @Before
    fun setUp() {
        mockWebServer.start()

        apolloClient = ApolloClient.builder().serverUrl(mockWebServer.url("/")).build()
        queryClient = FollowingQueryClient(apolloClient, FollowingQueryToDataMapper())
    }

    @Test
    fun returnsPagesCount() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(getUserIdJson()))
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(getFollowingPagesCountJson()))

        val count = queryClient.getPagesCount().blockingGet()

        assertEquals(1, count)
    }

    @Test
    fun returnsPage() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(getUserIdJson()))
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(getFollowingItemJson()))

        val items = queryClient.getPage(1).blockingGet()

        assertEquals(1, items.size)
    }

    private fun getUserIdJson(): String {
        return """{
    "data": {
        "Viewer": {
            "__typename": "User",
            "id": 302319
        }
    }
}"""
    }

    private fun getFollowingPagesCountJson(): String {
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
            "mediaList": [
                {
                    "__typename": "MediaList",
                    "id": 133705906
                },
                {
                    "__typename": "MediaList",
                    "id": 133705973
                },
                {
                    "__typename": "MediaList",
                    "id": 133705976
                }]}}}"""
    }

    private fun getFollowingItemJson(): String {
        return """{
    "data": {
        "Page": {
            "__typename": "Page",
            "pageInfo": {
                "__typename": "PageInfo",
                "total": 1,
                "perPage": 50,
                "currentPage": 1,
                "lastPage": 1,
                "hasNextPage": false
            },
            "mediaList": [{
                    "__typename": "MediaList",
                    "id": 133705993,
                    "status": "CURRENT",
                    "media": {
                        "__typename": "Media",
                        "id": 115113,
                        "type": "ANIME",
                        "format": "TV",
                        "title": {
                            "__typename": "MediaTitle",
                            "romaji": "Uzaki-chan wa Asobitai!",
                            "native": "宇崎ちゃんは遊びたい！",
                            "english": "Uzaki-chan Wants to Hang Out!"
                        },
                        "season": "SUMMER",
                        "seasonYear": 2020,
                        "description": "Sakurai Shinichi’s one wish is for a little peace and quiet. But Uzaki Hana–his boisterous, well-endowed underclassman–has other plans. All she wants is to hang out and poke fun at him. With the help of her chipper charm and peppy persistence, this might just be the start of a beautiful relationship!<br>\n<br>\n(Source: Seven Seas Entertainment)",
                        "coverImage": {
                            "__typename": "MediaCoverImage",
                            "large": "https://s4.anilist.co/file/anilistcdn/media/anime/cover/medium/bx115113-bJDZV7kP0XrP.png"
                        },
                        "bannerImage": "https://s4.anilist.co/file/anilistcdn/media/anime/banner/115113-wIWyzBlDR5Kt.jpg",
                        "episodes": 12,
                        "genres": [
                            "Comedy",
                            "Ecchi",
                            "Romance",
                            "Slice of Life"
                        ],
                        "averageScore": 67,
                        "meanScore": 68,
                        "siteUrl": "https://anilist.co/anime/115113",
                        "characters": {
                            "__typename": "CharacterConnection",
                            "edges": [
                                {
                                    "__typename": "CharacterEdge",
                                    "id": 201787,
                                    "media": [],
                                    "node": {
                                        "__typename": "Character",
                                        "id": 130665,
                                        "name": {
                                            "__typename": "CharacterName",
                                            "full": "Hana Uzaki"
                                        },
                                        "image": {
                                            "__typename": "CharacterImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/character/medium/b130665-eb65zDu3doTn.jpg"
                                        }
                                    },
                                    "voiceActors": [
                                        {
                                            "__typename": "Staff",
                                            "id": 117329,
                                            "name": {
                                                "__typename": "StaffName",
                                                "full": "Naomi Oozora"
                                            },
                                            "image": {
                                                "__typename": "StaffImage",
                                                "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/n117329-lF0lh3HMfjRd.jpg"
                                            },
                                            "language": "JAPANESE"
                                        },
                                        {
                                            "__typename": "Staff",
                                            "id": 95159,
                                            "name": {
                                                "__typename": "StaffName",
                                                "full": "Monica Rial"
                                            },
                                            "image": {
                                                "__typename": "StaffImage",
                                                "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/n95159-iTd65o5LhA62.png"
                                            },
                                            "language": "ENGLISH"
                                        }
                                    ]
                                },
                                {
                                    "__typename": "CharacterEdge",
                                    "id": 201788,
                                    "media": [],
                                    "node": {
                                        "__typename": "Character",
                                        "id": 130664,
                                        "name": {
                                            "__typename": "CharacterName",
                                            "full": "Shinichi Sakurai"
                                        },
                                        "image": {
                                            "__typename": "CharacterImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/character/medium/b130664-zhP3G0cDTMX4.png"
                                        }
                                    },
                                    "voiceActors": [
                                        {
                                            "__typename": "Staff",
                                            "id": 102512,
                                            "name": {
                                                "__typename": "StaffName",
                                                "full": "Kenji Akabane"
                                            },
                                            "image": {
                                                "__typename": "StaffImage",
                                                "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/n102512-3nVZNCewjZYm.png"
                                            },
                                            "language": "JAPANESE"
                                        },
                                        {
                                            "__typename": "Staff",
                                            "id": 111775,
                                            "name": {
                                                "__typename": "StaffName",
                                                "full": "Ricco Fajardo"
                                            },
                                            "image": {
                                                "__typename": "StaffImage",
                                                "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/n111775-gTts7pK5zHJ2.jpg"
                                            },
                                            "language": "ENGLISH"
                                        }
                                    ]
                                },
                                {
                                    "__typename": "CharacterEdge",
                                    "id": 201789,
                                    "media": [],
                                    "node": {
                                        "__typename": "Character",
                                        "id": 159410,
                                        "name": {
                                            "__typename": "CharacterName",
                                            "full": "Ami Asai"
                                        },
                                        "image": {
                                            "__typename": "CharacterImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/character/medium/b159410-9Tk2NTmgQney.png"
                                        }
                                    },
                                    "voiceActors": [
                                        {
                                            "__typename": "Staff",
                                            "id": 101996,
                                            "name": {
                                                "__typename": "StaffName",
                                                "full": "Ayana Taketatsu"
                                            },
                                            "image": {
                                                "__typename": "StaffImage",
                                                "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/n101996-JdC7dME8uuNU.png"
                                            },
                                            "language": "JAPANESE"
                                        }
                                    ]
                                },
                                {
                                    "__typename": "CharacterEdge",
                                    "id": 201790,
                                    "media": [],
                                    "node": {
                                        "__typename": "Character",
                                        "id": 159409,
                                        "name": {
                                            "__typename": "CharacterName",
                                            "full": "Itsuhito Sakaki"
                                        },
                                        "image": {
                                            "__typename": "CharacterImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/character/medium/b159409-ogn9B95ldgEQ.png"
                                        }
                                    },
                                    "voiceActors": [
                                        {
                                            "__typename": "Staff",
                                            "id": 159411,
                                            "name": {
                                                "__typename": "StaffName",
                                                "full": "Tomoya Takagi"
                                            },
                                            "image": {
                                                "__typename": "StaffImage",
                                                "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/n159411-vcy9Xh2GX6AS.png"
                                            },
                                            "language": "JAPANESE"
                                        }
                                    ]
                                },
                                {
                                    "__typename": "CharacterEdge",
                                    "id": 221991,
                                    "media": [],
                                    "node": {
                                        "__typename": "Character",
                                        "id": 169505,
                                        "name": {
                                            "__typename": "CharacterName",
                                            "full": "Akihiko Asai"
                                        },
                                        "image": {
                                            "__typename": "CharacterImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/character/medium/b169505-WJnD7PlvyWhu.png"
                                        }
                                    },
                                    "voiceActors": [
                                        {
                                            "__typename": "Staff",
                                            "id": 95634,
                                            "name": {
                                                "__typename": "StaffName",
                                                "full": "Yousuke Akimoto"
                                            },
                                            "image": {
                                                "__typename": "StaffImage",
                                                "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/n95634-q6SacvhRo9Pq.jpg"
                                            },
                                            "language": "JAPANESE"
                                        }
                                    ]
                                },
                                {
                                    "__typename": "CharacterEdge",
                                    "id": 236255,
                                    "media": [],
                                    "node": {
                                        "__typename": "Character",
                                        "id": 181196,
                                        "name": {
                                            "__typename": "CharacterName",
                                            "full": "Kuso Cat"
                                        },
                                        "image": {
                                            "__typename": "CharacterImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/character/medium/b181196-BZ37uPVISyTK.png"
                                        }
                                    },
                                    "voiceActors": []
                                },
                                {
                                    "__typename": "CharacterEdge",
                                    "id": 247445,
                                    "media": [],
                                    "node": {
                                        "__typename": "Character",
                                        "id": 157997,
                                        "name": {
                                            "__typename": "CharacterName",
                                            "full": "Tsuki  Uzaki"
                                        },
                                        "image": {
                                            "__typename": "CharacterImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/character/medium/b157997-pX33n6xC1sET.jpg"
                                        }
                                    },
                                    "voiceActors": [
                                        {
                                            "__typename": "Staff",
                                            "id": 95869,
                                            "name": {
                                                "__typename": "StaffName",
                                                "full": "Saori Hayami"
                                            },
                                            "image": {
                                                "__typename": "StaffImage",
                                                "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/n95869-GhaqnZtJRAUj.png"
                                            },
                                            "language": "JAPANESE"
                                        }
                                    ]
                                }
                            ]
                        },
                        "staff": {
                            "__typename": "StaffConnection",
                            "edges": [
                                {
                                    "__typename": "StaffEdge",
                                    "id": 167049,
                                    "node": {
                                        "__typename": "Staff",
                                        "id": 118660,
                                        "name": {
                                            "__typename": "StaffName",
                                            "full": "Kazuya Miura"
                                        },
                                        "image": {
                                            "__typename": "StaffImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/n118660-FQju22rhdLVq.png"
                                        }
                                    },
                                    "role": "Director"
                                },
                                {
                                    "__typename": "StaffEdge",
                                    "id": 167050,
                                    "node": {
                                        "__typename": "Staff",
                                        "id": 103837,
                                        "name": {
                                            "__typename": "StaffName",
                                            "full": "Takashi Aoshima"
                                        },
                                        "image": {
                                            "__typename": "StaffImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/103837-mXSivqkIscdx.jpg"
                                        }
                                    },
                                    "role": "Series Composition"
                                },
                                {
                                    "__typename": "StaffEdge",
                                    "id": 167051,
                                    "node": {
                                        "__typename": "Staff",
                                        "id": 133642,
                                        "name": {
                                            "__typename": "StaffName",
                                            "full": "Manabu Kurihara"
                                        },
                                        "image": {
                                            "__typename": "StaffImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/default.jpg"
                                        }
                                    },
                                    "role": "Character Design"
                                },
                                {
                                    "__typename": "StaffEdge",
                                    "id": 167052,
                                    "node": {
                                        "__typename": "Staff",
                                        "id": 125685,
                                        "name": {
                                            "__typename": "StaffName",
                                            "full": "Take"
                                        },
                                        "image": {
                                            "__typename": "StaffImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/125685-0MsyJxwDLPIN.png"
                                        }
                                    },
                                    "role": "Original Creator"
                                },
                                {
                                    "__typename": "StaffEdge",
                                    "id": 196989,
                                    "node": {
                                        "__typename": "Staff",
                                        "id": 174558,
                                        "name": {
                                            "__typename": "StaffName",
                                            "full": "YuNi"
                                        },
                                        "image": {
                                            "__typename": "StaffImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/n174558-MzCKOWsb34XO.png"
                                        }
                                    },
                                    "role": "Theme Song Performance (ED)"
                                },
                                {
                                    "__typename": "StaffEdge",
                                    "id": 202089,
                                    "node": {
                                        "__typename": "Staff",
                                        "id": 180249,
                                        "name": {
                                            "__typename": "StaffName",
                                            "full": "YUC'e"
                                        },
                                        "image": {
                                            "__typename": "StaffImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/n180249-lxQ8OgzkCKJK.png"
                                        }
                                    },
                                    "role": "Theme Song Lyrics (ED)"
                                },
                                {
                                    "__typename": "StaffEdge",
                                    "id": 202090,
                                    "node": {
                                        "__typename": "Staff",
                                        "id": 180249,
                                        "name": {
                                            "__typename": "StaffName",
                                            "full": "YUC'e"
                                        },
                                        "image": {
                                            "__typename": "StaffImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/n180249-lxQ8OgzkCKJK.png"
                                        }
                                    },
                                    "role": "Theme Song Arrangement (ED)"
                                },
                                {
                                    "__typename": "StaffEdge",
                                    "id": 202091,
                                    "node": {
                                        "__typename": "Staff",
                                        "id": 180249,
                                        "name": {
                                            "__typename": "StaffName",
                                            "full": "YUC'e"
                                        },
                                        "image": {
                                            "__typename": "StaffImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/n180249-lxQ8OgzkCKJK.png"
                                        }
                                    },
                                    "role": "Theme Song Composition (ED)"
                                },
                                {
                                    "__typename": "StaffEdge",
                                    "id": 202095,
                                    "node": {
                                        "__typename": "Staff",
                                        "id": 120081,
                                        "name": {
                                            "__typename": "StaffName",
                                            "full": "Kano"
                                        },
                                        "image": {
                                            "__typename": "StaffImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/25081-AwbA5ITp8DQY.jpg"
                                        }
                                    },
                                    "role": "Theme Song Performance (OP)"
                                },
                                {
                                    "__typename": "StaffEdge",
                                    "id": 202096,
                                    "node": {
                                        "__typename": "Staff",
                                        "id": 117329,
                                        "name": {
                                            "__typename": "StaffName",
                                            "full": "Naomi Oozora"
                                        },
                                        "image": {
                                            "__typename": "StaffImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/n117329-lF0lh3HMfjRd.jpg"
                                        }
                                    },
                                    "role": "Theme Song Performance (OP)"
                                },
                                {
                                    "__typename": "StaffEdge",
                                    "id": 202097,
                                    "node": {
                                        "__typename": "Staff",
                                        "id": 128073,
                                        "name": {
                                            "__typename": "StaffName",
                                            "full": "Tsubasa Itou"
                                        },
                                        "image": {
                                            "__typename": "StaffImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/default.jpg"
                                        }
                                    },
                                    "role": "Theme Song Arrangement (OP)"
                                },
                                {
                                    "__typename": "StaffEdge",
                                    "id": 202098,
                                    "node": {
                                        "__typename": "Staff",
                                        "id": 148496,
                                        "name": {
                                            "__typename": "StaffName",
                                            "full": "Tomokazu Tashiro"
                                        },
                                        "image": {
                                            "__typename": "StaffImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/n148496-iKSIC1NJYz69.png"
                                        }
                                    },
                                    "role": "Theme Song Composition (OP)"
                                },
                                {
                                    "__typename": "StaffEdge",
                                    "id": 202099,
                                    "node": {
                                        "__typename": "Staff",
                                        "id": 148496,
                                        "name": {
                                            "__typename": "StaffName",
                                            "full": "Tomokazu Tashiro"
                                        },
                                        "image": {
                                            "__typename": "StaffImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/n148496-iKSIC1NJYz69.png"
                                        }
                                    },
                                    "role": "Theme Song Lyrics (OP)"
                                },
                                {
                                    "__typename": "StaffEdge",
                                    "id": 203446,
                                    "node": {
                                        "__typename": "Staff",
                                        "id": 133642,
                                        "name": {
                                            "__typename": "StaffName",
                                            "full": "Manabu Kurihara"
                                        },
                                        "image": {
                                            "__typename": "StaffImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/default.jpg"
                                        }
                                    },
                                    "role": "Chief Animation Director"
                                },
                                {
                                    "__typename": "StaffEdge",
                                    "id": 203447,
                                    "node": {
                                        "__typename": "Staff",
                                        "id": 168267,
                                        "name": {
                                            "__typename": "StaffName",
                                            "full": "Rina Oguchi"
                                        },
                                        "image": {
                                            "__typename": "StaffImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/default.jpg"
                                        }
                                    },
                                    "role": "Editing"
                                },
                                {
                                    "__typename": "StaffEdge",
                                    "id": 203448,
                                    "node": {
                                        "__typename": "Staff",
                                        "id": 148829,
                                        "name": {
                                            "__typename": "StaffName",
                                            "full": "Ayako Aihara"
                                        },
                                        "image": {
                                            "__typename": "StaffImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/default.jpg"
                                        }
                                    },
                                    "role": "Color Design"
                                },
                                {
                                    "__typename": "StaffEdge",
                                    "id": 203449,
                                    "node": {
                                        "__typename": "Staff",
                                        "id": 159162,
                                        "name": {
                                            "__typename": "StaffName",
                                            "full": "Satoshi Watanabe"
                                        },
                                        "image": {
                                            "__typename": "StaffImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/default.jpg"
                                        }
                                    },
                                    "role": "Art Design"
                                },
                                {
                                    "__typename": "StaffEdge",
                                    "id": 203450,
                                    "node": {
                                        "__typename": "Staff",
                                        "id": 159162,
                                        "name": {
                                            "__typename": "StaffName",
                                            "full": "Satoshi Watanabe"
                                        },
                                        "image": {
                                            "__typename": "StaffImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/default.jpg"
                                        }
                                    },
                                    "role": "Art Director"
                                },
                                {
                                    "__typename": "StaffEdge",
                                    "id": 203451,
                                    "node": {
                                        "__typename": "Staff",
                                        "id": 119688,
                                        "name": {
                                            "__typename": "StaffName",
                                            "full": "Kiyotaka Kawada"
                                        },
                                        "image": {
                                            "__typename": "StaffImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/default.jpg"
                                        }
                                    },
                                    "role": "Sound Effects"
                                },
                                {
                                    "__typename": "StaffEdge",
                                    "id": 203452,
                                    "node": {
                                        "__typename": "Staff",
                                        "id": 103110,
                                        "name": {
                                            "__typename": "StaffName",
                                            "full": "Yasunori Ebina"
                                        },
                                        "image": {
                                            "__typename": "StaffImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/n103110-z6EM0rxSduFl.png"
                                        }
                                    },
                                    "role": "Sound Director"
                                },
                                {
                                    "__typename": "StaffEdge",
                                    "id": 203453,
                                    "node": {
                                        "__typename": "Staff",
                                        "id": 181127,
                                        "name": {
                                            "__typename": "StaffName",
                                            "full": "Satoshi Igarashi"
                                        },
                                        "image": {
                                            "__typename": "StaffImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/default.jpg"
                                        }
                                    },
                                    "role": "Music"
                                },
                                {
                                    "__typename": "StaffEdge",
                                    "id": 203454,
                                    "node": {
                                        "__typename": "Staff",
                                        "id": 148830,
                                        "name": {
                                            "__typename": "StaffName",
                                            "full": "Kotobuki Matsumoku"
                                        },
                                        "image": {
                                            "__typename": "StaffImage",
                                            "medium": "https://s4.anilist.co/file/anilistcdn/staff/medium/default.jpg"
                                        }
                                    },
                                    "role": "Director of Photography"
                                }
                            ]
                        },
                        "studios": {
                            "__typename": "StudioConnection",
                            "nodes": [
                                {
                                    "__typename": "Studio",
                                    "id": 6292,
                                    "name": "ENGI"
                                }
                            ]
                        },
                        "nextAiringEpisode": {
                            "__typename": "AiringSchedule",
                            "id": 287322,
                            "airingAt": 1600432200,
                            "episode": 11
                        }
                    }
                }]}}}"""
    }
}