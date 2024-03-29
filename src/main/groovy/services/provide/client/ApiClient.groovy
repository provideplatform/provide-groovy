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

import groovy.json.JsonBuilder
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwsHeader
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SigningKeyResolverAdapter
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.SignatureException
import io.jsonwebtoken.security.Keys
import java.security.Key
import org.apache.http.client.methods.*
import org.apache.http.client.utils.URIBuilder
import org.apache.http.util.EntityUtils
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients

/*
 * API client for the provide.services API.
 */
 public class ApiClient {

    private static final DEFAULT_HOST = 'api.provide.services'
    private static final DEFAULT_SCHEME = 'https'

    /**
     * Static init for conveniently constructing API client instances.
     */
    static def init(scheme, host, token) {
        return new ApiClient(scheme, host, token)
    }

    private def baseUrl
    private def token
    private def path = 'api/v1/'

    private def ApiClient(scheme, host, token) {
         if (!scheme)
            scheme = DEFAULT_SCHEME
         if (!host)
            host = DEFAULT_HOST
         this.baseUrl = "${scheme}://${host}/${path}"
         this.token = token
    }

    def get(uri, params) {
        call('GET', uri, params, [:])
    }

    def post(uri, params) {
        call('POST', uri, params, [:])
    }
    
    def put(uri, params) {
        call('PUT', uri, params, [:])
    }

    def delete(uri, params) {
        call('DELETE', uri, params, [:])
    }

    def getApplicationId() {
        final def token = this.token
        def subject
        try {
            Jwts.parser().setSigningKeyResolver(new SigningKeyResolverAdapter() {
                public byte[] resolveSigningKeyBytes(JwsHeader header, Claims claims) {
                    subject = claims.getSubject()
                    return ''.getBytes();
                }})
            .parseClaimsJws(token)
        } catch (any) {}

        def subParts = subject?.split(':')
        if (subParts.length != 2) {
            return null
        }
        if (subParts[0] != 'application') {
            return null
        }
        subParts[1]
    }

    private def call(method, uri, params, headers) {
        def resp
        def respStatusCode
        def respBody

        try {
            def httpClient = HttpClients.createDefault()
            def req

            if (!headers) headers = [:]
            def reqHeaders = defaultHeaders()
            reqHeaders << headers

            if (method == 'GET') {
                def builder = new URIBuilder("${baseUrl}${uri}")
                    params.each { k, v ->
                        builder.setParameter(k, v)
                    }
                req = new HttpGet(builder.build())
            } else if (['POST', 'PUT', 'PATCH'].indexOf(method) != -1) {
                if (method == 'POST') {
                    req = new HttpPost("${baseUrl}${uri}")
                } else if (method == 'PUT') {
                    req = new HttpPut("${baseUrl}${uri}")
                } else if (method == 'PATCH') {
                    req = new HttpPatch("${baseUrl}${uri}")
                }
                reqHeaders['Content-Type'] = 'application/json'
                req.setEntity(new StringEntity(new JsonBuilder(params).toString()))
            }

            if (req) {
                reqHeaders.each { k, v ->
                    req.setHeader(k, v)
                }

                resp = httpClient.execute(req)
                respStatusCode = resp?.statusLine?.statusCode
                def entity = resp?.entity
                if (entity) {
                    respBody = EntityUtils.toString(entity)
                    EntityUtils.consume(entity)
                }
            }
        } catch (any) {
            System.out.println('ERROR: Failed to call provide API; ' + any)
        } finally {
            if (resp) resp.close()
        }

        [respStatusCode, respBody]
    }

    private def defaultHeaders() {
        def headers = [:]
        def userAgent = System.getenv('API_USER_AGENT')
        if (!userAgent)
            userAgent = 'provide-groovy client'
        if (userAgent)
            headers['User-Agent'] = userAgent
        if (token)
            headers['Authorization'] = "bearer ${token}"
        headers
    }
 }
