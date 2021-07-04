package com.eddsteel.pad

import freemarker.cache.ClassTemplateLoader
import freemarker.core.HTMLOutputFormat
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(FreeMarker) {
            templateLoader = ClassTemplateLoader(this::class.java.classLoader, "")
            outputFormat = HTMLOutputFormat.INSTANCE
        }

        routing {
            get("/content") {
                call.respond(pad.get())
            }
            post("/content") {
                val content = call.receive<String>()
                pad.set(content)
                call.respond(HttpStatusCode.Accepted)
            }
            get("/") {
                call.respond(FreeMarkerContent("index.ftl", mapOf("content" to pad.get())))
            }
            post("/") {
                val content = call.receiveParameters()["content"] ?: return@post call.respond(HttpStatusCode.BadRequest)
                pad.set(content)
                call.respondRedirect("/")
            }
        }
    }.start(wait = true)
}
