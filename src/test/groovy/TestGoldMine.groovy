import services.provide.client.Goldmine


class TestGoldMine extends GroovyTestCase {

    def prvd

    void test()
    {
        prvd = Goldmine.init("mytoken")

        prvd.createTransaction("test")
    }

}
