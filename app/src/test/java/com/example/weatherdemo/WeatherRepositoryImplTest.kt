package com.example.weatherdemo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.weatherdemo.data.api.ForecastApiService
import com.example.weatherdemo.data.api.GeocodingApiService
import com.example.weatherdemo.data.models.GeocodingDto
import com.example.weatherdemo.data.repository.WeatherRepositoryImpl
import com.example.weatherdemo.domain.models.City
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.capture
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class WeatherRepositoryImplTest {

    @Mock
    private lateinit var mockGeocodingApiService: GeocodingApiService

    @Mock
    private lateinit var mockForecastApiService: ForecastApiService

    @Mock
    private lateinit var mockContext: Context // Usually not directly interacted with beyond DataStore

    @Mock
    private lateinit var mockDataStore: DataStore<Preferences>

    @Mock
    private lateinit var mockPreferences: Preferences

    @Mock
    private lateinit var mockMutablePreferences: MutablePreferences

    private lateinit var gson: Gson // Use real Gson for serialization tests if needed, or mock it
    private lateinit var weatherRepository: WeatherRepositoryImpl

    private val testApiKey = "test_api_key"
    private val LAST_CITY_KEY = stringPreferencesKey("last_searched_city")

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        gson = Gson()

        weatherRepository = WeatherRepositoryImpl(
            mockGeocodingApiService,
            mockForecastApiService,
            mockDataStore,
            gson,
            testApiKey
        )
    }

    // --- searchCities Tests ---
    @Test
    fun `searchCities success returns mapped cities`() {
        runTest {
        val query = "London"
        val limit = 5
        val mockCityDto = GeocodingDto(name = "London", lat = 51.5, lon = -0.1, country = "GB", state = "England", localNames = emptyMap())
        val expectedCities = listOf(City("London", 51.5, -0.1, "GB", "England", emptyMap()))

        whenever(mockGeocodingApiService.searchCities(query, limit, testApiKey))
            .thenAnswer{
                listOf(mockCityDto)
            }

        val result = weatherRepository.searchCities(query, limit)

        Assert.assertEquals(expectedCities, result)
        verify(mockGeocodingApiService).searchCities(query, limit, testApiKey)
        }
    }

    @Test
    fun `searchCities api error returns empty list`() = runTest {
        val query = "ErrorCity"
        val limit = 1
        whenever(mockGeocodingApiService.searchCities(query, limit, testApiKey))
            .thenThrow(RuntimeException("API Network Error"))

        val result = weatherRepository.searchCities(query, limit)

        Assert.assertTrue(result.isEmpty())
    }

}