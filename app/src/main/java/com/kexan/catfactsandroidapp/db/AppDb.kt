package db

import androidx.room.Database
import androidx.room.RoomDatabase
import dao.FactDao
import dao.FactRemoteKeyDao
import com.kexan.catfactsandroidapp.entity.FactEntity
import com.kexan.catfactsandroidapp.entity.FactRemoteKeyEntity

@Database(
    entities = [FactEntity::class, FactRemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract fun factDao(): FactDao
    abstract fun factRemoteKeyDao(): FactRemoteKeyDao
}