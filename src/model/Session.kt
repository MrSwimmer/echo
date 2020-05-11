package model

data class Session(
    val application: ApplicationX,
    val message_id: Int,
    val new: Boolean,
    val session_id: String,
    val skill_id: String,
    val user: User,
    val user_id: String
)