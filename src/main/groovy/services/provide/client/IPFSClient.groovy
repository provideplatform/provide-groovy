package services.provide.client

import io.ipfs.api.IPFS
import io.ipfs.api.NamedStreamable

/*
 * IPFS client wrapper.
 */
public class IPFSClient {

    private static final DEFAULT_HOST = 'ipfs.provide.services'
    private static final DEFAULT_PORT = 5001
    private static final DEFAULT_SCHEME = 'http'
    private static final DEFAULT_VERSION = '/api/v0/'

    /**
     * Static init for conveniently constructing IPFS client instances.
     */
    static def init(scheme, host, port) {
        return new IPFSClient(scheme, host, port)
    }

    private def ipfs

    private def IPFSClient(scheme, host, port) {
        if (!scheme)
            scheme = DEFAULT_SCHEME
        if (!host)
            host = DEFAULT_HOST
        if (!port)
            port = DEFAULT_PORT
        def ssl = scheme == 'https'
        this.ipfs = new IPFS(host, port, DEFAULT_VERSION, ssl)
    }

    def add(filename, bytes) {
        def hash
        try {
            def file = new NamedStreamable.ByteArrayWrapper(filename, bytes)
            hash = ipfs.add(file)?.get(0)?.hash?.toString()
        } catch (any) {
            System.out.println('ERROR: Failed to add file to IPFS; ' + any)
        }
        hash
    }
}
