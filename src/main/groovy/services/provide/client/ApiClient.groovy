package services.provide.client

import groovy.json.JsonBuilder
import org.apache.http.client.methods.*
import org.apache.http.client.utils.URIBuilder
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients

/*
 * API client for the provide.services API.
 */
 public class ApiClient {

    /**
     * Static initializer for constructing API client instances.
     */
    static def init(token) {
        return new ApiClient(token)
    }

    private def baseUrl
    private def token
    private def path = 'api/v1/'

    private def ApiClient(token) {
         def scheme = System.getenv('API_SCHEME')
         if (!scheme)
            scheme = 'https'
         def host = System.getenv('API_HOST')
         if (!host)
            host = 'console.provide.services'
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

    private def call(method, uri, params, headers) {
        def resp
        def entity

        try {
            def httpClient = HttpClients.createDefault()
            def req

            if (!headers) headers = [:]
            def reqHeaders = defaultHeaders()
            reqHeaders << headers

            if (method == 'GET') {
                def builder = new URIBuilder("${baseUrl}${uri}")
                for (name in params) {
                    builder.setParameter(params[name])
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
                headers['Content-Type'] = 'application/json'
                for (name in reqHeaders) {
                    req.setHeader(name, reqHeaders[name])
                }
                req.setEntity(new StringEntity(new JsonBuilder(params).toString()))
            }

            if (req) {
                resp = httpClient.execute(req)
                entity = resp.getEntity()
                EntityUtils.consume(entity)
            }
        } catch (any) {
            System.err.println('Failed to call API; ' + any)
        } finally {
            if (response) response.close()
        }

        return [resp, entity]
    }

    private def defaultHeaders() {
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
