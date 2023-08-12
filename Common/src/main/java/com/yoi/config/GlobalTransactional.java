package com.yoi.config;

import io.seata.core.exception.TransactionException;
import io.seata.core.model.GlobalStatus;
import io.seata.tm.api.GlobalTransaction;
import io.seata.tm.api.GlobalTransactionRole;
import io.seata.tm.api.transaction.SuspendedResourcesHolder;

/**
 * @author 游弋
 * @create 2023-08-12 23:46
 */
public class GlobalTransactional implements GlobalTransaction {
    @Override
    public void begin() throws TransactionException {

    }

    @Override
    public void begin(int timeout) throws TransactionException {
        timeout = 300000;
    }

    @Override
    public void begin(int timeout, String name) throws TransactionException {

    }

    @Override
    public void commit() throws TransactionException {

    }

    @Override
    public void rollback() throws TransactionException {

    }

    @Override
    public SuspendedResourcesHolder suspend() throws TransactionException {
        return null;
    }

    @Override
    public void resume(SuspendedResourcesHolder suspendedResourcesHolder) throws TransactionException {

    }

    @Override
    public GlobalStatus getStatus() throws TransactionException {
        return null;
    }

    @Override
    public String getXid() {
        return null;
    }

    @Override
    public void globalReport(GlobalStatus globalStatus) throws TransactionException {

    }

    @Override
    public GlobalStatus getLocalStatus() {
        return null;
    }

    @Override
    public GlobalTransactionRole getGlobalTransactionRole() {
        return null;
    }
}
