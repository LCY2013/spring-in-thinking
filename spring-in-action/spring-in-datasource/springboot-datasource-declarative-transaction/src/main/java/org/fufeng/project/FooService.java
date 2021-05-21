package org.fufeng.project;

public interface FooService {
    void insertRecord();
    void insertThenRollback() throws RollbackException;
    void invokeInsertThenRollback() throws RollbackException;
    void invokeBeanInsertThenRollback() throws RollbackException;
    void insertThenRollbackPropagation() throws RollbackException;
    void insertThenRollbackMainPropagation() throws RuntimeException;
    void insertThenRollbackMethod() throws RuntimeException;
    void insertRuntimeExceptionMethod() throws Exception;
}
