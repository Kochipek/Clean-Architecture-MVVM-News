import com.google.common.truth.Truth.assertThat
import com.kochipek.news_app.BuildConfig
import com.kochipek.news_app.data.api.NewsApiService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiServiceTest {
    // test for the NewsApiService interface
    private lateinit var newsApiService: NewsApiService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        newsApiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }

    private fun enqueueMockResponse(fileName: String) {
        // Enqueue Mock Response
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
                .setResponseCode(200)
        )
    }

    @Test
    fun getNews_sentRequest_receivedResponse() {
        runBlocking {
            enqueueMockResponse("newsResponse.json")
            val responseBody = newsApiService.getNews("us", 1).body()
            val request = mockWebServer.takeRequest()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=${BuildConfig.API_KEY}")
        }
    }

    @Test
    fun getNews_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("newsResponse.json")
            val responseBody = newsApiService.getNews("us", 1).body()
            // Verify the response body with truth library
            assertThat(responseBody?.articles?.size).isEqualTo(20)
        }
    }

    @Test
    fun getNewsItem_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("newsResponse.json")
            val responseBody = newsApiService.getNews("us", 1).body()
            val article = responseBody?.articles?.get(0)
            assertThat(article?.author).isEqualTo("Reuters")
        }
    }

    @After
    fun teardown() {
        // Close the MockWebServer after the test
        mockWebServer.shutdown()
    }
}