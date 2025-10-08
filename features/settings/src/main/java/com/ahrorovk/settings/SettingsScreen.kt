package com.ahrorovk.settings

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahrorovk.core.getLanguages
import com.ahrorovk.core.model.Language
import com.ahrorovk.settings.components.CommonSettingsCard
import java.util.Locale

@Composable
fun SettingsScreen(
//    state: SettingsState,
    viewModel: SettingsViewModel = hiltViewModel()
) {
//    val currentLocale by remember { mutableStateOf(Locale.getDefault().language) }

    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    var isDialogOpen by remember { mutableStateOf(false) }

    LaunchedEffect(state.language) {
        val newLocale = Locale(state.language.shortCut)

        val currentLang = context.resources.configuration.locales.get(0).language
        if (currentLang != state.language.shortCut) {
            LocaleHelper.setLocale(context, newLocale)
            (context as? Activity)?.recreate()
        }
    }

    Image(
        painter = painterResource(com.ahrorovk.core.R.drawable.stambul_mosque),
        contentDescription = "Settings Background",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(22.dp)
    ) {
        Text(
            text = stringResource(com.ahrorovk.core.R.string.location),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 7.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        CommonSettingsCard(
            icon = ImageVector.vectorResource(com.ahrorovk.core.R.drawable.location),
            text = stringResource(com.ahrorovk.core.R.string.sughd_khujand),
            color = Color(0xFFD9D9D9),
            onClick = {}
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = stringResource(com.ahrorovk.core.R.string.system_settings),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 7.dp)
        )

        Spacer(modifier = Modifier.height(9.dp))

        CommonSettingsCard(
            icon = ImageVector.vectorResource(com.ahrorovk.core.R.drawable.time),
            text = stringResource(com.ahrorovk.core.R.string.prayer_time),
            color = Color(0xFF3DCE23),
            onClick = {}
        )

        Spacer(modifier = Modifier.height(9.dp))

        CommonSettingsCard(
            icon = ImageVector.vectorResource(com.ahrorovk.core.R.drawable.notification),
            text = stringResource(com.ahrorovk.core.R.string.notification_and_sound),
            color = Color(0xFFFC6060),
            onClick = {}
        )

        Spacer(modifier = Modifier.height(9.dp))

        CommonSettingsCard(
            icon = ImageVector.vectorResource(com.ahrorovk.core.R.drawable.language),
            text = stringResource(com.ahrorovk.core.R.string.language),
            color = Color(0xFFD9D9D9),
            onClick = {
                isDialogOpen = true
            }
        )
        if (isDialogOpen) {
            LanguagePickerDialog(
                languages = getLanguages(),
                currentLanguage = state.language,
                onLanguageSelected = { selectedLanguage ->
                    isDialogOpen = false
                    viewModel.onEvent(SettingsEvent.OnLanguageChange(selectedLanguage))
                },
                onDismiss = { isDialogOpen = false }
            )
        }
    }
}

@Composable
fun LanguagePickerDialog(
    languages: List<Language>,
    currentLanguage: Language,
    onLanguageSelected: (Language) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(com.ahrorovk.core.R.string.choose_language))
        },
        text = {
            Column {
                languages.forEach { language ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onLanguageSelected(language) }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = language.id == currentLanguage.id,
                            onClick = { onLanguageSelected(language) }
                        )
                        Text(
                            text = stringResource(id = if(language.shortCut == "en") com.ahrorovk.core.R.string.english else com.ahrorovk.core.R.string.russian),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(com.ahrorovk.core.R.string.cancel))
            }
        }
    )
}
