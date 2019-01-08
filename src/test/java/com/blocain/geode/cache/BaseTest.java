package com.blocain.geode.cache;

import com.blocain.geode.entity.Person;
import org.apache.geode.cache.Cache;
import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.RegionFactory;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.query.*;
import org.junit.Test;

import java.util.Date;
import java.util.Iterator;

public class BaseTest {


    /**
     * 基本缓存操作测试
     * @throws NameResolutionException
     * @throws TypeMismatchException
     * @throws QueryInvocationTargetException
     * @throws FunctionDomainException
     */
    @Test
    public void testCache() throws NameResolutionException, TypeMismatchException, QueryInvocationTargetException, FunctionDomainException {
        //创建服务端节点实例
        Cache cache = new CacheFactory().create();
        Region<Long, Person> region =
                cache.getRegion("Person");
        if (region == null) {
            RegionFactory<Long, Person> factory =
                    cache.createRegionFactory();
            region = factory.create("Person");
        }
        Person p = new Person();
        p.setId(1l);
        p.setName("test");
        p.setCreateDate(new Date());
        //基本缓存操作
        region.put(p.getId(), p);
        Person p1 = region.get(p.getId());
        System.out.println(p1);
        region.remove(p.getId());
        p1 = region.get(p.getId());
        if (null == p1) {
            System.out.println("缓存条目添加并删除成功");
        }
        region.put(p.getId(), p);
        region.clear();
        p1 = region.get(p.getId());
        if (null == p1) {
            System.out.println("区域缓存删除成功");
        }
        //OQL操作
        region.put(p.getId(), p);
        QueryService queryService = cache.getQueryService();
        Query query = queryService.newQuery("select * from /Person;");
        SelectResults results = (SelectResults) query.execute();
        Iterator it = results.iterator();
        while (it.hasNext()) {
            p1 = (Person) it.next();
            System.out.println("OQL查询结果：" + p1.toString());
        }

        //性能
        long start = System.currentTimeMillis();
        for (int i = 0; i < 50000; i++) {
            p.setId(System.currentTimeMillis());
            region.put(p.getId(), p);
        }
        long end = System.currentTimeMillis();
        System.out.println("插入50万条数据耗时（毫秒）：" + (end-start));
        //关闭节点，释放资源
        cache.close();
    }


}
