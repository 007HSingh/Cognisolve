package com.cognisolve.repository

import com.cognisolve.models.*
import com.cognisolve.network.ApiClient

class CognisolveRepository(private val api: ApiClient = ApiClient()) {

    suspend fun diagnose(request: ExplainRequest): Result<DiagnosisResult> {
        return try {
            Result.success(api.diagnose(request))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun explain(request: ExplainRequest): Result<ExplainResponse> {
        return try {
            Result.success(api.explain(request))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun practice(request: PracticeRequest): Result<PracticeResponse> {
        return try {
            Result.success(api.practice(request))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun feedback(request: FeedbackRequest): Result<FeedbackResponse> {
        return try {
            Result.success(api.feedback(request))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
