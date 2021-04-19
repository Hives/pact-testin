import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.format.Jackson.auto

fun createOrdersFetcher(client: HttpHandler): () -> List<Order> =
    fun(): List<Order> = ordersBodyLens(client(Request(GET, "/orders")))

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
