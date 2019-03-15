import groovy.json.JsonSlurper
import services.provide.client.IPFSClient
import services.provide.dao.Application
import services.provide.dao.Contract


class TestGoldMine extends GroovyTestCase {

    def prvd
    def ident
    def i
    def contract_id = "0x80CF71df16499713b06d2117bAABCad1Ff05198d"
    //def app_id = "cbdff9b1-16b0-4d47-ae46-f26aa07aa2ee"
    def token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7fSwiZXhwIjpudWxsLCJpYXQiOjE1NTIzMzIzMzksImp0aSI6IjVkMmVjY2FjLWQzYzgtNGVlMy1hMTQxLWJmNmM5YTM5ODZjZiIsInN1YiI6ImFwcGxpY2F0aW9uOjg3NzE3NmViLTY4OTMtNDNkMi05MTgxLTI2ZjYxNmMyMDc3OSJ9.cz1MwjJbUDauHQ5ahYLuLnF6ox6a1KE69vO041dhy_8"
    void test() {

        def val = services.provide.client.microservices.Goldmine.validateToken(token);

        prvd = services.provide.client.microservices.Goldmine.init(token)

        ident = services.provide.client.microservices.Ident.init(token)

        def app_id = prvd.getApplicationId(token)

        //def user = ident.authenticate(params)


        def app = ident.fetchApplicationDetails(app_id)
        def application = prvd.getApplication(app.get(1))
        def contracts = prvd.fetchContracts(null)
        Contract[] tvcontract = prvd.getContracts(contracts.get(1))
        def contract = prvd.fetchContractDetails("82075098-d354-418a-808a-ab2dc01a70d2")
        def wallets = prvd.fetchWallets([:])
        //def wallet = prvd.fetchWalletDetails("dfc12196-44d8-4ca2-8e6e-46f3ccf4c3a8")

      //  def wallets = prvd.fetchWallets([:])
        //println(app_id)
        //print(contracts)
        //print(contract)
        //println(app)
        //println(wallets)
        //print(wallet)
        def me = ""

        def _ipfs_fields = prvd.executeContract(contract_id, [network_id: "024ff1ef-7369-4dee-969c-1918c6edb5d4",
                                                                                               app_id    : app_id,
                                                                                               wallet_id : "2d215b92-6a93-48a6-aa35-5a6d1f6c1057",
                                                                                               to        : contract_id, value     : 0, data: "", params: null, method: "ipfs_fields"])



        def resp = prvd.getIPFSList(_ipfs_fields)
        println(resp.get(0))

        //def txs = prvd.network_transactions("024ff1ef-7369-4dee-969c-1918c6edb5d4",[:])
        //def t = prvd.fetchTransactions([:])
        //println(t)

        //for (i = 0; i < 1; i++) {
        def hash = new IPFSClient(null, null, null).add(UUID.randomUUID().toString(), "Hello Provide".bytes)

            //when:



        def insert = prvd.executeContract(contract_id, [network_id: "024ff1ef-7369-4dee-969c-1918c6edb5d4",
                                                                                      app_id    : app_id,
                                                                                      wallet_id : "2d215b92-6a93-48a6-aa35-5a6d1f6c1057",
                                                                                      to        : contract_id,
                                                                                      value     : 0, data: "", params: ["222", "ZZ","567890", "01", "4010","856", hash], method: "send"])

        print(insert)
/*
        def retreive = prvd.executeContract(contract_id, [network_id: "024ff1ef-7369-4dee-969c-1918c6edb5d4",
                                                                                          app_id    : app_id,
                                                                                          wallet_id : "dfc12196-44d8-4ca2-8e6e-46f3ccf4c3a8",
                                                                                          to        : contract_id,
                                                                                          value     : 0, data: "", params: ["567890", "01", "4010","856"], method: "retrieve"])
*/
            //then:
//            System.out.println(retreive)
        //}


    }

}
