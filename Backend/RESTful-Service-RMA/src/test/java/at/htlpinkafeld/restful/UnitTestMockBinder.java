package at.htlpinkafeld.restful;

import at.htlpinkafeld.restful.servlet.ApplicationBinder;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

public class UnitTestMockBinder extends ApplicationBinder {

    public UnitTestMockBinder() {
        super(true);
    }

    public void injectHereForUnitTest(Object... injectIn){
        ServiceLocator serviceLocator = ServiceLocatorUtilities.bind(this);
        for(Object object : injectIn){
            serviceLocator.inject(object);
        }
    }
}
