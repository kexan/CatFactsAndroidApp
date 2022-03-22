package com.kexan.catfactsandroidapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FactRemoteKeyEntity(
    @PrimaryKey
    val type: KeyType,
    val id: Long
) {
    enum class KeyType {
        AFTER, BEFORE
    }
}