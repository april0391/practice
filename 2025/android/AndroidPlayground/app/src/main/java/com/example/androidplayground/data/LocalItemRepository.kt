package com.example.androidplayground.data

import kotlinx.coroutines.flow.Flow

class LocalItemRepository(private val itemDao: ItemDao) : ItemRepository {

    override fun findAllStream(): Flow<List<Item>> = itemDao.findAll()

    override fun findOneByIdStream(id: Int): Flow<Item> = itemDao.findOneById(id)

    override suspend fun insert(item: Item) = itemDao.insert(item)

    override suspend fun update(item: Item) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: Item) {
        TODO("Not yet implemented")
    }
}