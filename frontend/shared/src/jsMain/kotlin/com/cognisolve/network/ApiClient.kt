package com.cognisolve.network

import com.cognisolve.models.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

// Determine base URL at runtime:
//  - On localhost: call the EC2 backend directly (no HTTPS needed)
//  - Anywhere else (e.g. Render): route through the Nginx /api/ proxy, which
//    forwards to EC2 server-side and avoids mixed-content browser errors.
private fun resolveBaseUrl(): String {
    val hostname = js("window.location.hostname").toString()
    return if (hostname == "localhost" || hostname == "127.0.0.1") {
        "http://32.192.6.18:8000"
    } else {
        js("window.location.origin").toString() + "/api"
    }
}

class ApiClient(private val baseUrl: String = resolveBaseUrl()) {
    
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    suspend fun diagnose(request: ExplainRequest): DiagnosisResult {
        return client.post("$baseUrl/explain/diagnose") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun explain(request: ExplainRequest): ExplainResponse {
        return client.post("$baseUrl/explain") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun practice(request: PracticeRequest): PracticeResponse {
        return client.post("$baseUrl/practice") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun feedback(request: FeedbackRequest): FeedbackResponse {
        return client.post("$baseUrl/practice/feedback") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}
