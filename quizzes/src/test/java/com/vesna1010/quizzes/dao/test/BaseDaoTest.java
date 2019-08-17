package com.vesna1010.quizzes.dao.test;

import javax.transaction.Transactional;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.vesna1010.quizzes.configuration.ConfigurationForDatabase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ConfigurationForDatabase.class })
@Sql(scripts = "classpath:sql/init.sql")
@Transactional
public abstract class BaseDaoTest {

}
