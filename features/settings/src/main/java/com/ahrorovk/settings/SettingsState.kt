package com.ahrorovk.settings

import com.ahrorovk.core.model.Language

data class SettingsState(
    val language: Language = Language(0, "English", "en"),
    val availableLanguages: List<Language> = listOf(
        Language(0, "English", "en"),
        Language(1, "Russian", "ru")
    )
)