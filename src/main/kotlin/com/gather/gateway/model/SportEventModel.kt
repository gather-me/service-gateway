package com.gather.gateway.model

import java.math.BigDecimal
import java.time.Instant

data class SportEventModel(
    val id: Long,
    val eventType: EventType = EventType.Sport,
    val title: String,
    val description: String?,
    val creator: UserModel,
    val capacity: Long?,
    val enrolled: Long,
    val price: BigDecimal?,
    val isPrivate: Boolean,
    val category: SportCategory,
    val startDate: Instant,
    val endDate: Instant,
    val locationModel: LocationModel
)

enum class SportCategory {
    Football, Basketball, Volleyball, Jogging
}
