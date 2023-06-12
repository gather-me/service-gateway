package com.gather.gateway.config

import com.gather.gateway.model.MusicalEventCreationRequest
import com.gather.gateway.model.MusicalEventModel
import com.gather.gateway.model.UserModel
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class MusicalEventClient(
    @Qualifier("eventWebClient")
    private val client: WebClient
) {
    fun get(id: Long): Mono<MusicalEventModel> =
        client
            .get()
            .uri("events/musical/{id}", id)
            .retrieve()
            .bodyToMono()

    fun getEvents(ids: List<Long>): Flux<MusicalEventModel> =
        client
            .get()
            .uri("events/musical") {
                it.queryParam("ids", ids)
                    .build()
            }
            .retrieve()
            .bodyToFlux()

    fun getInvitingUsers(id: Long, page: Int): Flux<UserModel> =
        client
            .get()
            .uri("events/musical/{id}/inviting") {
                it.queryParam("page", page)
                    .build(id)
            }
            .retrieve()
            .bodyToFlux()

    fun create(id: Long, body: MusicalEventCreationRequest): Mono<Void> =
        client
            .post()
            .uri("users/{userId}/events/musical/create", id)
            .bodyValue(body)
            .retrieve()
            .bodyToMono()

    fun enroll(userId: Long, eventId: Long): Mono<Void> =
        client
            .post()
            .uri("users/{userId}/enroll/events/musical/{id}", userId, eventId)
            .retrieve()
            .bodyToMono()

    fun respondEnrollmentRequest(creatorId: Long, userId: Long, eventId: Long, response: Boolean): Mono<Void> =
        client
            .post()
            .uri("users/{creatorId}/events/musical/{eventId}/enrollment-requests/{userId}") {
                it.queryParam("response", response)
                    .build(userId)
            }
            .retrieve()
            .bodyToMono()
    fun participants(id: Long, enrolled: Boolean): Flux<UserModel> =
        client
            .get()
            .uri("events/musical/{id}/participants") {
                it.queryParam("enrolled", enrolled)
                    .build(id)
            }
            .retrieve()
            .bodyToFlux()

    fun invite(userId: Long, invitedUserId: Long, eventId: Long): Mono<Void> =
        client
            .post()
            .uri("users/{userId}/events/musical/{eventId}/invite") {
                it.queryParam("invitedUserId", invitedUserId)
                    .build(userId, eventId)
            }
            .retrieve()
            .bodyToMono()

    fun respond(userId: Long, invitationId: Long, response: Boolean): Mono<Void> =
        client
            .post()
            .uri("users/{userId}/invitations/musical/{invitationId}/respond") {
                it.queryParam("response", response)
                    .build(userId, invitationId)
            }
            .retrieve()
            .bodyToMono()

    fun rateEvent(userId: Long, eventId: Long, rate: Int): Mono<Void> =
        client
            .post()
            .uri("/users/{userId}/events/musical/{eventId}/rate") {
                it.queryParam("rate", rate)
                    .build(userId, eventId)
            }
            .retrieve()
            .bodyToMono()
}
