package com.kexan.catfactsandroidapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kexan.catfactsandroidapp.dto.Fact

@Entity
data class FactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val _id: String,
    val user: String = "",
    val text: String,
    val type: String,
    val updatedAt: String
) {
    fun toDto() = Fact(id, _id, user, text, type, updatedAt)

    companion object {
        fun fromDto(dto: Fact) =
            FactEntity(
                dto.id,
                dto._id,
                dto.user,
                dto.text,
                dto.type,
                dto.updatedAt
            )
    }
}

fun List<FactEntity>.toDto(): List<Fact> = map(FactEntity::toDto)
fun List<Fact>.toEntity(): List<FactEntity> = map(FactEntity.Companion::fromDto)
fun Fact.toEntity(): FactEntity = FactEntity.fromDto(this)