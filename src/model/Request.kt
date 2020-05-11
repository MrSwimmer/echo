package model

data class Request(
    val command: String,
    val markup: Markup,
    val nlu: Nlu,
    val original_utterance: String,
    val type: String
)