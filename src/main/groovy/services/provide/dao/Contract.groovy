package services.provide.dao



public class Contract {
    private id = null
    private name = null
    private address = null


    public def Contract(id, name, address)
    {
        this.id = id
        this.name = name
        this.address = address
    }

    public def getId() {
        return this.id
    }

    public def getName() {
        return this.name
    }

    public def getAddress() {
        return this.address
    }
}
