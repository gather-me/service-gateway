package com.gather.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ServiceGatewayApplication

fun main(args: Array<String>) {
    runApplication<ServiceGatewayApplication>(*args)
}
