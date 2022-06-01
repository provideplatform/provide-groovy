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
