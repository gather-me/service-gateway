package com.gather.gateway.config

import com.gather.gateway.model.SportEventCreationRequest
import com.gather.gateway.model.SportEventModel
import com.gather.gateway.model.UserModel
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class SportEventClient(
    @Qualifier("eventWebClient")
    private val client: WebClient
) {
    fun get(id: Long): Mono<SportEventModel> =
        client
            .get()
            .uri("events/sport/{id}", id)
            .retrieve()
            .bodyToMono()

    fun getEvents(ids: List<Long>): Flux<SportEventModel> =
        client
            .get()
            .uri("events/sport") {
                it.queryParam("ids", ids)
                    .build()
            }
            .retrieve()
            .bodyToFlux()

    fun getInvitingUsers(id: Long, page: Int): Flux<UserModel> =
        client
            .get()
            .uri("events/sport/{id}/inviting") {
                it.queryParam("page", page)
                    .build(id)
            }
            .retrieve()
            .bodyToFlux()

    fun create(id: Long, body: SportEventCreationRequest): Mono<Void> =
        client
            .post()
            .uri("users/{userId}/events/sport/create", id)
            .bodyValue(body)
            .retrieve()
            .bodyToMono()

    fun enroll(userId: Long, eventId: Long): Mono<Void> =
        client
            .post()
            .uri("users/{userId}/enroll/events/sport/{id}", userId, eventId)
            .retrieve()
            .bodyToMono()

    fun respondEnrollmentRequest(creatorId: Long, userId: Long, eventId: Long, response: Boolean): Mono<Void> =
        client
            .post()
            .uri("users/{creatorId}/events/sport/{eventId}/enrollment-requests/{userId}") {
                it.queryParam("response", response)
                    .build(userId)
            }
            .retrieve()
            .bodyToMono()
    fun participants(id: Long, enrolled: Boolean): Flux<UserModel> =
        client
            .get()
            .uri("events/sport/{id}/participants") {
                it.queryParam("enrolled", enrolled)
                    .build(id)
            }
            .retrieve()
            .bodyToFlux()

    fun invite(userId: Long, invitedUserId: Long, eventId: Long): Mono<Void> =
        client
            .post()
            .uri("users/{userId}/events/sport/{eventId}/invite") {
                it.queryParam("invitedUserId", invitedUserId)
                    .build(userId, eventId)
            }
            .retrieve()
            .bodyToMono()

    fun respond(userId: Long, invitationId: Long, response: Boolean): Mono<Void> =
        client
            .post()
            .uri("users/{userId}/invitations/sport/{invitationId}/respond") {
                it.queryParam("response", response)
                    .build(userId, invitationId)
            }
            .retrieve()
            .bodyToMono()

    fun rateEvent(userId: Long, eventId: Long, rate: Int): Mono<Void> =
        client
            .post()
            .uri("/users/{userId}/events/sport/{eventId}/rate") {
                it.queryParam("rate", rate)
                    .build(userId, eventId)
            }
            .retrieve()
            .bodyToMono()
}
