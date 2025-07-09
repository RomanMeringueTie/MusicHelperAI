package com.example.maps.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.maps.R
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
fun AnalysisScreenImpl(modifier: Modifier, state: State<String>, onDismissError: () -> Unit) {

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
        ) {
            when (state) {
                State.Initial -> {}

                State.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.Center)
                    )
                }

                is State.Content -> {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                    ) {
                        Text(text = state.data, modifier = Modifier.padding(16.dp))
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