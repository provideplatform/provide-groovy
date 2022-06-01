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

import spock.lang.*

import services.provide.client.microservices.Goldmine

class GoldmineSpec extends Specification {

    def prvdClient

    def setup() {
        this.prvdClient = Goldmine.init('some-api-token')
    }

    def 'tx broadcast with invalid key'() {
        when:
            def txResult = prvdClient.createTransaction([:])

        then:
            txResult != null
            txResult.size() == 2
            txResult[0] == 401
    }
}
