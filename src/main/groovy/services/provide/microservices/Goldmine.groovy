package services.provide.client.microservices

import services.provide.client.ApiClient

/*
 * Goldmine microservice; provides access to functionality
 * exposed by the Provide blockchain APIs.
 */
 public class Goldmine {

    private static final DEFAULT_HOST = 'goldmine.provide.services'

    /**
     * Static initializer for constructing API client instances.
     */
    static def init(token) {
        return new Goldmine(token)
    }

    private def client

    private def Goldmine(token) {
        def scheme = System.getenv('GOLDMINE_API_SCHEME')
        def host = System.getenv('GOLDMINE_API_HOST')
        if (!host)
            host = DEFAULT_HOST
        this.client = ApiClient.init(scheme, host, token)
    }

    def createTransaction(params) {
        client.post('transactions', params)
    }

    def executeContract(contractId, params) {
        client.post("contracts/${contractId}/execute", params)
    }
}
