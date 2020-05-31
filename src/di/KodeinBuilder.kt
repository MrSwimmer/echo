package di

import com.google.gson.Gson
import io.ktor.application.ApplicationEnvironment
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import db.DatabaseFactory
import org.kodein.di.generic.instance
import routing.RoutingV1
import ru.memeapp.echo.repository.JokeRepository
import ru.memeapp.echo.service.JokeService
import ru.memeapp.echo.service.ParserService
import java.net.URI

class KodeinBuilder(private val environment: ApplicationEnvironment) {

    @Suppress("EXPERIMENTAL_API_USAGE")
    fun setup(builder: Kodein.MainBuilder) {
        with(builder) {
            bind<DatabaseFactory>() with eagerSingleton {
                val dbUri = URI(environment.config.property("db.jdbcUrl").getString())

                val username: String = dbUri.userInfo.split(":")[0]
                val password: String = dbUri.userInfo.split(":")[1]
                val dbUrl = ("jdbc:postgresql://${dbUri.host}:${dbUri.port}${dbUri.path}")

                DatabaseFactory(
                    dbUrl = dbUrl,
                    dbPassword = password,
                    dbUser = username
                ).apply {
                    init()
                }
            }
            bind<Gson>() with eagerSingleton { Gson() }
            bind<JokeRepository>() with eagerSingleton { JokeRepository() }
            val vkToken = environment.config.property("vk.token").getString()
            print("vkToken $vkToken")
            bind<ParserService>() with eagerSingleton { ParserService(instance(), instance(), vkToken) }
            bind<JokeService>() with eagerSingleton { JokeService(instance()) }
            bind<RoutingV1>() with eagerSingleton { RoutingV1(instance(), instance()) }
        }
    }
}