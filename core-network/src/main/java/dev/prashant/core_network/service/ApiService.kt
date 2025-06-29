package dev.prashant.core_network.service

import dev.prashant.core_network.dtos.characterDetails.CharacterDetailsResponse
import dev.prashant.core_network.dtos.characters.CharacterResponse
import dev.prashant.core_network.dtos.planets.PlanetResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //https://dragonball-api.com/api/characters?limit=5
    //https://dragonball-api.com/api/characters/1
    //https://dragonball-api.com/api/planets/1

    @GET("api/characters")
    suspend fun getAllCharacters(
        @Query("limit") limit: Int = 60
    ): CharacterResponse

    @GET("api/characters/{id}")
    suspend fun getCharacterDetails(
        @Path("id") id : Int
    ): CharacterDetailsResponse

    @GET("api/planets/{id}")
    suspend fun getPlanetCharacters(
        @Path("id") id: Int
    ): PlanetResponse
}