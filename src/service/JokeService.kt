package ru.memeapp.echo.service

import ru.memeapp.echo.repository.JokeRepository

class JokeService(
    private val jokeRepository: JokeRepository
) {

    suspend fun getAll() =
        jokeRepository.getAll()

    suspend fun get() =
        jokeRepository.getAll().random()

    suspend fun getJoke() =
        jokeRepository.getAllJokes().random()

    suspend fun getHumoresque() =
        jokeRepository.getAllHumoresques().random()
}