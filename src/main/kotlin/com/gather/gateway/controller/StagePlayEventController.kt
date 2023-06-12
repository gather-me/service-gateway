package com.gather.gateway.controller

import com.gather.gateway.config.StagePlayEventClient
import com.gather.gateway.model.StagePlayEventCreationRequest
import com.gather.gateway.model.StagePlayEventModel
import com.gather.gateway.model.UserModel
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class StagePlayEventController(
    private val eventClient: StagePlayEventClient
) {
    @GetMapping("events/stagePlay/{id}")
    suspend fun get(
        @PathVariable id: Long
    ): StagePlayEventModel {
        return eventClient.get(id).awaitSingle()
    }

    @GetMapping("events/stagePlay")
    suspend fun get(
        @RequestParam ids: List<Long>
    ): List<StagePlayEventModel> {
        return eventClient.getEvents(ids).collectList().awaitSingle()
    }

    @GetMapping("events/stagePlay/{id}/inviting")
    suspend fun getInvitingUsers(
        @PathVariable id: Long,
        @RequestParam page: Int
    ): List<UserModel> {
        return eventClient.getInvitingUsers(id, page).collectList().awaitSingle()
    }

    @PostMapping("users/me/events/stagePlay/create")
    suspend fun create(
        authentication: Authentication,
        @RequestBody body: StagePlayEventCreationRequest
    ) {
        eventClient.create(authentication.name.toLong(), body).awaitSingleOrNull()
    }

    @PostMapping("users/me/enroll/events/stagePlay/{id}")
    suspend fun enroll(
        authentication: Authentication,
        @PathVariable id: Long
    ) {
        eventClient.enroll(authentication.name.toLong(), id).awaitSingleOrNull()
    }

    @PostMapping("users/me/events/stagePlay/{eventId}/enrollment-requests/{userId}")
    suspend fun respondEnrollmentRequest(
        authentication: Authentication,
        @PathVariable eventId: Long,
        @PathVariable userId: Long,
        @RequestParam response: Boolean
    ) {
        eventClient.respondEnrollmentRequest(authentication.name.toLong(), userId, eventId, response).awaitSingleOrNull()
    }

    @GetMapping("events/stagePlay/{id}/participants")
    suspend fun participants(
        @PathVariable id: Long,
        @RequestParam enrolled: Boolean
    ): List<UserModel> {
        return eventClient.participants(id, enrolled).collectList().awaitSingle()
    }

    @PostMapping("users/me/events/stagePlay/{eventId}/invite")
    suspend fun invite(
        authentication: Authentication,
        @PathVariable eventId: Long,
        @RequestParam invitedUserId: Long
    ) {
        eventClient.invite(authentication.name.toLong(), invitedUserId, eventId).awaitSingleOrNull()
    }

    @PostMapping("users/me/invitations/stagePlay/{invitationId}/respond")
    suspend fun respond(
        authentication: Authentication,
        @PathVariable invitationId: Long,
        @RequestParam response: Boolean
    ) {
        eventClient.respond(authentication.name.toLong(), invitationId, response).awaitSingleOrNull()
    }

    @PostMapping("users/me/events/stagePlay/{eventId}/rate")
    suspend fun rateEvent(
        authentication: Authentication,
        @PathVariable eventId: Long,
        @RequestParam rate: Int
    ) {
        eventClient.rateEvent(authentication.name.toLong(), eventId, rate).awaitSingleOrNull()
    }
}
