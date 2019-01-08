package com.blocain.geode.cache;

import com.blocain.geode.entity.Person;
import com.blocain.geode.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})

public class SpringdataTest {
    @Autowired
    private PersonService personService;

    @Test
    public void testSpring() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 500000; i++) {
            personService.save(new Person(System.currentTimeMillis(), "test"));
        }
        long end = System.currentTimeMillis();
        System.out.println(">>>插入50万条数据耗时（毫秒）：" + (end - start));
    }
}
