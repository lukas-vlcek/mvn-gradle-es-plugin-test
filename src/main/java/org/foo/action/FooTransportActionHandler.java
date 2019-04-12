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

package org.foo.action;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.support.ActionFilters;
import org.elasticsearch.action.support.HandledTransportAction;
import org.elasticsearch.cluster.metadata.IndexNameExpressionResolver;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.threadpool.ThreadPool;
import org.elasticsearch.transport.TransportService;

/**
 * FooTransportActionHandler.
 */
public class FooTransportActionHandler extends HandledTransportAction<FooActionRequest, FooActionResponse> {

    @Inject
    public FooTransportActionHandler(Settings settings, ThreadPool threadPool,
                                     TransportService transportService, ActionFilters actionFilters,
                                     IndexNameExpressionResolver indexNameExpressionResolver) {
        super(settings, FooAction.NAME, threadPool, transportService, actionFilters, indexNameExpressionResolver,
                FooActionRequest::new);
    }

    @Override
    protected void doExecute(FooActionRequest request, ActionListener<FooActionResponse> listener) {
        listener.onResponse(new FooActionResponse());
    }
}
