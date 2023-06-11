package com.gather.gateway.controller

import com.gather.gateway.config.UserClient
import com.gather.gateway.model.RegistrationRequest
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
class UserController(
    private val userClient: UserClient
) {
    @GetMapping("users/all")
    suspend fun getUsersByIds(
        @RequestParam userIds: List<Long>
    ): List<UserModel> = userClient.getUsersByIds(userIds).collectList().awaitSingle()

    @GetMapping("users/{userId}")
    suspend fun getUserById(
        @PathVariable userId: Long
    ): UserModel = userClient.getUserById(userId).awaitSingle()

    @PostMapping("register")
    suspend fun register(
        @RequestBody body: RegistrationRequest
    ) {
        userClient.register(body).awaitSingleOrNull()
    }

    @PostMapping("/users/me/follow")
    suspend fun follow(
        authentication: Authentication,
        @RequestParam followingUserId: Long
    ): Boolean {
        return userClient.follow(authentication.name.toLong(), followingUserId).awaitSingle()
    }

    @GetMapping("/users/{userId}/followers")
    suspend fun followers(
        @PathVariable userId: Long
    ): List<UserModel> {
        return userClient.getFollowers(userId).collectList().awaitSingle()
    }

    @GetMapping("/users/{userId}/followers/count")
    suspend fun followerCount(
        @PathVariable userId: Long
    ): Long {
        return userClient.getFollowersCount(userId).awaitSingle()
    }

    @GetMapping("/users/{userId}/followings")
    suspend fun followings(
        @PathVariable userId: Long
    ): List<UserModel> {
        return userClient.getFollowings(userId).collectList().awaitSingle()
    }

    @GetMapping("/users/{userId}/followings/count")
    suspend fun followingCount(
        @PathVariable userId: Long
    ): Long {
        return userClient.getFollowingsCount(userId).awaitSingle()
    }
}
