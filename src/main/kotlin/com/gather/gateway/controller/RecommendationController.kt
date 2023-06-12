package com.gather.gateway.controller

import com.gather.gateway.config.RecommendationClient
import com.gather.gateway.model.EventType
import com.gather.gateway.model.RecommendationModel
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RecommendationController(
    private val recommendationClient: RecommendationClient
) {
    @GetMapping("users/me/recommend/events/{eventType}")
    suspend fun getRecommendation(
        authentication: Authentication,
        @PathVariable eventType: EventType
    ): List<RecommendationModel> = recommendationClient.getRecommendation(authentication.name.toLong(), eventType).collectList().awaitSingle()

    @GetMapping("users/me/recommend/events/{eventType}/group")
    suspend fun getGroupRecommendation(
        authentication: Authentication,
        @PathVariable eventType: EventType,
        @RequestParam users: List<Long>
    ): List<RecommendationModel> = recommendationClient.getGroupRecommendation(authentication.name.toLong(), eventType, users).collectList().awaitSingle()
}
