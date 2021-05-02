package routes

import org.http4k.core.Body
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind

typealias NVal = String
typealias ProductSetId = String

val nValsRoute = "/nvals" bind POST to { request ->
    val productSets = requestBodyLens(request)

    val nValMap = productSets
        .map { productSet -> "${productSet.id}-NVAL" to productSet }
        .toMap()

    Response(OK).with(responseBodyLens of nValMap)
}

val requestBodyLens = Body.auto<List<ProductSet>>().toLens()
val responseBodyLens = Body.auto<Map<NVal, ProductSet>>().toLens()

data class ProductSet(
    val id: ProductSetId,
)
