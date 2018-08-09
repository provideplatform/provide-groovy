import spock.lang.*

import services.provide.client.IPFSClient

class IPFSClientSpec extends Specification {
    
    def ipfsClient

    def setup() {
        this.ipfsClient = IPFSClient.init(null, null, null)
    }

    def 'it should an initialize an IPFS instance'() {
        expect:
            ipfsClient.ipfs != null
    }

    def 'it should expose an API for adding files IPFS and return the hash'() {
        when:
            def hash = ipfsClient.add('helloworld.txt', 'well hello there'.bytes)

        then:
            hash != null
    }
}
