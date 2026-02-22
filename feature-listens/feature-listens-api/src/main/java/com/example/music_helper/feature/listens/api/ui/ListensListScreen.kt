package com.example.music_helper.feature.listens.api.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.music_helper.common.api.presentation.State
import com.example.music_helper.common.api.ui.utils.getDayTimeFromEpochTime
import com.example.music_helper.feature.listens.api.model.TrackReview
import com.example.music_helper.common.api.R as CommonR
import com.example.music_helper.feature.listens.api.R
import com.example.music_helper.feature.listens.api.model.Day
import com.example.music_helper.feature.listens.api.model.ListenFull
import com.example.music_helper.feature.listens.api.presentation.ListensListViewModel

@Composable
fun ListensListScreen(
    modifier: Modifier,
    viewModel: ListensListViewModel,
    onListensAnalyze: () -> Unit,
    onStats: () -> Unit,
    onRouteToSettings: () -> Unit,
) {
    val days = viewModel.days.collectAsState()
    val indexToDelete = viewModel.indexToDelete.collectAsState()
    val isInsertDialogVisible = viewModel.isInsertDialogShown.collectAsState()
    val indexToAnalyze = viewModel.indexToAnalyze.collectAsState()
    val trackReview = viewModel.trackReview.collectAsState()

    ListensListScreenImpl(
        modifier = modifier,
        state = days.value,
        onRefresh = viewModel::loadListens,
        indexToAnalyze = indexToAnalyze.value,
        onIndexToAnalyzeChange = viewModel::setIndexToAnalyze,
        onListensAnalyze = onListensAnalyze,
        onTrackAnalyze = viewModel::analyzeListen,
        onStats = onStats,
        indexToDelete = indexToDelete.value,
        onRouteToSettings = onRouteToSettings,
        onIndexToDeleteChange = viewModel::setIndexToDelete,
        onDelete = viewModel::deleteListen,
        onInsert = viewModel::insertListen,
        onChangeInsertDialogVisibility = viewModel::changeInsertDialogVisibility,
        isInsertDialogVisible = isInsertDialogVisible.value,
        trackReview = trackReview.value
    )
}

@SuppressLint("SimpleDateFormat", "AutoboxingStateValueProperty")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListensListScreenImpl(
    modifier: Modifier = Modifier,
    state: State<List<Day>>,
    onDismissError: () -> Unit = {},
    onRefresh: () -> Unit,
    onListensAnalyze: () -> Unit,
    indexToAnalyze: Pair<Int, Int>?,
    onIndexToAnalyzeChange: (Int, Int) -> Unit,
    onTrackAnalyze: () -> Unit,
    onStats: () -> Unit,
    onRouteToSettings: () -> Unit,
    indexToDelete: Pair<Int, Int>?,
    onIndexToDeleteChange: (Int, Int) -> Unit,
    onDelete: () -> Unit,
    onInsert: (String, String) -> Unit,
    onChangeInsertDialogVisibility: () -> Unit,
    isInsertDialogVisible: Boolean,
    trackReview: State<TrackReview>,
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            key(MaterialTheme.colorScheme.background) {
                TopAppBar(
                    title = { Text(stringResource(R.string.you_have_listened)) },
                    actions = {
                        IconButton(onClick = onRefresh) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = stringResource(CommonR.string.refresh)
                            )
                        }
                        IconButton(onClick = onRouteToSettings) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = stringResource(CommonR.string.settings)
                            )
                        }
                    }
                )
            }
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
                            ElevatedButton(onClick = onListensAnalyze) {
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
                            Icon(Icons.Filled.Add, stringResource(CommonR.string.add_listen))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
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
                        DaysListContent(
                            days = state.data,
                            indexToAnalyze = indexToAnalyze,
                            onIndexToAnalyzeChange = onIndexToAnalyzeChange,
                            onTrackAnalyze = onTrackAnalyze,
                            indexToDelete = indexToDelete,
                            onIndexToDeleteChange = onIndexToDeleteChange,
                            onDelete = onDelete,
                            onInsert = onInsert,
                            onDismissInsert = onChangeInsertDialogVisibility,
                            isInsertDialogVisible = isInsertDialogVisible,
                            trackReview = trackReview
                        )
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
                                    Text(stringResource(CommonR.string.ok))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SimpleDateFormat")
@Composable
private fun DaysListContent(
    days: List<Day>,
    onDelete: () -> Unit,
    onTrackAnalyze: () -> Unit,
    isInsertDialogVisible: Boolean,
    onInsert: (String, String) -> Unit,
    onDismissInsert: () -> Unit,
    indexToDelete: Pair<Int, Int>?,
    onIndexToDeleteChange: (Int, Int) -> Unit,
    indexToAnalyze: Pair<Int, Int>?,
    onIndexToAnalyzeChange: (Int, Int) -> Unit,
    trackReview: State<TrackReview>,
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
                            onIndexToDeleteChange(dayIndex, listenIndex)
                        },
                        onTrackAnalyze = { listenIndex ->
                            onIndexToAnalyzeChange(dayIndex, listenIndex)
                            onTrackAnalyze()
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
            onDismiss = { onIndexToDeleteChange(-1, -1) }
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

    indexToAnalyze?.let {

//        AlertDialog(
//            onDismissRequest = {
//                onIndexToAnalyzeChange(
//                    -1,
//                    -1
//                )
//            },
//            title = {
//                when (trackReview) {
//                    is State.Content -> {
//                        Text("Анализ трека ${trackReview.data.artist} - ${trackReview.data.title}")
//                    }
//
//                    else -> {
//                        Text("Анализ трека")
//                    }
//                }
//            },
//            text = {
//                when (trackReview) {
//                    is State.Loading -> {
//                        Box(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .padding(16.dp),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            CircularProgressIndicator(
//                                modifier = Modifier.size(48.dp),
//                                color = MaterialTheme.colorScheme.primary,
//                                strokeWidth = 4.dp
//                            )
//                        }
//                    }
//
//                    is State.Failure -> {
//                        Column(
//                            modifier = Modifier.padding(16.dp),
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            Icon(
//                                imageVector = Icons.Default.Warning,
//                                contentDescription = null,
//                                tint = MaterialTheme.colorScheme.onErrorContainer,
//                                modifier = Modifier.size(32.dp)
//                            )
//                            Spacer(modifier = Modifier.height(8.dp))
//                            Text(
//                                text = trackReview.message,
//                                style = MaterialTheme.typography.bodyMedium,
//                                color = MaterialTheme.colorScheme.onErrorContainer,
//                                textAlign = TextAlign.Center
//                            )
//                            Spacer(modifier = Modifier.height(16.dp))
//                            TextButton(onClick = {
//                                onIndexToAnalyzeChange(-1, -1)
//                            }) {
//                                Text("OK")
//                            }
//                        }
//                    }
//
//                    is State.Content -> {
//                        val verticalScrollState = rememberScrollState()
//                        Text(
//                            modifier = Modifier.verticalScroll(verticalScrollState),
//                            text = trackReview.data.review
//                        )
//                    }
//                }
//            },
//            confirmButton = {
//                TextButton(onClick = {
//                    onIndexToAnalyzeChange(-1, -1)
//                }) {
//                    Text("OK")
//                }
//            },
//        )
        TrackAnalysisDialog(
            trackReview, onDismiss = {
                onIndexToAnalyzeChange(-1, -1)
            }
        )
    }

}

@SuppressLint("SimpleDateFormat")
@Composable
private fun DayItem(
    day: Day,
    onDelete: (Int) -> Unit,
    onTrackAnalyze: (Int) -> Unit,
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
                    contentDescription = if (isExpanded) stringResource(CommonR.string.collapse) else stringResource(CommonR.string.expand),

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
                            onTrackAnalyze = { onTrackAnalyze(listenIndex) },
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
    onTrackAnalyze: () -> Unit,
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

        IconButton(onClick = onTrackAnalyze) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = stringResource(CommonR.string.analyze_track),
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(if (isInDayView) 18.dp else 24.dp)
            )
        }

        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(CommonR.string.delete),
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
                Text(stringResource(CommonR.string.delete))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(CommonR.string.cancel))
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
        title = { Text(stringResource(CommonR.string.add_listen)) },
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
                Text(stringResource(CommonR.string.cancel))
            }
        }
    )
}

