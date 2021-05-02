import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.with
import org.http4k.format.Jackson.auto

typealias NVal = String
typealias ProductSetId = String

typealias NValForSetFinder = (List<ProductSetId>) -> Map<NVal, ProductSet>

fun createNValForSetFinder(client: HttpHandler): NValForSetFinder =
    fun(productSetIds: List<ProductSetId>): Map<NVal, ProductSet> {
        val request = Request(POST, "/nvals")
            .with(requestBodyLens of productSetIds.map { ProductSet(it) })

        val response = client(request)

        println(response)

        return responseBodyLens(response)
    }

val requestBodyLens = Body.auto<List<ProductSet>>().toLens()

val responseBodyLens = Body.auto<Map<NVal, ProductSet>>().toLens()

data class ProductSet(
    val id: ProductSetId,
)
