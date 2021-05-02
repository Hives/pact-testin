package routes

import NValForSetFinder
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.format.Jackson.auto
import org.http4k.lens.Query
import org.http4k.lens.string
import org.http4k.routing.bind

fun createNValsRoute(nValForSetFinder: NValForSetFinder) =
    "/nvals-for-sets" bind GET to {
        val setIds = setIdLens(it)
        val nValMaps = nValForSetFinder(setIds)
        Response(Status.OK).with(responseBodyLens of nValMaps.keys)
    }

private val setIdLens = Query.string().multi.required("setId")
val responseBodyLens = Body.auto<Set<String>>().toLens()
