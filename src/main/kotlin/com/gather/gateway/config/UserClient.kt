package com.gather.gateway.config

import com.gather.gateway.model.RegistrationRequest
import com.gather.gateway.model.UserModel
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class UserClient(
    @Qualifier("userWebClient")
    private val client: WebClient
) {
    fun register(request: RegistrationRequest): Mono<Void> =
        client
            .post()
            .uri("/register")
            .retrieve()
            .bodyToMono()

    fun getUserFollowings(userId: Long): Flux<UserModel> =
        client
            .get()
            .uri("/users/{userId}/followings", userId)
            .retrieve()
            .bodyToFlux()

    fun getUserById(userId: Long): Mono<UserModel> =
        client
            .get()
            .uri("/users/{userId}", userId)
            .retrieve()
            .bodyToMono()

    fun getUsersByIds(userIds: List<Long>): Flux<UserModel> =
        client
            .get()
            .uri("/users/all") {
                it.queryParam("userIds", userIds).build()
            }
            .retrieve()
            .bodyToFlux()

    fun follow(userId: Long, followingUserId: Long): Mono<Boolean> =
        client
            .post()
            .uri("/users/{userId}/follow") {
                it.queryParam("followingUserId", followingUserId).build(userId)
            }
            .retrieve()
            .bodyToMono()

    fun getFollowers(userId: Long): Flux<UserModel> =
        client
            .get()
            .uri("/users/{userId}/followers", userId)
            .retrieve()
            .bodyToFlux()

    fun getFollowersCount(userId: Long): Mono<Long> =
        client
            .get()
            .uri("/users/{userId}/followers/count", userId)
            .retrieve()
            .bodyToMono()

    fun getFollowings(userId: Long): Flux<UserModel> =
        client
            .get()
            .uri("/users/{userId}/followings", userId)
            .retrieve()
            .bodyToFlux()

    fun getFollowingsCount(userId: Long): Mono<Long> =
        client
            .get()
            .uri("/users/{userId}/followings/count", userId)
            .retrieve()
            .bodyToMono()
}
