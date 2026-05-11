package com.example.aplicacionemt.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoritoDao {

    @Query("SELECT * FROM favoritos")
    fun getAll(): LiveData<List<Favorito>>

    @Query("SELECT * FROM favoritos")
    suspend fun getAllList(): List<Favorito>

    @Query("SELECT * FROM favoritos WHERE stopId = :stopId LIMIT 1")
    fun getByStopId(stopId: Int): LiveData<Favorito?>

    @Query("SELECT * FROM favoritos WHERE stopId = :stopId LIMIT 1")
    suspend fun getByStopIdOnce(stopId: Int): Favorito?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorito: Favorito)

    @Delete
    suspend fun delete(favorito: Favorito)
}
