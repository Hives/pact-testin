import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Uri
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import routes.createNValsRoute
import routes.createTotalsRoute

const val PROVIDER_API = "http://localhost:8081"

fun main() {
    val client = createClient(Uri.of(PROVIDER_API))

    val ordersFetcher = createOrdersFetcher(client)
    val nValForSetFinder = createNValForSetFinder(client)

    routes(
        "/" bind GET to { Response(OK).body("Ok then") },
        createTotalsRoute(ordersFetcher),
        createNValsRoute(nValForSetFinder)
    ).asServer(Jetty(8080)).start()
}
