package com.gather.gateway.model

import java.time.Instant

data class InvitationModel(
    val id: Long,
    val event: EventModel,
    val user: UserModel,
    val date: Instant
)

data class EnrollmentRequestModel(
    val event: EventModel,
    val users: List<UserModel>
)
