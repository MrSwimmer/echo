package ru.memeapp.echo.repository

import db.dbQuery
import db.table.Jokes
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import ru.memeapp.echo.model.JokeType

class JokeRepository {

    suspend fun save(joke: String, jokeType: JokeType) {
        dbQuery {
            Jokes.insert { insertStatement ->
                insertStatement[text] = joke
                insertStatement[type] = jokeType.name
            }
        }
    }

    suspend fun getAll(): List<String> =
        dbQuery {
            Jokes.selectAll().map { it[Jokes.text] }
        }

    suspend fun getAllJokes(): List<String> =
        dbQuery {
            Jokes.select {
                Jokes.type.eq(JokeType.JOKE.name)
            }.map { it[Jokes.text] }
        }

    suspend fun getAllHumoresques(): List<String> =
        dbQuery {
            Jokes.select {
                Jokes.type.eq(JokeType.HUMORESQUE.name)
            }.map { it[Jokes.text] }
        }

    suspend fun deleteAll() {
        dbQuery {
            Jokes.deleteAll()
        }
    }
}