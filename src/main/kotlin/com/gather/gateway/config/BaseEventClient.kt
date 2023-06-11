package com.gather.gateway.config

import com.gather.gateway.model.EnrollmentRequestModel
import com.gather.gateway.model.EventModel
import com.gather.gateway.model.InvitationModel
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import reactor.core.publisher.Flux

@Component
class BaseEventClient(
    @Qualifier("eventWebClient")
    private val client: WebClient
) {
    fun getFollowingUserEvents(userId: Long, page: Int): Flux<EventModel> =
        client
            .get()
            .uri("/users/{userId}/events/followings") {
                it.queryParam("page", page)
                    .build(userId)
            }
            .retrieve()
            .bodyToFlux()

    fun getUpcomingEvents(page: Int): Flux<EventModel> =
        client
            .get()
            .uri("events/upcoming") {
                it.queryParam("page", page)
                    .build()
            }
            .retrieve()
            .bodyToFlux()

    fun getCreatedEvents(userId: Long, page: Int): Flux<EventModel> =
        client
            .get()
            .uri("users/{userId}/events/created-events") {
                it.queryParam("page", page)
                    .build(userId)
            }
            .retrieve()
            .bodyToFlux()

    fun getPreviousEvents(userId: Long, page: Int): Flux<EventModel> =
        client
            .get()
            .uri("users/{userId}/events/previous") {
                it.queryParam("page", page)
                    .build(userId)
            }
            .retrieve()
            .bodyToFlux()

    fun getUnratedEvents(userId: Long, page: Int): Flux<EventModel> =
        client
            .get()
            .uri("users/{userId}/events/previous/unrated") {
                it.queryParam("page", page)
                    .build(userId)
            }
            .retrieve()
            .bodyToFlux()

    fun getInvitations(userId: Long): Flux<InvitationModel> =
        client
            .get()
            .uri("users/{userId}/events/invitations", userId)
            .retrieve()
            .bodyToFlux()

    fun getRequests(userId: Long): Flux<EnrollmentRequestModel> =
        client
            .get()
            .uri("users/{userId}/events/created-events/requests", userId)
            .retrieve()
            .bodyToFlux()
}
