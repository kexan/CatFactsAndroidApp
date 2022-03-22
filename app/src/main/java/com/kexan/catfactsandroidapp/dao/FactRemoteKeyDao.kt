package dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kexan.catfactsandroidapp.entity.FactRemoteKeyEntity

@Dao
interface FactRemoteKeyDao {
    @Query("SELECT COUNT(*) == 0 FROM FactRemoteKeyEntity")
    suspend fun isEmpty(): Boolean

    @Query("SELECT MAX(id) FROM FactRemoteKeyEntity")
    suspend fun max(): Long?

    @Query("SELECT MIN(id) FROM FactRemoteKeyEntity")
    suspend fun min(): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(key: FactRemoteKeyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: List<FactRemoteKeyEntity>)

    @Query("DELETE FROM FactRemoteKeyEntity")
    suspend fun removeAll()
}