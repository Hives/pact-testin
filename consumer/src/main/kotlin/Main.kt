import org.http4k.client.JavaHttpClient
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    routes(
        "/person/{name}" bind GET to { req ->
            val name = req.path("name")!!
            val person = foo(name)

            Response(Status.OK).with(Person.bodyLens of person)
        }
    ).asServer(Jetty(8080)).start()
}

fun foo(name: String): Person =
    client(Request(GET, "http://localhost:8081/person/$name")).let { Person.bodyLens(it) }

val client = JavaHttpClient()

data class Person(
    val favouriteColour: String
) {
    companion object {
        val bodyLens = Body.auto<Person>().toLens()
    }
}
