package com.gather.gateway.model

import java.math.BigDecimal
import java.time.Instant

data class StagePlayEventModel(
    val id: Long,
    val title: String,
    val description: String?,
    val creator: UserModel,
    val capacity: Long?,
    val enrolled: Long,
    val price: BigDecimal?,
    val isPrivate: Boolean,
    val category: StagePlayCategory,
    val startDate: Instant,
    val endDate: Instant,
    val locationModel: LocationModel
)

enum class StagePlayCategory {
    Theatre, StandUp
}
