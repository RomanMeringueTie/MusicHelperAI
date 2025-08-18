package com.example.maps.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.maps.R
import com.example.maps.data.model.Day
import com.example.maps.data.model.ListenFull
import com.example.maps.presentation.ListensListViewModel
import com.example.maps.presentation.State
import com.example.maps.ui.utils.EnterAnimation
import com.example.maps.ui.utils.getDayTimeFromEpochTime

@Composable
fun ListensListScreen(
    modifier: Modifier,
    viewModel: ListensListViewModel,
    onAnalyze: () -> Unit,
    onStats: () -> Unit,
    onRouteToSettings: () -> Unit,
) {
    val days = viewModel.days.collectAsState()
    val indexToDelete = viewModel.indexToDelete.collectAsState()
    val isInsertDialogVisible = viewModel.isInsertDialogShown.collectAsState()

    ListensListScreenImpl(
        modifier = modifier,
        state = days.value,
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

@SuppressLint("SimpleDateFormat", "AutoboxingStateValueProperty")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListensListScreenImpl(
    modifier: Modifier = Modifier,
    state: State<List<Day>>,
    onDismissError: () -> Unit = {},
    onAnalyze: () -> Unit,
    onStats: () -> Unit,
    onRouteToSettings: () -> Unit,
    indexToDelete: Pair<Int, Int>?,
    onIndexChange: (Int, Int) -> Unit,
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
                Column {
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (state.data.isNotEmpty() && state.data.any { it.listens.isNotEmpty() }) {
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
                            elevation = FloatingActionButtonDefaults.elevation(
                                defaultElevation = 2.dp,
                                pressedElevation = 2.dp,
                                focusedElevation = 2.dp,
                                hoveredElevation = 2.dp
                            )
                        ) {

                            Icon(Icons.Filled.Add, "Add listen")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
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
                        DaysListContent(
                            days = state.data,
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
private fun DaysListContent(
    days: List<Day>,
    onDelete: () -> Unit,
    isInsertDialogVisible: Boolean,
    onInsert: (String, String) -> Unit,
    onDismissInsert: () -> Unit,
    indexToDelete: Pair<Int, Int>?,
    onIndexChange: (Int, Int) -> Unit,
) {
    val totalListens = days.sumOf { it.listens.size }

    if (totalListens == 0) {
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
                text = stringResource(R.string.empty_listens_placeholder_title),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.empty_listens_placeholder_body),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(items = days) { dayIndex, day ->
                if (day.listens.isNotEmpty()) {
                    DayItem(
                        day = day,
                        onDelete = { listenIndex ->
                            onIndexChange(dayIndex, listenIndex)
                        }
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }

    indexToDelete?.let {
        DeleteDialog(
            onConfirm = {
                onDelete()
            },
            onDismiss = { onIndexChange(-1, -1) }
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
private fun DayItem(
    day: Day,
    onDelete: (Int) -> Unit,
) {
    var isExpanded by rememberSaveable { mutableStateOf(true) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = day.date,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = stringResource(
                            R.string.listens_count,
                            day.listens.size
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",

                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )

                    day.listens.forEachIndexed { listenIndex, listen ->
                        ListenItem(
                            listen = listen,
                            onDelete = { onDelete(listenIndex) },
                            isInDayView = true
                        )

                        if (listenIndex < day.listens.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
private fun ListenItem(
    listen: ListenFull,
    onDelete: () -> Unit,
    isInDayView: Boolean = false,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = if (isInDayView) 16.dp else 16.dp,
                vertical = if (isInDayView) 8.dp else 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!isInDayView) {
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
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = listen.artist,
                style = if (isInDayView) MaterialTheme.typography.bodyMedium else MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = listen.title,
                style = if (isInDayView) MaterialTheme.typography.bodySmall else MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            val time = getDayTimeFromEpochTime(listen.playedAt)
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
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(if (isInDayView) 18.dp else 24.dp)
            )
        }
    }
}

@Composable
private fun DeleteDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.delete_listen_confirm_title)) },
        text = { Text(stringResource(R.string.delete_listen_confirm_body)) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.delete))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
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
        title = { Text(stringResource(R.string.add_listen)) },
        text = {
            Column {
                OutlinedTextField(
                    value = artist,
                    onValueChange = { artist = it },
                    label = { Text(stringResource(R.string.artist)) },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = track,
                    onValueChange = { track = it },
                    label = { Text(stringResource(R.string.track)) },
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
                Text(stringResource(R.string.save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

//@Composable
//@Preview(showBackground = true)
//fun Preview_HomeScreen() {
//    ListensListScreenImpl(
//        modifier = Modifier.fillMaxSize(),
//        state = State.Content(persistentListOf()),
//        onStats = {},
//        onAnalyze = {},
//        onDismissError = {},
//        onRouteToSettings = {},
//        indexToDelete = null,
//        onIndexChange = {},
//        onDelete = {},
//        onInsert = { it1, it2 -> },
//        onChangeInsertDialogVisibility = {},
//        isInsertDialogVisible = false
//    )
//}