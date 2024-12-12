package com.example.clientapp.Domain.Model.Model

data class Prediction(
    val description: String,
    val place_id: String,
    val reference: String,
    val has_children: Boolean?,
    val types: List<String>,
    val distance_meters: Int?
)