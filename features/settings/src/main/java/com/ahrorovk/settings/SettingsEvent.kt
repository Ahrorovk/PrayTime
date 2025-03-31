package com.ahrorovk.settings

import com.ahrorovk.core.model.Language

sealed class SettingsEvent {
    data class OnLanguageChange(val newLanguage: Language): SettingsEvent()
}