package model.alice

data class AliceRequest(
    val meta: Meta,
    val request: Request,
    val session: Session,
    val version: String
)