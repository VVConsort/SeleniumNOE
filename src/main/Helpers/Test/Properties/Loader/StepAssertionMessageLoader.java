package Helpers.Test.Properties.Loader;

import Helpers.Test.Properties.StepAssertionMessages;

public class StepAssertionMessageLoader extends BaseLoader{

    public static final String CHECK_CUSTOMER_PRESENCE_ON_RCU = "CHECK_CUSTOMER_PRESENCE_ON_RCU";

    public StepAssertionMessageLoader(String propertiesFilePath) {
        super(propertiesFilePath);
    }

    public void setProperties() {
        StepAssertionMessages.CHECK_CUSTOMER_PRESENCE_ON_RCU = currentProperties.getProperty(CHECK_CUSTOMER_PRESENCE_ON_RCU);
    }
}
