package com.zimbra.cs.datasource;

import java.util.List;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.account.DataSource;

public class YahooContactsImport implements DataSource.DataImport {
    private DataSource ds = null;
    public YahooContactsImport(DataSource ds) {
        this.ds = ds;
    }
    @Override
    public void test() throws ServiceException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void importData(List<Integer> folderIds, boolean fullSync) throws ServiceException {
        ZimbraLog.datasource.debug("Importing contacts from Yahoo. Refresh token %s. URL: %s", ds.getOauthRefreshToken(), ds.getOauthRefreshTokenUrl());
    }
}
