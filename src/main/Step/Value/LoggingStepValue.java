package Step.Value;

import Helpers.Test.TestSuiteProperties.TestSuiteProperties;

public class LoggingStepValue {

    public String url;
    public String terminalKey;
    public String chromeProfile;
    public String chromeProfilePath;
    public String username;
    public String password;

    public LoggingStepValue() {
        url = TestSuiteProperties.OB_POS_URL;
        terminalKey = TestSuiteProperties.TERMINAL_KEY;
        chromeProfile = TestSuiteProperties.CHROME_PROFILE;
        chromeProfilePath = TestSuiteProperties.CHROME_PROFILE_PATH;
        username = TestSuiteProperties.USERNAME;
        password = TestSuiteProperties.PASSWORD;
    }

    public LoggingStepValue(String url, String terminalKey ,String chromeProfilePath,String chromeProfile,String username,String password)
    {
        this.url = url;
        this.terminalKey = terminalKey;
        this.chromeProfile = chromeProfile;
        this.chromeProfilePath = chromeProfilePath;
        this.username = username;
        this.password = password;
    }
}
