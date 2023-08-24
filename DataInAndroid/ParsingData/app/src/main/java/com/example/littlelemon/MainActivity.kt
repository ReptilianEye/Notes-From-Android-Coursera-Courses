package com.example.littlelemon

import android.os.Bundle
import android.view.Menu
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val client = HttpClient(Android)


    private val menuItemsLiveData = MutableLiveData<List<String>>()
    private suspend fun getMenu(category: String): List<String> {
        val response: String =
            client.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/littleLemonMenu.json")
                .bodyAsText()
        val gson = Gson()
        val mapType = object : TypeToken<Map<String, MenuCategory>>() {}.type
        val objResp: Map<String, MenuCategory> = gson.fromJson(response, mapType)
        return objResp[category]?.menu ?: listOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val menuItems = getMenu("Salads")

            runOnUiThread {
                menuItemsLiveData.value = menuItems
            }
        }

        setContent {
            LittleLemonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val items = menuItemsLiveData.observeAsState(emptyList())
                        MenuItems(items.value)
                    }
                }
            }
        }
    }
}

@Composable
fun MenuItems(
    items: List<String> = emptyList(),
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        LazyColumn {
            itemsIndexed(items) { _, item ->
                MenuItemDetails(item)
            }
        }
    }
}

@Composable
fun MenuItemDetails(menuItem: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = menuItem)
    }
}
