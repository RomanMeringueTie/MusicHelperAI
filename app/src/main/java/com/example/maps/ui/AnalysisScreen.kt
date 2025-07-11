package com.example.maps.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.maps.R
import com.example.maps.data.model.Review
import com.example.maps.presentation.AnalysisViewModel
import com.example.maps.presentation.State

@Composable
fun AnalysisScreen(modifier: Modifier, viewModel: AnalysisViewModel) {

    val state = viewModel.review.collectAsState()

    AnalysisScreenImpl(
        modifier = modifier,
        state = state.value,
        onDismissError = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreenImpl(modifier: Modifier, state: State<Review>, onDismissError: () -> Unit) {

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.analysis)) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            when (state) {

                State.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.Center)
                    )
                }

                is State.Content -> {
                    val items = listOf(
                        Pair("Характер", state.data.traits),
                        Pair("Вкус", state.data.taste),
                        Pair("Рекомендации", state.data.recommendations),
                    )
                    EnterAnimation {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(items) {
                                val screenWidth = LocalConfiguration.current.screenWidthDp.dp
                                ReviewCard(
                                    modifier = Modifier
                                        .fillParentMaxHeight()
                                        .width(screenWidth - 32.dp),
                                    title = it.first,
                                    body = it.second
                                )
                            }
                        }
                    }
                }

                is State.Failure -> {
                    AlertDialog(
                        onDismissRequest = { onDismissError() },
                        title = {
                            Text("Ошибка")
                        },
                        text = {
                            Text(state.message)
                        },
                        confirmButton = {
                            TextButton(onClick = { onDismissError() }) {
                                Text("OK")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ReviewCard(modifier: Modifier, title: String, body: String) {
    Card(
        modifier = modifier
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = body,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}