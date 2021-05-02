import assertk.assertThat
import assertk.assertions.isEqualTo
import au.com.dius.pact.consumer.ConsumerPactBuilder
import au.com.dius.pact.consumer.PactVerificationResult
import au.com.dius.pact.consumer.dsl.newJsonArray
import au.com.dius.pact.consumer.dsl.newJsonObject
import au.com.dius.pact.consumer.dsl.newObject
import helpers.runTest
import org.http4k.core.Uri
import org.junit.jupiter.api.Test

internal class NValsContractTests {

    @Test
    fun `it can call the nvals endpoint and process the response`() {
        val setId = "my-set-id"

        val requestBody = newJsonArray {
            newObject {
                stringType("id", setId)
            }
        }

        val providerResponseBody = newJsonObject {
            eachKeyLike("nval") { nVal ->
                nVal.stringType("id", setId)
            }
        }

        val pact = ConsumerPactBuilder
            .consumer("the consumer")
            .hasPactWith("the provider")
            .uponReceiving("POST /nvals with set ids")
            .path("/nvals")
            .method("POST")
            .body(requestBody)
            .willRespondWith()
            .status(200)
            .body(providerResponseBody)
            .toPact()

        val result = pact.runTest { port ->
            val client = createClient(Uri.of("http://127.0.0.1:$port"))
            val nValForSetFinder = createNValForSetFinder(client)
            nValForSetFinder(listOf(setId))
        }

        assertThat(result).isEqualTo(PactVerificationResult.Ok())
    }
}
