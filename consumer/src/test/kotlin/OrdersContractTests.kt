import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEmpty
import au.com.dius.pact.consumer.ConsumerPactBuilder
import au.com.dius.pact.consumer.PactVerificationResult
import au.com.dius.pact.consumer.dsl.newArray
import au.com.dius.pact.consumer.dsl.newJsonArray
import au.com.dius.pact.consumer.dsl.newObject
import helpers.runTest
import org.http4k.core.Uri
import org.junit.jupiter.api.Test

internal class OrdersContractTests {

    @Test
    fun `it can call the orders api and process the response`() {
        val providerResponse = newJsonArray {
            newObject {
                stringType("id")
                newArray("items") {
                    newObject {
                        stringType("name")
                        integerType("quantity")
                        integerType("value")
                    }
                }
            }
        }

        val pact = ConsumerPactBuilder
            .consumer("the consumer")
            .hasPactWith("the provider")
            .uponReceiving("a request for the orders")
            .path("/orders")
            .method("GET")
            .willRespondWith()
            .status(200)
            .headers(mapOf("Content-Type" to "application/json"))
            .body(providerResponse)
            .toPact()

        val result = pact.runTest { port ->
            val ordersClient = createClient(Uri.of("http://localhost:$port"))
            val ordersFetcher = createOrdersFetcher(ordersClient)

            val orders = ordersFetcher()
            assertThat(orders).isNotEmpty()
        }

        assertThat(result).isEqualTo(PactVerificationResult.Ok())
    }
}