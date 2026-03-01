package com.cognisolve.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
enum class ConfusionType {
    @SerialName("conceptual") CONCEPTUAL,
    @SerialName("procedural") PROCEDURAL,
    @SerialName("abstraction_gap") ABSTRACTION_GAP,
    @SerialName("misconception") MISCONCEPTION,
    @SerialName("transfer") TRANSFER,
    @SerialName("unknown") UNKNOWN
}

@Serializable
enum class ExplanationStrategy {
    @SerialName("analogy_based") ANALOGY,
    @SerialName("step_by_step") STEP_BY_STEP,
    @SerialName("intuition_first") INTUITION_FIRST,
    @SerialName("code_first") CODE_FIRST,
    @SerialName("visual_reasoning") VISUAL_REASONING,
    @SerialName("simplified_rephrasing") SIMPLIFIED
}

@Serializable
data class ExplainRequest(
    val concept: String,
    @SerialName("user_doubt") val userDoubt: String,
    @SerialName("code_snippet") val codeSnippet: String? = null,
    @SerialName("difficulty_level") val difficultyLevel: String? = "beginner",
    @SerialName("learner_id") val learnerId: String? = null
)

@Serializable
data class DiagnosisResult(
    @SerialName("confusion_type") val confusionType: ConfusionType,
    val confidence: Double,
    val reasoning: String
)

@Serializable
data class ExplainResponse(
    val concept: String,
    @SerialName("confusion_type") val confusionType: ConfusionType,
    @SerialName("strategy_used") val strategyUsed: ExplanationStrategy,
    val explanation: String,
    val analogy: String? = null,
    @SerialName("key_insight") val keyInsight: String? = null,
    @SerialName("common_mistake") val commonMistake: String? = null,
    @SerialName("follow_up_hint") val followUpHint: String? = null
)

@Serializable
data class PracticeRequest(
    val concept: String,
    @SerialName("confusion_type") val confusionType: ConfusionType,
    @SerialName("explanation_given") val explanationGiven: String,
    @SerialName("difficulty_level") val difficultyLevel: String? = "beginner",
    @SerialName("num_questions") val numQuestions: Int? = 2
)

@Serializable
data class PracticeQuestion(
    @SerialName("question_id") val questionId: Int,
    val question: String,
    @SerialName("question_type") val questionType: String,
    val options: List<String>? = null,
    @SerialName("correct_answer") val correctAnswer: String,
    val explanation: String
)

@Serializable
data class PracticeResponse(
    val concept: String,
    @SerialName("confusion_type") val confusionType: ConfusionType,
    val questions: List<PracticeQuestion>
)

@Serializable
data class FeedbackRequest(
    @SerialName("learner_id") val learnerId: String,
    val concept: String,
    val question: String,
    @SerialName("learner_answer") val learnerAnswer: String,
    @SerialName("correct_answer") val correctAnswer: String,
    @SerialName("confusion_type") val confusionType: ConfusionType
)

@Serializable
data class FeedbackResponse(
    @SerialName("is_correct") val isCorrect: Boolean,
    val score: Double,
    @SerialName("feedback_message") val feedbackMessage: String,
    @SerialName("re_explanation") val reExplanation: String? = null,
    val encouragement: String
)
