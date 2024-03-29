/*
 * Copyright 2017-2022 Provide Technologies Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package services.provide.client.microservices

import groovy.json.JsonSlurper
import services.provide.client.ApiClient
import org.apache.commons.codec.binary.Base64

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

    def fetchAccountBalance(accountId, tokenId) {
        client.get("accounts/${accountId}/balances/${tokenId}", [:])
    }

    def fetchAccounts(params) {
        client.get('accounts', (params || [:]))
    }

    def fetchAccountDetails(accountId) {
        client.get("accounts/${accountId}", [:])
    }

    def createAccount(params) {
        client.post('accounts', params)
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
