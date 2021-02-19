package at.htlpinkafeld.RMA_backend_java;

public class AppConfig extends org.glassfish.jersey.server.ResourceConfig {

    public AppConfig(){

        register(new ApplicationBinder());

    }
}
