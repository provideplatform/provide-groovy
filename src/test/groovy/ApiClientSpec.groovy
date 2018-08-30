import spock.lang.*

import services.provide.client.ApiClient

class ApiClientSpec extends Specification {
    
    def prvdClient

    def setup() {
        this.prvdClient = ApiClient.init(null, null, 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7fSwiZXhwIjpudWxsLCJpYXQiOjE1MzQyNjY1NDcsImp0aSI6ImZhNmUyNzY0LTM5ZmEtNDNlOC1hMDNhLWYzNTVmNDRmNmRjZSIsInN1YiI6ImFwcGxpY2F0aW9uOmNiNTgzZmE1LWEzODctNDE5OC1hNTViLWFlZDM3MTM1YzA1MCJ9.R7akJwCrKCt_tyO9UybE-kqXxpYNZFQYkrdBuEsf0b8')
    }

    def 'it should use production as the default api host'() {
        expect:
            prvdClient.baseUrl == 'https://console.provide.services/api/v1/'
    }

    def 'it should expose the application id in the token'() {
        expect:
            prvdClient.getApplicationId() == 'cb583fa5-a387-4198-a55b-aed37135c050'
    }
}
