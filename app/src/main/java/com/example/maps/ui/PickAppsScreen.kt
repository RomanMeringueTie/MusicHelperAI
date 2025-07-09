package com.example.maps.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.example.maps.R
import com.example.maps.data.model.AppInfo
import com.example.maps.presentation.PickAppsViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun PickAppsScreen(modifier: Modifier, viewModel: PickAppsViewModel, onRoute: () -> Unit) {
    val allApps = viewModel.apps.collectAsState()

    PickAppsScreenImpl(
        modifier = modifier,
        apps = allApps.value,
        onClick = viewModel::onAppPick,
        onSave = { viewModel.onSave(); onRoute() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickAppsScreenImpl(
    modifier: Modifier = Modifier,
    apps: PersistentList<AppInfo>,
    onClick: (Int) -> Unit,
    onSave: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.pick_apps)) }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = onSave,
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(stringResource(R.string.save))
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            itemsIndexed(apps) { index, app ->
                AppItemRow(
                    app = app,
                    checked = app.isPicked,
                    onCheckedChange = { onClick(index) }
                )
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

@Composable
@Preview(showBackground = true)
fun PickAppsScreen_Preview() {
    PickAppsScreenImpl(
        modifier = Modifier.fillMaxSize(),
        apps = persistentListOf(),
        onClick = {},
        onSave = {}
    )
}