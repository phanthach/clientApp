package com.example.clientapp.Domain.Model.Response

import com.example.clientapp.Domain.Model.Model.Prediction

data class PlaceAutoCompleteResponse(
    val predictions: List<Prediction>,
    val execution_time: String?,
    val status: String
)

