package repository

import androidx.paging.PagingData
import com.kexan.catfactsandroidapp.dto.Fact
import kotlinx.coroutines.flow.Flow

interface FactRepository {
    val data: Flow<PagingData<Fact>>
    suspend fun getRandomFact(amount: Int)
    suspend fun getFactById(_id: String)
}