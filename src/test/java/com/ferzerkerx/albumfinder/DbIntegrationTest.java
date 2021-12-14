package com.ferzerkerx.albumfinder;

import com.ferzerkerx.albumfinder.config.DbTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@Import(DbTestConfig.class)
@Transactional(transactionManager = "transactionManager")
@Rollback
public abstract class DbIntegrationTest {

    @Autowired
    private SessionFactory sessionFactory;

    protected void flush() {
        sessionFactory.getCurrentSession().flush();
    }

}

