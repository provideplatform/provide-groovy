import spock.lang.*

import services.provide.client.microservices.Ident

class IdentSpec extends Specification {

    def prvdClient

    def setup() {
        this.prvdClient = Ident.init('some-api-token')
    }

    def 'fetch applications with invalid key'() {
        when:
            def appsResult = prvdClient.fetchApplications([:])

        then:
            appsResult != null
            appsResult.size() == 2
            appsResult[0] == 401
    }
}
