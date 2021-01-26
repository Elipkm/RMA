package at.htlpinkafeld.RMA_backend_java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
        /*http
                //all protected paths
                .authorizeRequests()
                .antMatchers("GET", "/rma/usernames").authenticated()
                .antMatchers("POST","/rma/register").permitAll()
                .anyRequest().authenticated()
                .and()
                // configure to actually log in
                .formLogin()
                .loginPage("/login.html")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout");
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
    }

         */}

}
