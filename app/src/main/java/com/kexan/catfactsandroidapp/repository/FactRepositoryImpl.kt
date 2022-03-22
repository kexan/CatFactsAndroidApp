package com.kexan.catfactsandroidapp.repository

import androidx.paging.*
import com.kexan.catfactsandroidapp.api.ApiService
import dao.FactDao
import dao.FactRemoteKeyDao
import db.AppDb
import com.kexan.catfactsandroidapp.dto.Fact
import com.kexan.catfactsandroidapp.entity.FactEntity
import com.kexan.catfactsandroidapp.entity.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import repository.FactRepository
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FactRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val factDao: FactDao,
    remoteKeyDao: FactRemoteKeyDao,
    appDb: AppDb
) : FactRepository {

    @OptIn(ExperimentalPagingApi::class)
    override val data: Flow<PagingData<Fact>> = Pager(
        config = PagingConfig(pageSize = 5),
        remoteMediator = FactRemoteMediator(api, factDao, remoteKeyDao, appDb),
        pagingSourceFactory = factDao::pagingSource
    ).flow.map { pagingData ->
        pagingData.map(FactEntity::toDto)
    }

    override suspend fun getRandomFact(amount: Int) {
        try {
            val response = api.getRandomFact(amount)
            if (!response.isSuccessful) {
                throw Exception()
            }
            val body = response.body() ?: throw Exception()
            factDao.insert(body.toEntity())
        } catch (e: IOException) {
            throw Exception()
        } catch (e: Exception) {
            throw Exception()
        }
    }

    override suspend fun getFactById(_id: String) {
        try {
            val response = api.getFactById(_id)
            if (!response.isSuccessful) {
                throw Exception()
            }
            val body = response.body() ?: throw Exception()
            factDao.insert(body.toEntity())
        } catch (e: IOException) {
            throw Exception()
        } catch (e: Exception) {
            throw Exception()
        }
    }
}