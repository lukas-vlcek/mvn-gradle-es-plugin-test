/*
 * Copyright [2019] [Foo]
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
 *
 */

package org.foo.rest;

import static org.elasticsearch.rest.RestRequest.Method.GET;

import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.*;
import org.elasticsearch.rest.action.RestResponseListener;
import org.foo.action.FooAction;
import org.foo.action.FooActionRequest;
import org.foo.action.FooActionResponse;

/**
 * RestFooAction.
 */
public class RestFooAction extends BaseRestHandler {

    @Inject
    public RestFooAction(Settings settings, RestController controller) {
        super(settings);
        controller.registerHandler(GET, "/_foo", this);
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) {
        FooActionRequest fooActionRequest = new FooActionRequest();
        return channel -> client.execute(FooAction.INSTANCE, fooActionRequest,
                new RestResponseListener<FooActionResponse>(channel) {
                    @Override
                    public RestResponse buildResponse(FooActionResponse fooActionResponse) throws Exception {
                        return new BytesRestResponse(RestStatus.OK, "Yo!");
                    }
                });
    }

}
