package com.gather.gateway.model

import java.math.BigDecimal
import java.time.Instant

data class NatureEventModel(
    val id: Long,
    val eventType: EventType = EventType.Nature,
    val title: String,
    val description: String?,
    val creator: UserModel,
    val capacity: Long?,
    val enrolled: Long,
    val price: BigDecimal?,
    val isPrivate: Boolean,
    val category: NatureCategory,
    val startDate: Instant,
    val endDate: Instant,
    val locationModel: LocationModel
)

enum class NatureCategory {
    Camp, Hiking
}