@Composable
fun TrackAnalysisDialog(
    trackReview: State<TrackReview>,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.note_icon),
                    contentDescription = stringResource(CommonR.string.note_icon),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = when (trackReview) {
                        is State.Content -> stringResource(R.string.analysis_track_title, trackReview.data.artist, trackReview.data.title)
                        else -> stringResource(R.string.analysis_track_default)
                    },
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        text = {
            TrackAnalysisContent(
                trackReview = trackReview,
                onDismiss = onDismiss
            )
        },
        confirmButton = {
            if (trackReview is State.Content) {
                TextButton(
                    onClick = onDismiss,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = stringResource(CommonR.string.close),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp,
        modifier = Modifier.widthIn(min = 280.dp, max = 560.dp)
    )
}

@Composable
private fun TrackAnalysisContent(
    trackReview: State<TrackReview>,
    onDismiss: () -> Unit,
) {
    when (trackReview) {
        is State.Loading -> {
            LoadingContent()
        }

        is State.Failure -> {
            ErrorContent(
                message = trackReview.message,
                onDismiss = onDismiss
            )
        }

        is State.Content -> {
            ReviewContent(review = trackReview.data.review)
        }
    }
}

@Composable
private fun LoadingContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 4.dp,
            strokeCap = StrokeCap.Round
        )
        Text(
            text = stringResource(R.string.analyzing_track),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onDismiss: () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Warning,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = stringResource(R.string.analysis_error),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
                textAlign = TextAlign.Center
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = onDismiss,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                ),
                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.onErrorContainer.copy(alpha = 0.5f)
                )
            ) {
                Text(
                    text = stringResource(CommonR.string.got_it),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
private fun ReviewContent(review: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        val scrollState = rememberScrollState()

        Text(
            text = review,
            style = MaterialTheme.typography.bodyMedium.copy(
                lineHeight = 22.sp
            ),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp, max = 400.dp)
                .verticalScroll(scrollState)
                .padding(20.dp),
            textAlign = TextAlign.Start
        )

        if (scrollState.maxValue > 0) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(scrollState.value.toFloat() / scrollState.maxValue)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.6f))
                )
            }
        }
    }
}
