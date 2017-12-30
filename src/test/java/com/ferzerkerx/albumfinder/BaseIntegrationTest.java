package com.ferzerkerx.albumfinder;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfig.class})
@Transactional(transactionManager="transactionManager")
@Rollback
public abstract class BaseIntegrationTest {

    @Autowired
    private SessionFactory sessionFactory;

    protected void flush() {
        sessionFactory.getCurrentSession().flush();
    }

}
