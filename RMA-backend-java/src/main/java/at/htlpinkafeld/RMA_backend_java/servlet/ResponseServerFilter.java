package at.htlpinkafeld.RMA_backend_java.servlet;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class ResponseServerFilter implements ContainerResponseFilter {

    private final String[] allowedUrlsForCORS = new String[]{
            "http://localhost:4200"
    };

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        this.enableCors(responseContext);
    }

    private void enableCors(ContainerResponseContext responseContext){
        for(String allowedUrl : allowedUrlsForCORS){
            responseContext.getHeaders().add("Access-Control-Allow-Origin",allowedUrl);
        }
        //responseContext.getHeaders().add("Access-Control-Allow-Credentials","true");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "Authorization");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type");
    }
}
