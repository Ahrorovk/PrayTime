package com.ahrorovk.model.location

data class Informative(
    val description: String,
    val geonameId: Int,
    val isoCode: String,
    val isoName: String,
    val name: String,
    val order: Int,
    val wikidataId: String
)