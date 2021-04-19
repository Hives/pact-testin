import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEmpty
import au.com.dius.pact.consumer.*
import au.com.dius.pact.consumer.dsl.newArray
import au.com.dius.pact.consumer.dsl.newJsonArray
import au.com.dius.pact.consumer.dsl.newObject
import au.com.dius.pact.consumer.model.MockProviderConfig
import au.com.dius.pact.core.model.RequestResponsePact
import org.http4k.core.Uri
import org.junit.jupiter.api.Test

internal class ContractTest {

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

private fun RequestResponsePact.runTest(test: (Int) -> Unit): PactVerificationResult {
    val port = 7777
    val config = MockProviderConfig.httpConfig(port = port)
    return runConsumerTest(this, config, object : PactTestRun<Nothing?> {
        override fun run(mockServer: MockServer, context: PactTestExecutionContext?): Nothing? {
            test(port)
            return null
        }
    })
}