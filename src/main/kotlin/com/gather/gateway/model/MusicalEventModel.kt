package com.gather.gateway.model

import java.math.BigDecimal
import java.time.Instant

data class MusicalEventModel(
    val id: Long,
    val title: String,
    val description: String?,
    val creator: UserModel,
    val capacity: Long?,
    val enrolled: Long,
    val price: BigDecimal?,
    val isPrivate: Boolean,
    val category: MusicalCategory,
    val startDate: Instant,
    val endDate: Instant,
    val locationModel: LocationModel,
    val artist: String
)

enum class MusicalCategory {
    Concert, Festival
}
