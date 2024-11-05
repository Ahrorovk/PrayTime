package com.ahrorovk.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.state.StateDialog
import com.maxkeppeler.sheets.state.models.ProgressIndicator
import com.maxkeppeler.sheets.state.models.State
import com.maxkeppeler.sheets.state.models.StateConfig
import kotlinx.coroutines.delay

@Composable
fun CustomProgressIndicator(
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            val progress = remember { mutableStateOf(0f) }
            val progressAnimated = animateFloatAsState(
                targetValue = progress.value, tween(1000),
                label = ""
            ).value
            LaunchedEffect(Unit) {
                progress.value = 1f
                delay(1000)
            }

            val state = State.Loading(
                stringResource(id = R.string.wait_a_moment),
                ProgressIndicator.Circular(progressAnimated)
            )
            StateDialog(
                state = rememberSheetState(visible = isLoading, onCloseRequest = { }),
                config = StateConfig(state = state)
            )
        }
    }
}