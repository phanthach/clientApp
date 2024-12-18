package com.example.clientapp.Domain.Model.Model


data class RouteLine(
    val routes: List<Routee>?
)
data class Routee(
    val overview_polyline: OverviewPolyline
)
data class OverviewPolyline(
    val points: String?
)