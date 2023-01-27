package com.cit

import com.cit.database.DatabaseFactory
import com.cit.database.InitializationDatabaseContent
import com.cit.models.ModelError
import com.cit.routings.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.doublereceive.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.nio.charset.StandardCharsets

lateinit var PATH_LOCAL_PROPERTY: String

fun main(args: Array<String>) {
    PATH_LOCAL_PROPERTY = if (args.isNotEmpty()) args[0] else "local.properties"

    embeddedServer(Netty, port = getLocalProperty("port").toInt(), host = getLocalProperty("host"), module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    install(DoubleReceive)

    DatabaseFactory.initDataBase()

    configureUsersRouting()
    configurePlantsRouting()
    configureConfigRouting()
    configureFileRouting()
    configureCartRouting()
}

fun getLocalProperty(key: String): String {
    val properties = java.util.Properties()
    val localProperties = File(PATH_LOCAL_PROPERTY)
    if (localProperties.isFile) {
        InputStreamReader(FileInputStream(localProperties), StandardCharsets.UTF_8).use { reader ->
            properties.load(reader)
        }
    } else error("File from not found")

    return properties.getProperty(key)
}