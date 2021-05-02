package helpers

import au.com.dius.pact.consumer.*
import au.com.dius.pact.consumer.model.MockProviderConfig
import au.com.dius.pact.core.model.RequestResponsePact

fun RequestResponsePact.runTest(test: (Int) -> Unit): PactVerificationResult {
    val port = 7777
    val config = MockProviderConfig.httpConfig(port = port)
    return runConsumerTest(this, config, object : PactTestRun<Nothing?> {
        override fun run(mockServer: MockServer, context: PactTestExecutionContext?): Nothing? {
            test(port)
            return null
        }
    })
}
