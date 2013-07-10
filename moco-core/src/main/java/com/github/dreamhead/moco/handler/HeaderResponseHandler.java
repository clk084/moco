package com.github.dreamhead.moco.handler;

import com.github.dreamhead.moco.MocoConfig;
import com.github.dreamhead.moco.ResponseHandler;
import com.github.dreamhead.moco.resource.Resource;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;

public class HeaderResponseHandler implements ResponseHandler {
    private final ContentTypeDetector detector = new ContentTypeDetector();

    private final String name;
    private final Resource value;

    public HeaderResponseHandler(String name, Resource value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void writeToResponse(HttpRequest request, HttpResponse response) {
        if (detector.hasHeader(response, name)) {
            response.removeHeader(name);
        }

        HttpHeaders.addHeader(response, name, new String(value.asByteArray(request)));
    }

    @Override
    public ResponseHandler apply(final MocoConfig config) {
        return this;
    }
}
