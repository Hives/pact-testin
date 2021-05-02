package routes

import org.http4k.core.*
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind

val ordersRoute = "/orders" bind Method.GET to {
    Response(Status.OK).with(ordersBodyLens of orders)
}

private val orders = listOf(
    Order(
        id = "1",
        items = listOf(
            Order.Item(
                name = "burger",
                quantity = 2,
                value = 20
            ),
            Order.Item(
                name = "coke",
                quantity = 2,
                value = 5
            ),
        )
    ),
    Order(
        id = "2",
        items = listOf(
            Order.Item(
                name = "cheeseburger",
                quantity = 1,
                value = 23
            ),
            Order.Item(
                name = "milkshake",
                quantity = 1,
                value = 7
            ),
        )
    ),
    Order(
        id = "3",
        items = listOf(
            Order.Item(
                name = "nuggets",
                quantity = 1,
                value = 30
            ),
        )
    )
)

private val ordersBodyLens = Body.auto<List<Order>>().toLens()

private data class Order(
    val id: String,
    val items: List<Item>
) {
    data class Item(
        val name: String,
        val quantity: Int,
        val value: Int
    )
}
