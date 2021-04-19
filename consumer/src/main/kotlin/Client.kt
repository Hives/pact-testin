import okhttp3.OkHttpClient
import org.http4k.client.OkHttp
import org.http4k.core.HttpHandler
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters

fun createClient(baseUrl: Uri): HttpHandler =
    ClientFilters.SetBaseUriFrom(baseUrl).then(OkHttp(OkHttpClient()))