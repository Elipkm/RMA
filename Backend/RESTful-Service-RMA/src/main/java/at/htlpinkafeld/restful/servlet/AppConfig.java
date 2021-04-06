package at.htlpinkafeld.restful.servlet;

public class AppConfig extends org.glassfish.jersey.server.ResourceConfig {

    public AppConfig(){
        ApplicationBinder rmaBinder = new ApplicationBinder(true);
        register(rmaBinder);
    }
}
