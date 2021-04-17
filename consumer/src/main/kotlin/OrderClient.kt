import org.http4k.client.JavaHttpClient
import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.format.Jackson.auto

const val ORDERS_API = "http://localhost:8081"

fun fetchOrders(): List<Order> =
    client(Request(Method.GET, "$ORDERS_API/orders")).let { ordersBodyLens(it) }

val client = JavaHttpClient()

val ordersBodyLens = Body.auto<List<Order>>().toLens()

data class Order(
    val id: String,
    val items: List<Item>
) {
    val total = items.sumBy { it.total }

    data class Item(
        val name: String,
        val quantity: Int,
        val value: Int
    ) {
        val total = quantity * value
    }
}
