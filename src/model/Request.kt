package model

data class Request(
    val command: String,
    val markup: Markup,
    val nlu: Nlu,
    val original_utterance: String,
    val payload: Payload,
    val type: String
)