package model

data class Nlu(
    val entities: List<Entity>,
    val tokens: List<String>
)