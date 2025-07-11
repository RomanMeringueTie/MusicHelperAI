package com.example.maps.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ListensListScreen(
    modifier: Modifier,
    viewModel: ListensListViewModel,
    onClick: (String) -> Unit,
    onRouteToSettings: () -> Unit,
) {
    val listens = viewModel.listens.collectAsState()

    ListensListScreenImpl(
        modifier = modifier,
        state = listens.value,
        onClick = onClick,
        onRouteToSettings = onRouteToSettings,
        onDelete = viewModel::deleteListen
    )

}

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListensListScreenImpl(
    modifier: Modifier = Modifier,
    state: State<List<ListenFull>>,
    onDismissError: () -> Unit = {},
    onClick: (String) -> Unit,
    onRouteToSettings: () -> Unit,
    onDelete: (Int) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.you_have_listened)) },
                actions = {
                    IconButton(onClick = onRouteToSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
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
                    ElevatedButton(onClick = {
                        onClick(state.data.joinToString { "${it.artist} - ${it.title}" })
                    })
                    {
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
                State.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.Center)
                    )
                }

                is State.Content -> {
                    EnterAnimation {
                        ListensListContent(listens = state.data, onDelete = onDelete)
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

@SuppressLint("SimpleDateFormat")
@Composable
private fun ListensListContent(listens: List<ListenFull>, onDelete: (Int) -> Unit) {
    var indexToDelete by remember { mutableStateOf<Int?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(items = listens) { index, listen ->
            Card(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
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
                    val date = Date(listen.playedAt)
                    val format = SimpleDateFormat("HH:mm")
                    val time = format.format(date)
                    Column {
                        IconButton(onClick = { indexToDelete = index }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        }
                        Text(
                            text = time,
                            color = Color.Gray
                        )
                    }

                }
            }
            indexToDelete?.let {
                DeleteDialog(
                    onConfirm = {
                        onDelete(indexToDelete!!)
                        indexToDelete = null
                    },
                    onDismiss = { indexToDelete = null }
                )
            }
        }
    }
}

@Composable
private fun DeleteDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Подтвердите удаление") },
        text = { Text("Вы уверены, что хотите удалить этот элемент?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Удалить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun Preview_HomeScreen() {
    ListensListScreenImpl(
        modifier = Modifier.fillMaxSize(),
        state = State.Content(persistentListOf()),
        onClick = {},
        onDismissError = {},
        onRouteToSettings = {},
        onDelete = {}
    )
}