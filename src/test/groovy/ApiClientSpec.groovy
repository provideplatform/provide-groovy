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

import services.provide.client.ApiClient

class ApiClientSpec extends Specification {
    
    def prvdClient

    def setup() {
        this.prvdClient = ApiClient.init(null, null, 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7fSwiZXhwIjpudWxsLCJpYXQiOjE1MzQyNjY1NDcsImp0aSI6ImZhNmUyNzY0LTM5ZmEtNDNlOC1hMDNhLWYzNTVmNDRmNmRjZSIsInN1YiI6ImFwcGxpY2F0aW9uOmNiNTgzZmE1LWEzODctNDE5OC1hNTViLWFlZDM3MTM1YzA1MCJ9.R7akJwCrKCt_tyO9UybE-kqXxpYNZFQYkrdBuEsf0b8')
    }

    def 'it should use production as the default api host'() {
        expect:
            prvdClient.baseUrl == 'https://api.provide.services/api/v1/'
    }

    def 'it should expose the application id in the token'() {
        expect:
            prvdClient.getApplicationId() == 'cb583fa5-a387-4198-a55b-aed37135c050'
    }
}
