import spock.lang.*
import services.provide.client.ApiClient

class ApiClientSpec extends Specification {
    
    def prvdClient

    def setup() {
        this.prvdClient = ApiClient.init('some-api-token')
    }

    def 'it should use production as the default api host'() {
        expect:
            prvdClient.baseUrl == 'https://console.provide.services/api/v1/'
    }
}
