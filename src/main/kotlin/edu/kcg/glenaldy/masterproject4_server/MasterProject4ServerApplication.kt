package edu.kcg.glenaldy.masterproject4_server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication
class MasterProject4ServerApplication

fun main(args: Array<String>) {
	runApplication<MasterProject4ServerApplication>(*args)
}
