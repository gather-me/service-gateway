package com.gather.gateway.controller

import com.gather.gateway.config.BaseEventClient
import com.gather.gateway.model.EnrollmentRequestModel
import com.gather.gateway.model.EventModel
import com.gather.gateway.model.InvitationModel
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class BaseEventController(
    private val baseEventClient: BaseEventClient
) {
    @GetMapping("users/me/events/followings")
    suspend fun getFollowingUserEvents(
        authentication: Authentication,
        @RequestParam page: Int
    ): List<EventModel> = baseEventClient.getFollowingUserEvents(authentication.name.toLong(), page).collectList().awaitSingle()

    @GetMapping("events/upcoming")
    suspend fun getUpcomingEvents(
        @RequestParam page: Int
    ): List<EventModel> = baseEventClient.getUpcomingEvents(page).collectList().awaitSingle()

    @GetMapping("users/{userId}/events/created-events")
    suspend fun getCreatedEvents(
        @PathVariable userId: Long,
        @RequestParam page: Int
    ): List<EventModel> = baseEventClient.getCreatedEvents(userId, page).collectList().awaitSingle()

    @GetMapping("users/{userId}/events/previous")
    suspend fun getPreviousEvents(
        @PathVariable userId: Long,
        @RequestParam page: Int
    ): List<EventModel> = baseEventClient.getPreviousEvents(userId, page).collectList().awaitSingle()

    @GetMapping("users/me/events/previous/unrated")
    suspend fun getUnratedEvents(
        authentication: Authentication,
        @RequestParam page: Int
    ): List<EventModel> = baseEventClient.getUnratedEvents(authentication.name.toLong(), page).collectList().awaitSingle()

    @GetMapping("users/me/events/invitations")
    suspend fun getInvitations(
        authentication: Authentication
    ): List<InvitationModel> = baseEventClient.getInvitations(authentication.name.toLong()).collectList().awaitSingle()

    @GetMapping("users/me/events/created-events/requests")
    suspend fun getRequests(
        authentication: Authentication
    ): List<EnrollmentRequestModel> = baseEventClient.getRequests(authentication.name.toLong()).collectList().awaitSingle()
}
