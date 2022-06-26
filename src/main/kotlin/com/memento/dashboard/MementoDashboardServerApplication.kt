package com.memento.dashboard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class MementoDashboardServerApplication

fun main(args: Array<String>) {
    runApplication<MementoDashboardServerApplication>(*args)
}