package com.example.maps.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.example.maps.R
import com.example.maps.data.model.AppInfo
import com.example.maps.presentation.PickAppsViewModel
import com.example.maps.presentation.State

@Composable
fun PickAppsScreen(modifier: Modifier, viewModel: PickAppsViewModel, onRoute: () -> Unit) {
    val state = viewModel.apps.collectAsState()
    val query = viewModel.searchQuery.collectAsState()

    PickAppsScreenImpl(
        modifier = modifier,
        state = state.value,
        searchQuery = query.value,
        onSearchChange = viewModel::onSearchQueryChanged,
        onClick = viewModel::onAppPick,
        onSave = { viewModel.onSave(); onRoute() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickAppsScreenImpl(
    modifier: Modifier = Modifier,
    state: State<List<AppInfo>>,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onClick: (String) -> Unit,
    onSave: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(stringResource(R.string.pick_apps)) }
                )
                OutlinedTextField(
                    colors = TextFieldDefaults.colors()
                        .copy(unfocusedContainerColor = Color.Transparent),
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    placeholder = { Text(stringResource(R.string.app_search)) },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search Icon"
                        )
                    },
                    shape = RoundedCornerShape(20.dp)
                )
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                ElevatedButton(
                    onClick = onSave,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = stringResource(R.string.save), modifier = Modifier.padding(8.dp))
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
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(state.data) {app ->
                                AppItemRow(
                                    app = app,
                                    checked = app.isPicked,
                                    onCheckedChange = { onClick(app.packageName) }
                                )
                            }
                        }
                    }
                }

                is State.Failure -> {
                    AlertDialog(
                        onDismissRequest = {},
                        title = { stringResource(R.string.error) },
                        text = { Text(state.message) },
                        confirmButton = {
                            TextButton(onClick = {}) {
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
fun AppItemRow(
    app: AppInfo,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val imageBitmap = remember(app.icon) {
            app.icon.toBitmap().asImageBitmap()
        }

        Image(
            painter = BitmapPainter(imageBitmap),
            contentDescription = app.appName,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = app.appName,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedChange(it) }
        )
    }
}

//@Composable
//@Preview(showBackground = true)
//fun PickAppsScreen_Preview() {
//    PickAppsScreenImpl(
//        modifier = Modifier.fillMaxSize(),
//        apps = persistentListOf(),
//        onClick = {},
//        onSave = {}
//    )
//}