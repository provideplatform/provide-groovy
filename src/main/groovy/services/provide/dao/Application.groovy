package services.provide.dao

import groovy.json.JsonSlurper

public class Application {
    private network_id = null
    private name = null

    static def init(json)
    {
        def jsonSlurper = new JsonSlurper()
        def jsonBody = jsonSlurper.parseText(json)
        assert jsonBody instanceof Map

        def name = jsonBody.get("name")
        def network_id = jsonBody.get("network_id")

        return new Application(network_id,name)
    }

    private def Application(network_id, name)
    {
        this.network_id = network_id
        this.name = name;
    }

    public def getNetworkId()
    {
        return this.network_id
    }

    public def getApplicationName()
    {
        return this.name
    }
}
