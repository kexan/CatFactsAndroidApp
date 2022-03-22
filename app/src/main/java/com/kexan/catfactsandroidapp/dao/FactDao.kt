package dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kexan.catfactsandroidapp.entity.FactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FactDao {
    @Query("SELECT * FROM FactEntity ORDER BY id DESC")
    fun pagingSource(): PagingSource<Int, FactEntity>

    @Query("SELECT * FROM FactEntity ORDER BY id DESC")
    fun getAll(): Flow<List<FactEntity>>

    @Query("SELECT COUNT(*) == 0 FROM FactEntity")
    suspend fun isEmpty(): Boolean

    @Query("SELECT COUNT(*) FROM FactEntity")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: FactEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(posts: List<FactEntity>)
}