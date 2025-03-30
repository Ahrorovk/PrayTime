package com.ahrorovk.settings

import com.ahrorovk.core.model.Language

sealed class SettingsEvent {
    data class CHANGE_LANGUAGE(val newLanguage: Language): SettingsEvent()
}