import org.http4k.core.Body
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Uri
import org.http4k.core.with
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

const val ORDERS_API = "http://localhost:8081"

fun main() {
    val ordersFetcher = createOrdersFetcher(createClient(Uri.of(ORDERS_API)))

    routes(
        "/" bind GET to { Response(OK).body("Ok then") },
        "/total" bind GET to {
            Response(OK).with(OrdersTotal.ordersTotalBodyLens of OrdersTotal.from(ordersFetcher()))
        }
    ).asServer(Jetty(8080)).start()
}

data class OrdersTotal(
    val value: Int
) {
    companion object {
        fun from(orders: List<Order>) = OrdersTotal(orders.sumBy { it.total })

        val ordersTotalBodyLens = Body.auto<OrdersTotal>().toLens()
    }
}
