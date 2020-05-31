package ru.memeapp.echo.repository

import db.dbQuery
import db.table.Jokes
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class JokeRepository {

    suspend fun save(joke: String) {
        dbQuery {
            Jokes.insert { insertStatement ->
                insertStatement[text] = joke
            }
        }
    }

    suspend fun getAll(): List<String> =
        dbQuery {
            Jokes.selectAll().map { it[Jokes.text] }
        }

    suspend fun deleteAll() {
        dbQuery {
            Jokes.deleteAll()
        }
    }
}