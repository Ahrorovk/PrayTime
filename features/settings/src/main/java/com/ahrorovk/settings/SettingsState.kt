package com.ahrorovk.settings

import com.ahrorovk.core.model.Language

data class SettingsState(
    val language: Language = Language(0,"English","en")
)