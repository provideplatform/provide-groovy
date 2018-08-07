package services.provide.client

/*
 * Goldmine microservice; provides access to functionality
 * exposed by the Provide blockchain APIs.
 */
 public class Goldmine {

    /**
     * Static initializer for constructing API client instances.
     */
    static def init(token) {
        return new Goldmine(token)
    }

    private def client

    private def Goldmine(token) {
        this.client = new ApiClient(token)
    }

    def createTransaction(params) {
        client.post('transactions', params)
    }
}
