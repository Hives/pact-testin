import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    routes(
        "/orders" bind GET to {
            Response(OK).with(ordersBodyLens of orders)
        }
    ).asServer(Jetty(8081)).start()
}