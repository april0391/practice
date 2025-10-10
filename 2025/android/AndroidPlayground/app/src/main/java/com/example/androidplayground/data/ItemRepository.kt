package com.example.androidplayground.data

import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun findAllStream(): Flow<List<Item>>

    fun findOneByIdStream(id: Int): Flow<Item>

    suspend fun insert(item: Item)

    suspend fun update(item: Item)

    suspend fun delete(item: Item)
}