package services.provide.client.microservices


import groovy.json.JsonSlurper
import services.provide.client.ApiClient
import org.apache.commons.codec.binary.Base64
import services.provide.dao.Application
import services.provide.dao.Contract

import services.provide.dao.Field
import services.provide.dao.Function

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
    private def app_id

    private def Goldmine(token) {
        def scheme = System.getenv('GOLDMINE_API_SCHEME')
        def host = System.getenv('GOLDMINE_API_HOST')
        if (!host)
            host = DEFAULT_HOST
        this.client = ApiClient.init(scheme, host, token)
    }

     static def validateToken(token) {
         String[] parts = token.split("\\.");
         if (parts.length == 3)
             return true
         else
             return false
     }

     def getApplication(json) {
        return Application.init(json)
     }

     def Contract[] getContracts(json)
     {
         ArrayList<Contract> contractArrayList = new ArrayList<Contract>()
         def list = new JsonSlurper().parseText(json)
         list.each {
             def id = it.getAt("id")
             def name = it.getAt("name")
             def address = it.getAt("address")
             def contract = new Contract(id,name,address);

             def details = this.fetchContractDetails(id)
             def functions = new ArrayList<Function>();

             def js = new JsonSlurper().parseText(details.get(1))
             def abi = js.params.abi


             // Get all functions and their input / output fields
             abi.each {
                 def func_name = it.getAt("name")
                 def type = it.getAt("type")
                 if (!func_name.toString().contains("ipfs") && type.toString().equalsIgnoreCase("function")) {
                     def function = new Function()
                     def payable = it.getAt("payable")
                     function.setIsPayable(payable)
                     function.setName(func_name)

                     def inputs = it.getAt("inputs")
                     assert inputs instanceof ArrayList
                     if (inputs.size() > 0)
                     {
                         def fields = new ArrayList<Field>()
                         inputs.each {
                             fields.add(new Field(it.getAt("name").toString(), it.getAt("type").toString()))
                         }
                         function.setInputs(fields.toArray(new Field[0]))
                     }
                     def outputs = it.getAt("outputs")
                     assert outputs instanceof ArrayList
                     if (outputs.size() > 0)
                     {
                         def fields = new ArrayList<Field>()
                         outputs.each {
                             fields.add(new Field(it.getAt("name").toString(), it.getAt("type").toString()))
                         }
                         function.setOutputs(fields.toArray(new Field[0]))
                     }
                     functions.add(function)
                 }

             }
            contract.setFunctions(functions.toArray(new Function[0]))
             contractArrayList.add(contract)
         }
        return contractArrayList.toArray()
     }

     def  getApplicationId(token) {
         String base64EncodedBody = token.split("\\.")[1]

         Base64 base64 = new Base64()



         def body = new String(base64.decode(base64EncodedBody.bytes))
         def jsonSlurper = new JsonSlurper()
         def jsonBody = jsonSlurper.parseText(body)
         assert jsonBody instanceof Map


         return jsonBody.get("sub").split(":")[1]
     }

     def getIPFSList(resp)
     {
         assert resp instanceof ArrayList<String>
         def jsonSlurper = new JsonSlurper()
         def jsonBody = jsonSlurper.parseText(resp.get(1))
         assert jsonBody instanceof Map
         def _resp = jsonBody.get("response")
         assert _resp instanceof ArrayList<String>

         return _resp
     }

    def fetchBridges(params = nil) {
        client.get('bridges', (params || [:]))
    }

    def fetchBridgeDetails(bridgeId) {
        client.get("bridges/${bridgeId}", [:])
    }

    def createBridge(params) {
        client.post('bridges', params)
    }

    def fetchConnectors(params = nil) {
        client.get('connectors', (params || [:]))
    }

    def fetchConnectorDetails(connectorId) {
        client.get("connectors/${connectorId}", [:])
    }

    def createConnector(params) {
        client.post('connectors', params)
    }

    def deleteConnector(connectorId) {
        client.delete("connectors/${connectorId}")
    }

    def fetchContracts(params) {
        client.get('contracts', [:])
    }

    def fetchContractDetails(contractId) {
        client.get("contracts/${contractId}", [:])
    }

    def createContract(params) {
        client.post('contracts', params)
    }

    def executeContract(contractId, params) {
        client.post("contracts/${contractId}/execute", params)
    }

    def fetchNetworks(params = nil) {
        client.get('networks', (params || [:]))
    }

    def createNetwork(params) {
        client.post('networks', params)
    }

    def updateNetwork(networkId, params) {
        client.put("networks/${networkId}", params)
    }

    def fetchNetworkDetails(networkId) {
        client.get("networks/${networkId}", [:])
    }

    def fetchNetworkAccounts(networkId, params) {
        client.get("networks/${networkId}/accounts", params)
    }

    def fetchNetworkBlocks(networkId, params) {
        client.get("networks/${networkId}/blocks", params)
    }

    def fetchNetworkBridges(networkId, params) {
        client.get("networks/${networkId}/bridges", params)
    }

    def fetchNetworkConnectors(networkId, params) {
        client.get("networks/${networkId}/connectors", params)
    }

    def fetchNetworkContracts(networkId, params) {
        client.get("networks/${networkId}/contracts", params)
    }

    def fetchNetworkContractDetails(networkId, contractId) {
        client.get("networks/${networkId}/contracts/${contractId}", [:])
    }

    def fetchNetworkOracles(networkId, params) {
        client.get("networks/${networkId}/oracles", params)
    }

    def fetchNetworkTokens(networkId, params) {
        client.get("networks/${networkId}/tokens", params)
    }

    def network_transactions(networkId, params) {
        client.get("networks/${networkId}/transactions", params)
    }

    def fetchNetworkTransactionDetails(networkId, transactionId) {
        client.get("networks/${networkId}/transactions/${transactionId}", [:])
    }

    def fetchNetworkStatus(networkId) {
        client.get("networks/${networkId}/status", [:])
    }

    def fetchNetworkNodes(networkId, params = nil) {
        client.get("networks/${networkId}/nodes", (params || [:]))
    }

    def createNetworkNode(networkId, params) {
        client.post("networks/${networkId}/nodes", params)
    }

    def fetchNetworkNodeDetails(networkId, nodeId) {
        client.get("networks/${networkId}/nodes/${nodeId}", [:])
    }

    def fetchNetworkNodeLogs(networkId, nodeId) {
        client.get("networks/${networkId}/nodes/${nodeId}/logs", [:])
    }

    def deleteNetworkNode(networkId, nodeId) {
        client.delete("networks/${networkId}/nodes/${nodeId}")
    }

    def fetchOracles(params = nil) {
        client.get('oracles', (params || [:]))
    }

    def fetchOracleDetails(oracleId) {
        client.get("oracles/${oracleId}", [:])
    }

    def createOracle(params) {
        client.post('oracles', params)
    }

    def fetchTokens(params = nil) {
        client.get('tokens', (params || [:]))
    }

    def fetchTokenDetails(tokenId) {
        client.get("tokens/${tokenId}", [:])
    }

    def createToken(params) {
        client.post('tokens', params)
    }

    def createTransaction(params) {
        client.post('transactions', params)
    }

    def fetchTransactions(params = nil) {
        client.get('transactions', (params || [:]))
    }

    def fetchTransactionDetails(txId) {
        client.get("transactions/${txId}", [:])
    }

    def fetchWalletBalance(walletId, tokenId) {
        client.get("wallets/${walletId}/balances/${tokenId}", [:])
    }

    def fetchWallets(params ) {
        client.get('wallets', [:])
    }

    def fetchWalletDetails(walletId) {
        client.get("wallets/${walletId}", [:])
    }

    def createWallet(params) {
        client.post('wallets', params)
    }
}
