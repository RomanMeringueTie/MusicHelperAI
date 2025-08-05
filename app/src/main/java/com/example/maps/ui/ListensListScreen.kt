package com.example.maps.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.maps.R
import com.example.maps.data.model.ListenFull
import com.example.maps.presentation.ListensListViewModel
import com.example.maps.presentation.State
import com.example.maps.ui.utils.EnterAnimation
import kotlinx.collections.immutable.persistentListOf
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ListensListScreen(
    modifier: Modifier,
    viewModel: ListensListViewModel,
    onAnalyze: () -> Unit,
    onStats: () -> Unit,
    onRouteToSettings: () -> Unit,
) {
    val listens = viewModel.listens.collectAsState()
    val indexToDelete = viewModel.indexToDelete.collectAsState()
    val isInsertDialogVisible = viewModel.isInsertDialogShown.collectAsState()

    ListensListScreenImpl(
        modifier = modifier,
        state = listens.value,
        onAnalyze = onAnalyze,
        onStats = onStats,
        indexToDelete = indexToDelete.value,
        onRouteToSettings = onRouteToSettings,
        onIndexChange = viewModel::setIndexToDelete,
        onDelete = viewModel::deleteListen,
        onInsert = viewModel::insertListen,
        onChangeInsertDialogVisibility = viewModel::changeInsertDialogVisibility,
        isInsertDialogVisible = isInsertDialogVisible.value
    )
}

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListensListScreenImpl(
    modifier: Modifier = Modifier,
    state: State<List<ListenFull>>,
    onDismissError: () -> Unit = {},
    onAnalyze: () -> Unit,
    onStats: () -> Unit,
    onRouteToSettings: () -> Unit,
    indexToDelete: Int?,
    onIndexChange: (Int?) -> Unit,
    onDelete: () -> Unit,
    onInsert: (String, String) -> Unit,
    onChangeInsertDialogVisibility: () -> Unit,
    isInsertDialogVisible: Boolean,
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
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (state.data.isNotEmpty()) {
                        ElevatedButton(onClick = onAnalyze) {
                            Text(
                                text = stringResource(R.string.analysis),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        ElevatedButton(onClick = onStats) {
                            Text(
                                text = stringResource(R.string.stats),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }

                    FloatingActionButton(
                        onClick = onChangeInsertDialogVisibility,
                    ) {
                        Icon(Icons.Filled.Add, "Add listen")
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            when (state) {
                State.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 4.dp
                        )
                    }
                }

                is State.Content -> {
                    EnterAnimation {
                        ListensListContent(
                            listens = state.data,
                            onDelete = onDelete,
                            indexToDelete = indexToDelete,
                            onIndexChange = onIndexChange,
                            onInsert = onInsert,
                            onDismissInsert = onChangeInsertDialogVisibility,
                            isInsertDialogVisible = isInsertDialogVisible,
                        )
                    }
                }

                is State.Failure -> {
                    Card(
                        modifier = Modifier

                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onErrorContainer,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = state.message,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            TextButton(onClick = { onDismissError() }) {
                                Text("OK")
                            }
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
private fun ListensListContent(
    listens: List<ListenFull>,
    onDelete: () -> Unit,
    isInsertDialogVisible: Boolean,
    onInsert: (String, String) -> Unit,
    onDismissInsert: () -> Unit,
    indexToDelete: Int?,
    onIndexChange: (Int?) -> Unit,
) {
    if (listens.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Тут будут прослушанные вами треки",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Послушайте музыку в ваших приложениях или добавьте вручную",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(items = listens) { index, listen ->
                    ListenItem(
                        listen = listen,
                        onDelete = { onIndexChange(index) }
                    )
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }

    indexToDelete?.let {
        DeleteDialog(
            onConfirm = {
                onDelete()
            },
            onDismiss = { onIndexChange(null) }
        )
    }
    if (isInsertDialogVisible) {
        InsertTrackDialog(
            onConfirm = { artist, title ->
                onInsert(artist, title)
                onDismissInsert()
            },
            onDismiss = onDismissInsert
        )
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
private fun ListenItem(
    listen: ListenFull,
    onDelete: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.note_icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = listen.artist,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = listen.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                val date = Date(listen.playedAt)
                val format = SimpleDateFormat("HH:mm", Locale.getDefault())
                val time = format.format(date)
                Text(
                    text = time,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.outline
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
private fun InsertTrackDialog(
    onConfirm: (String, String) -> Unit,
    onDismiss: () -> Unit,
) {
    var artist by remember { mutableStateOf("") }
    var track by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Добавить трек") },
        text = {
            Column {
                OutlinedTextField(
                    value = artist,
                    onValueChange = { artist = it },
                    label = { Text("Исполнитель") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = track,
                    onValueChange = { track = it },
                    label = { Text("Композиция") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(artist, track)
                },
                enabled = artist.isNotBlank() && track.isNotBlank()
            ) {
                Text("Сохранить")
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
        onStats = {},
        onAnalyze = {},
        onDismissError = {},
        onRouteToSettings = {},
        indexToDelete = null,
        onIndexChange = {},
        onDelete = {},
        onInsert = {} as (String, String) -> Unit,
        onChangeInsertDialogVisibility = {},
        isInsertDialogVisible = false
    )
}