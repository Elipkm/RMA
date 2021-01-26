package at.htlpinkafeld.RMA_backend_java.service;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class ResponseServerFilter implements ContainerResponseFilter {

    private final String[] allowedUrlsForCORS = new String[]{
            "*"
    };

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        this.enableCors(responseContext);
    }

    private void enableCors(ContainerResponseContext responseContext){
        for(String allowedUrl : allowedUrlsForCORS){
            responseContext.getHeaders().add("Access-Control-Allow-Origin",allowedUrl);
        }
    }
}
