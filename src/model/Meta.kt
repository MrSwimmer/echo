package model

data class Meta(
    val client_id: String,
    val interfaces: Interfaces,
    val locale: String,
    val timezone: String
)