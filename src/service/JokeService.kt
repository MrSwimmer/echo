package ru.memeapp.echo.service

import ru.memeapp.echo.repository.JokeRepository

class JokeService(
    private val jokeRepository: JokeRepository
) {

    suspend fun getAll() =
        jokeRepository.getAll()

    suspend fun get() =
        jokeRepository.getAll().random()
}