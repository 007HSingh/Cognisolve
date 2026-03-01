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

class ApiClient(private val baseUrl: String = "http://32.192.6.18:8000") {
    
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
