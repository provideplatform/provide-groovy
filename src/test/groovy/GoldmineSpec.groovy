import spock.lang.*

import services.provide.client.microservices.Goldmine

class GoldmineSpec extends Specification {

    def prvdClient

    def setup() {
        this.prvdClient = Goldmine.init('some-api-token')
    }

    def 'tx broadcast with invalid key'() {
        when:
            def txResult = prvdClient.createTransaction([:])

        then:
            txResult != null
            txResult.size() == 2
            txResult[0] == 401
    }
}
