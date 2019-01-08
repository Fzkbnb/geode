package com.blocain.geode;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

//@ClientCacheApplication
@CacheServerApplication
@EnableLocator
//@ComponentScan(basePackages = "com.blocain.geode.service")
//@EnablePdx(serializerBeanName = "compositePdxSerializer")
@EnableEntityDefinedRegions(basePackages = {"com.blocain.geode.entity"})
@EnableGemfireRepositories(basePackages = {"com.blocain.geode.repository"})

public class GeodeStart {
    public static void main(String[] args) {
        String[] config = new String[]{"spring.xml"};
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(config);
        context.start();
    }
}
