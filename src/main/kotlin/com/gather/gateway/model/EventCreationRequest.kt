package com.gather.gateway.model

import java.math.BigDecimal
import java.time.Instant

data class StagePlayEventCreationRequest(
    val title: String,
    val description: String? = null,
    val capacity: Long? = null,
    val price: BigDecimal? = null,
    val isPrivate: Boolean,
    val category: StagePlayCategory,
    val startDate: Instant,
    val endDate: Instant,
    val locationModel: LocationModel
)

data class NatureEventCreationRequest(
    val title: String,
    val description: String? = null,
    val capacity: Long? = null,
    val price: BigDecimal? = null,
    val isPrivate: Boolean,
    val category: NatureCategory,
    val startDate: Instant,
    val endDate: Instant,
    val locationModel: LocationModel
)

data class SportEventCreationRequest(
    val title: String,
    val description: String? = null,
    val capacity: Long? = null,
    val price: BigDecimal? = null,
    val isPrivate: Boolean,
    val category: SportCategory,
    val startDate: Instant,
    val endDate: Instant,
    val locationModel: LocationModel
)

data class MusicalEventCreationRequest(
    val title: String,
    val description: String? = null,
    val capacity: Long? = null,
    val price: BigDecimal? = null,
    val isPrivate: Boolean,
    val category: MusicalCategory,
    val startDate: Instant,
    val endDate: Instant,
    val locationModel: LocationModel,
    val artist: String
)
