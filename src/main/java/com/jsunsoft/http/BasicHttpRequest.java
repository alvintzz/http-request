package com.jsunsoft.http;

/*
 * Copyright 2017 Benik Arakelyan
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

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Basic implementation of HttpRequest
 */
class BasicHttpRequest implements HttpRequest {
    private final CloseableHttpClient closeableHttpClient;
    private final Collection<Header> defaultHeaders;
    private final Collection<NameValuePair> defaultRequestParameters;
    private final RetryContext defaultRetryContext;

    BasicHttpRequest(CloseableHttpClient closeableHttpClient, Collection<Header> defaultHeaders, Collection<NameValuePair> defaultRequestParameters, RetryContext defaultRetryContext) {
        this.closeableHttpClient = ArgsCheck.notNull(closeableHttpClient, "closeableHttpClient");
        this.defaultHeaders = Collections.unmodifiableList(new ArrayList<>(ArgsCheck.notNull(defaultHeaders, "defaultHeaders")));
        this.defaultRequestParameters = Collections.unmodifiableList(new ArrayList<>(ArgsCheck.notNull(defaultRequestParameters, "defaultRequestParameters")));
        this.defaultRetryContext = defaultRetryContext;
    }

    @Override
    public WebTarget target(URI uri) {
        ArgsCheck.notNull(uri, "uri");
        return new BasicWebTarget(closeableHttpClient, new URIBuilder(uri), defaultHeaders, defaultRequestParameters, defaultRetryContext);
    }

    @Override
    public WebTarget target(String uri) {
        ArgsCheck.notNull(uri, "uri");
        try {
            return new BasicWebTarget(closeableHttpClient, new URIBuilder(uri), defaultHeaders, defaultRequestParameters, defaultRetryContext);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }
}
