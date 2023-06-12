package com.gather.gateway.config

import com.gather.gateway.model.EventType
import com.gather.gateway.model.RecommendationModel
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import reactor.core.publisher.Flux

@Component
class RecommendationClient(
    @Qualifier("recommendationWebClient")
    private val client: WebClient
) {
    fun getRecommendation(userId: Long, eventType: EventType): Flux<RecommendationModel> =
        client
            .get()
            .uri("users/{userId}/recommend/events/{eventType}", userId, eventType)
            .retrieve()
            .bodyToFlux()

    fun getGroupRecommendation(userId: Long, eventType: EventType, userIds: List<Long>): Flux<RecommendationModel> =
        client
            .get()
            .uri("users/{userId}/recommend/events/{eventType}/group") {
                it.queryParam("users", userIds)
                    .build(userId, eventType)
            }
            .retrieve()
            .bodyToFlux()
}
