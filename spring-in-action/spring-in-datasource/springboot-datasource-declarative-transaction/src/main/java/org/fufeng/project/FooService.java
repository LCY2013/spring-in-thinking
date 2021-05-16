package org.fufeng.project;

public interface FooService {
    void insertRecord();
    void insertThenRollback() throws RollbackException;
    void invokeInsertThenRollback() throws RollbackException;
    void invokeBeanInsertThenRollback() throws RollbackException;
}
