package model

data class Response(
    val buttons: List<Button>? = null,
    val end_session: Boolean,
    val text: String,
    val tts: String? = null
)