package org.fufeng.project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class FooServiceImpl implements FooService, ApplicationContextAware {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ApplicationContext applicationContext;

    @Override
    @Transactional
    public void insertRecord() {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('java')");
    }

    @Override
    @Transactional(rollbackFor = RollbackException.class)
    public void insertThenRollback() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('go')");
        throw new RollbackException();
    }

    @Override
    public void invokeInsertThenRollback() throws RollbackException {
        insertThenRollback();
    }

    @Override
    public void invokeBeanInsertThenRollback() throws RollbackException {
        // 通过SpringContext实现bean流程回滚
        this.applicationContext.getBean(FooService.class).insertThenRollback();
    }

    @Override
    @Transactional(rollbackFor = RollbackException.class,propagation = Propagation.NESTED)
    public void insertThenRollbackPropagation() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('5-go')");
        //throw new RollbackException();
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insertThenRollbackMainPropagation() throws RuntimeException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('4-go')");
        try {
            this.applicationContext.getBean(FooService.class).insertThenRollbackPropagation();
        }catch (Exception e){
            //throw new RuntimeException();
        }
        throw new RuntimeException();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insertThenRollbackMethod() throws RuntimeException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('6-go')");
        insert();
    }

    public void insert(){
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('7-go')");
        throw new RuntimeException();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertRuntimeExceptionMethod() throws Exception{
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('8-go')");
        throw new RuntimeException("for purpose: ");
        // throw new RollbackException();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
