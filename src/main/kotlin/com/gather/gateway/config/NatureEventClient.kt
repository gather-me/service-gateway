package com.gather.gateway.config

import com.gather.gateway.model.NatureEventCreationRequest
import com.gather.gateway.model.NatureEventModel
import com.gather.gateway.model.UserModel
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class NatureEventClient(
    @Qualifier("eventWebClient")
    private val client: WebClient
) {
    fun get(id: Long): Mono<NatureEventModel> =
        client
            .get()
            .uri("events/nature/{id}", id)
            .retrieve()
            .bodyToMono()

    fun getEvents(ids: List<Long>): Flux<NatureEventModel> =
        client
            .get()
            .uri("events/nature") {
                it.queryParam("ids", ids)
                    .build()
            }
            .retrieve()
            .bodyToFlux()

    fun getInvitingUsers(id: Long, page: Int): Flux<UserModel> =
        client
            .get()
            .uri("events/nature/{id}/inviting") {
                it.queryParam("page", page)
                    .build(id)
            }
            .retrieve()
            .bodyToFlux()

    fun create(id: Long, body: NatureEventCreationRequest): Mono<Void> =
        client
            .post()
            .uri("users/{userId}/events/nature/create", id)
            .bodyValue(body)
            .retrieve()
            .bodyToMono()

    fun enroll(userId: Long, eventId: Long): Mono<Void> =
        client
            .post()
            .uri("users/{userId}/enroll/events/nature/{id}", userId, eventId)
            .retrieve()
            .bodyToMono()

    fun respondEnrollmentRequest(creatorId: Long, userId: Long, eventId: Long, response: Boolean): Mono<Void> =
        client
            .post()
            .uri("users/{creatorId}/events/nature/{eventId}/enrollment-requests/{userId}") {
                it.queryParam("response", response)
                    .build(creatorId, eventId, userId)
            }
            .retrieve()
            .bodyToMono()
    fun participants(id: Long, enrolled: Boolean): Flux<UserModel> =
        client
            .get()
            .uri("events/nature/{id}/participants") {
                it.queryParam("enrolled", enrolled)
                    .build(id)
            }
            .retrieve()
            .bodyToFlux()

    fun invite(userId: Long, invitedUserId: Long, eventId: Long): Mono<Void> =
        client
            .post()
            .uri("users/{userId}/events/nature/{eventId}/invite") {
                it.queryParam("invitedUserId", invitedUserId)
                    .build(userId, eventId)
            }
            .retrieve()
            .bodyToMono()

    fun respond(userId: Long, invitationId: Long, response: Boolean): Mono<Void> =
        client
            .post()
            .uri("users/{userId}/invitations/nature/{invitationId}/respond") {
                it.queryParam("response", response)
                    .build(userId, invitationId)
            }
            .retrieve()
            .bodyToMono()

    fun rateEvent(userId: Long, eventId: Long, rate: Int): Mono<Void> =
        client
            .post()
            .uri("/users/{userId}/events/nature/{eventId}/rate") {
                it.queryParam("rate", rate)
                    .build(userId, eventId)
            }
            .retrieve()
            .bodyToMono()
}
