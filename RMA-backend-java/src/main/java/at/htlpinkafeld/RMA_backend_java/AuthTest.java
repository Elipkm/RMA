package at.htlpinkafeld.RMA_backend_java;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AuthTest extends WebSecurityConfigurerAdapter {

    public AuthTest(){
   /*     ClientRequestFilter tokenHeadersAdder = new ClientRequestFilter()
        {
            public void filter(ClientRequestContext ctx) throws IOException
            {
                final MultivaluedMap<String, Object> headers = ctx.getHeaders();
                headers.put("X-MyKey1", Collections.singletonList("MyValue1"));
            }
        };

        javax.ws.rs.core.Configuration clientConfig = new;
        Client client = ClientBuilder.newClient(clientConfig);
        client.register(tokenHeadersAdder);
        WebTarget webTarget = client.target("");  */
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }

}
