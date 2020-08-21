package my.projects.seasonalanimetracker.schedule

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.apollographql.apollo.ApolloClient
import my.projects.seasonalanimetracker.schedule.domain.IScheduleQueryClient
import my.projects.seasonalanimetracker.schedule.domain.ScheduleQueryClient
import my.projects.seasonalanimetracker.schedule.domain.mapper.query.ScheduleQueryToDataMapper
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestQueryClient {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockWebServer = MockWebServer()
    private lateinit var apolloClient: ApolloClient
    private lateinit var scheduleQueryClient: IScheduleQueryClient

    @Before
    fun setUp() {
        mockWebServer.start()
        apolloClient = ApolloClient.builder().serverUrl(mockWebServer.url("")).build()
        scheduleQueryClient = ScheduleQueryClient(apolloClient, ScheduleQueryToDataMapper())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}