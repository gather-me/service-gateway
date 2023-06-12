package com.gather.gateway.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class RecommendationModel(
    val id: Long,
    @JsonProperty("event_type")
    val eventType: EventType,
    val prediction: BigDecimal
)
