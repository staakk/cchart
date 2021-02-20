package io.github.staakk.cchart.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val LINE_CHART_ID = 0

class SamplesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column {
                    val selectedIndex = remember { mutableStateOf(0) }
                    when (selectedIndex.value) {
                        LINE_CHART_ID -> LineChartScreen()
                    }

                    Text(
                        modifier = Modifier.fillMaxWidth()
                            .padding(16.dp),
                        text = "Select on of the samples below",
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    )
                    LazyColumn {
                        item { MenuItem(LINE_CHART_ID, "Line chart") { selectedIndex.value = it } }
                    }
                }
            }
        }
    }
}

@Composable
fun MenuItem(index: Int, text: String, onSelected: (Int) -> Unit) {
    Text(
        modifier = Modifier.fillMaxWidth()
            .clickable { onSelected(index) }
            .padding(16.dp),
        text = text,
        style = TextStyle(fontSize = 18.sp)
    )
}