import org.http4k.core.Body
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
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
            val person = people.get(name)

            if (person == null) Response(NOT_FOUND)
            else Response(OK).with(Person.bodyLens of person)
        }
    ).asServer(Jetty(8081)).start()
}

val people = mapOf(
    "paul" to Person("blue"),
    "claire" to Person("pink"),
    "satan" to Person("red"),
)

data class Person(
    val favouriteColour: String
) {
    companion object {
        val bodyLens = Body.auto<Person>().toLens()
    }
}