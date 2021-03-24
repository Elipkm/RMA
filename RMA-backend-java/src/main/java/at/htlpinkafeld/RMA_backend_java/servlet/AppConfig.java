package at.htlpinkafeld.RMA_backend_java.servlet;

public class AppConfig extends org.glassfish.jersey.server.ResourceConfig {

    public AppConfig(){
        ApplicationBinder rmaBinder = new ApplicationBinder(true);
        register(rmaBinder);
    }
}
