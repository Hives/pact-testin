import org.http4k.core.Body
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    routes(
        "/total" bind GET to {
            val orders = fetchOrders()
            println(orders)
            Response(Status.OK).with(OrdersTotal.ordersTotalBodyLens of OrdersTotal.from(orders))
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
