package db.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Jokes : Table() {
    val id: Column<Long> = Jokes.long("id").autoIncrement().primaryKey()
    val text: Column<String> = Jokes.varchar("text", 1024)
    val type: Column<String> = Jokes.varchar("type", 50)
}