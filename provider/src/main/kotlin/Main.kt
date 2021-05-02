import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import routes.nValsRoute
import routes.ordersRoute

fun main() {
    routes(
        ordersRoute,
        nValsRoute
    ).asServer(Jetty(8081)).start()
}