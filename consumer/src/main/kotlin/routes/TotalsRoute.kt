package routes

import Order
import OrdersFetcher
import org.http4k.core.Body
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind
import routes.OrdersTotal.Companion.ordersTotalBodyLens

fun createTotalsRoute(ordersFetcher: OrdersFetcher) = "/total" bind GET to {
    Response(Status.OK)
        .with(ordersTotalBodyLens of OrdersTotal.from(ordersFetcher()))
}

data class OrdersTotal(
    val value: Int
) {
    companion object {
        fun from(orders: List<Order>) = OrdersTotal(orders.sumBy { it.total })

        val ordersTotalBodyLens = Body.auto<OrdersTotal>().toLens()
    }
}
