package com.gather.gateway.config

import com.gather.gateway.model.StagePlayEventCreationRequest
import com.gather.gateway.model.StagePlayEventModel
import com.gather.gateway.model.UserModel
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class StagePlayEventClient(
    @Qualifier("eventWebClient")
    private val client: WebClient
) {
    fun get(id: Long): Mono<StagePlayEventModel> =
        client
            .get()
            .uri("events/stagePlay/{id}", id)
            .retrieve()
            .bodyToMono()

    fun getEvents(ids: List<Long>): Flux<StagePlayEventModel> =
        client
            .get()
            .uri("events/stagePlay") {
                it.queryParam("ids", ids)
                    .build()
            }
            .retrieve()
            .bodyToFlux()

    fun getInvitingUsers(id: Long, page: Int): Flux<UserModel> =
        client
            .get()
            .uri("events/stagePlay/{id}/inviting") {
                it.queryParam("page", page)
                    .build(id)
            }
            .retrieve()
            .bodyToFlux()

    fun create(id: Long, body: StagePlayEventCreationRequest): Mono<Void> =
        client
            .post()
            .uri("users/{userId}/events/stagePlay/create", id)
            .bodyValue(body)
            .retrieve()
            .bodyToMono()

    fun enroll(userId: Long, eventId: Long): Mono<Void> =
        client
            .post()
            .uri("users/{userId}/enroll/events/stagePlay/{id}", userId, eventId)
            .retrieve()
            .bodyToMono()

    fun respondEnrollmentRequest(creatorId: Long, userId: Long, eventId: Long, response: Boolean): Mono<Void> =
        client
            .post()
            .uri("users/{creatorId}/events/stagePlay/{eventId}/enrollment-requests/{userId}") {
                it.queryParam("response", response)
                    .build(userId)
            }
            .retrieve()
            .bodyToMono()
    fun participants(id: Long, enrolled: Boolean): Flux<UserModel> =
        client
            .get()
            .uri("events/stagePlay/{id}/participants") {
                it.queryParam("enrolled", enrolled)
                    .build(id)
            }
            .retrieve()
            .bodyToFlux()

    fun invite(userId: Long, invitedUserId: Long, eventId: Long): Mono<Void> =
        client
            .post()
            .uri("users/{userId}/events/stagePlay/{eventId}/invite") {
                it.queryParam("invitedUserId", invitedUserId)
                    .build(userId, eventId)
            }
            .retrieve()
            .bodyToMono()

    fun respond(userId: Long, invitationId: Long, response: Boolean): Mono<Void> =
        client
            .post()
            .uri("users/{userId}/invitations/stagePlay/{invitationId}/respond") {
                it.queryParam("response", response)
                    .build(userId, invitationId)
            }
            .retrieve()
            .bodyToMono()

    fun rateEvent(userId: Long, eventId: Long, rate: Int): Mono<Void> =
        client
            .post()
            .uri("/users/{userId}/events/stagePlay/{eventId}/rate") {
                it.queryParam("rate", rate)
                    .build(userId, eventId)
            }
            .retrieve()
            .bodyToMono()
}
