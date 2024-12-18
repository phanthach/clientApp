package com.example.clientapp.Data.Network

import com.example.clientapp.Domain.Model.Model.DistanceMatrixResponse
import com.example.clientapp.Domain.Model.Model.RouteLine
import com.example.clientapp.Domain.Model.Response.LocationResponse
import com.example.clientapp.Domain.Model.Response.PlaceAutoCompleteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiGoongMapService {
//    vNlQwVhET6mwbHbteAXAUUIyWyl6IFAI3BraxuYj
//    bzX0L7RV0SW6Q9FVAYzrCiwzOYzErRh1hNZ4PqU4
//    V5ijRnWBS3k5rUHKREi3wqZzdi0jHiadYa1ZmLpP

    @GET("Place/AutoComplete?api_key=bzX0L7RV0SW6Q9FVAYzrCiwzOYzErRh1hNZ4PqU4")
    suspend fun getPlaceAutoComplete(@Query("input") input: String?): PlaceAutoCompleteResponse

    @GET("Place/Detail?api_key=bzX0L7RV0SW6Q9FVAYzrCiwzOYzErRh1hNZ4PqU4")
    suspend fun getPlaceDetail(@Query("place_id") place_id: String?): LocationResponse

    @GET("distancematrix?api_key=bzX0L7RV0SW6Q9FVAYzrCiwzOYzErRh1hNZ4PqU4")
    suspend fun getDistanceMatrix(@Query("origins") origins: String?,
                                  @Query("destinations") destinations: String?,
                                    @Query("vehicle") vehicle:String) : DistanceMatrixResponse
    @GET("direction?api_key=bzX0L7RV0SW6Q9FVAYzrCiwzOYzErRh1hNZ4PqU4")
    suspend fun getDirection(@Query("origin") origin: String?,
                             @Query("destination") destination: String?,
                             @Query("vehicle") vehicle:String): RouteLine

}