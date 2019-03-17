package services.provide.dao

class Function {
    private String name = null
    private Field[] inputs = null
    private Field[] outputs = null
    private boolean isPayable = false

    public def Function() {}

    public def Function(String name, Field[] inputs, Field[] outputs, boolean isPayable) {
        this.name = name
        this.inputs = inputs
        this.outputs = outputs
        this.isPayable = isPayable
    }

    public def String getName() {
        return name
    }

    public def setName(String name) {
        this.name = name
    }

    public def Field[] getInputs() {
        return inputs
    }

    public def setInputs(Field[] inputs) {
        this.inputs = inputs
    }

    public def Field[] getOutputs() {
        return outputs
    }

    public def setOutputs(Field[] outputs) {
        this.outputs = outputs
    }

    public def boolean getIsPayable() {
        return isPayable
    }

    public def setIsPayable(boolean isPayable) {
        this.isPayable = isPayable
    }
}
