package com.habilisadi.file

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class FileApplication

fun main(args: Array<String>) {
    runApplication<FileApplication>(*args)
}
