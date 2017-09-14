package com.zimbra.qa.unittest;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.zimbra.common.mailbox.FolderConstants;
import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.Cos;
import com.zimbra.cs.account.DataSource;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.datasource.DataSourceManager;
import com.zimbra.cs.ldap.LdapConstants;
import com.zimbra.soap.admin.type.DataSourceType;
import com.zimbra.soap.type.DataSource.ConnectionType;

public class TestYahooContactsDataSource {
    @Rule
    public static TestName testInfo = new TestName();

    private static String USER_NAME;
    private static String NAME_PREFIX;
    private static String REMOTE_USER = "grishick@yahoo.com";
    private Account account;

    private static final String DS_NAME = "TestYahooDataSource";
    @Before
    public void setUp()
    throws Exception {
        NAME_PREFIX = String.format("%s-%s", TestDataSource.class.getSimpleName(), testInfo.getMethodName()).toLowerCase();
        USER_NAME = String.format("%s-user1", NAME_PREFIX);

        cleanUp();

        account = TestUtil.createAccount(USER_NAME);

        if (!TestUtil.fromRunUnitTests) {
            TestUtil.cliSetup();
        }
    }

    @After
    public void tearDown()
    throws Exception {
        cleanUp();
    }

    public void cleanUp()
    throws Exception {
        TestUtil.deleteAccountIfExists(USER_NAME);
    }

    @Test
    public void test() throws Exception {
        // Create data source
        Provisioning prov = Provisioning.getInstance();
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(Provisioning.A_zimbraDataSourceEnabled, LdapConstants.LDAP_TRUE);
        attrs.put(Provisioning.A_zimbraDataSourceHost, "social.yahooapis.com");
        attrs.put(Provisioning.A_zimbraDataSourcePort, "443");
        attrs.put(Provisioning.A_zimbraDataSourceUsername, REMOTE_USER);
        attrs.put(Provisioning.A_zimbraDataSourceFolderId, "1");
        attrs.put(Provisioning.A_zimbraDataSourceImportClassName, "com.zimbra.cs.datasource.YahooContactsImport");
        attrs.put(Provisioning.A_zimbraDataSourceOAuthRefreshToken, "refresh-token");
        attrs.put(Provisioning.A_zimbraDataSourceOAuthRefreshTokenUrl, "https://api.login.yahoo.com/oauth2/get_token");
        attrs.put(Provisioning.A_zimbraDataSourceOAuthClientId, "dj0yJmk9M0QyTWZwUzZSRk95JmQ9WVdrOVRXcENiWEpDTm5VbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD04ZQ--");
        attrs.put(Provisioning.A_zimbraDataSourceOAuthClientSecret, "a491fc985d841ff16e52e79a63c070959c9b5160");
        DataSource ds = prov.createDataSource(account, DataSourceType.custom, DS_NAME, attrs);
        assertNotNull("DataSource should not be null", ds);
        DataSourceManager.importData(ds);
    }
}
