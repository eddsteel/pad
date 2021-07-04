package com.eddsteel.pad

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicReference

private val serial = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
val pad = Pad(Date().toString())

class Pad(initialContent: String) {
    private val content = AtomicReference(initialContent)

    fun get(): String {
        return content.get()
    }

    suspend fun set(newContent: String) {
        withContext(serial) {
                content.set(newContent)
        }
    }
}
