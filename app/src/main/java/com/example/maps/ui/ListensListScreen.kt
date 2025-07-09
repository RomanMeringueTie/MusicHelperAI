package com.example.maps.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.maps.R
import com.example.maps.data.model.ListenFull
import com.example.maps.presentation.ListensListViewModel
import com.example.maps.presentation.State
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ListensListScreen(modifier: Modifier, viewModel: ListensListViewModel, onClick: (String) -> Unit) {

    val listens = viewModel.listens.collectAsState()

    HomeScreenImpl(
        modifier = modifier,
        state = listens.value,
        onClick = onClick
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenImpl(
    modifier: Modifier = Modifier,
    state: State<List<ListenFull>>,
    onDismissError: () -> Unit = {},
    onClick: (String) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.you_have_listened)) }
            )
        },
        bottomBar = {
            if (state is State.Content) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ElevatedButton(onClick = { onClick(state.data.toString()) }) {
                        Text(
                            text = stringResource(R.string.analysis),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
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
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.data) { listen ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = listen.artist,
                                    )
                                    Text(
                                        text = listen.title,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }

                is State.Failure -> {
                    AlertDialog(
                        onDismissRequest = { onDismissError() },
                        title = {
                            Text(stringResource(R.string.error))
                        },
                        text = {
                            Text(state.message)
                        },
                        confirmButton = {
                            TextButton(onClick = { onDismissError() }) {
                                Text(stringResource(R.string.ok))
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun Preview_HomeScreen() {
    HomeScreenImpl(
        modifier = Modifier.fillMaxSize(),
        state = State.Content(persistentListOf()),
        onClick = {}
    )
}