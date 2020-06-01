package model.alice

data class Nlu(
    val entities: List<Entity>,
    val tokens: List<String>
)