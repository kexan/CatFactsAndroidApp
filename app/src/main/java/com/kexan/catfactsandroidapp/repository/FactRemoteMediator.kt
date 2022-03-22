package com.kexan.catfactsandroidapp.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kexan.catfactsandroidapp.api.ApiService
import dao.FactDao
import dao.FactRemoteKeyDao
import db.AppDb
import com.kexan.catfactsandroidapp.entity.FactEntity
import com.kexan.catfactsandroidapp.entity.FactRemoteKeyEntity
import com.kexan.catfactsandroidapp.entity.toEntity
import java.lang.Exception

@OptIn(ExperimentalPagingApi::class)
class FactRemoteMediator(
    private val service: ApiService,
    private val factDao: FactDao,
    private val remoteKeyDao: FactRemoteKeyDao,
    private val db: AppDb
) : RemoteMediator<Int, FactEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FactEntity>
    ): MediatorResult {
        try {
            val response = when (loadType) {
                LoadType.REFRESH -> {
                    service.getRandomFact(state.config.initialLoadSize)
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(false)
                }
                LoadType.APPEND -> {
                    return MediatorResult.Success(true)
                }
            }

            val body = response.body() ?: throw Exception()

            db.withTransaction {
                 when (loadType) {
                    LoadType.REFRESH -> {
                        remoteKeyDao.insert(
                            FactRemoteKeyEntity(
                                type = FactRemoteKeyEntity.KeyType.AFTER,
                                id = body.first().id
                            )
                        )
                    }
                    LoadType.APPEND -> {
                        remoteKeyDao.insert(
                            FactRemoteKeyEntity(
                                type = FactRemoteKeyEntity.KeyType.BEFORE,
                                id = body.last().id
                            )
                        )
                    }
                }
                factDao.insert(body.toEntity())
            }
            return MediatorResult.Success(endOfPaginationReached = body.isEmpty())
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}