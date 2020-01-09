### seata：分布式事务



#### 1、github地址

```
https://github.com/seata/seata
```

#### 2、下载 v1.0.0-GA 版本

```
https://github.com/seata/seata/releases/tag/v1.0.0
```

#### 3、script下有很多脚本(数据库、nacos等脚本)

```
https://github.com/seata/seata/tree/develop/script
```

#### 4、解压安装包进行安装

#### 5、修改register.conf

<!--使用nacos作为注册中心和配置中心，serverAddr为nacos的地址-->

<!--使用nacos作为注册中心后conf文件夹下的file.conf就可忽略不配置了-->

```
registry {
  # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
  type = "nacos"

  nacos {
    serverAddr = "localhost"
    namespace = ""
    cluster = "default"
  }
}

config {
  # file、nacos 、apollo、zk、consul、etcd3
  type = "nacos"

  nacos {
    serverAddr = "localhost"
    namespace = ""
  }
}
```

#### 6、将seata配置（file.conf）写入配置中心

```
https://github.com/seata/seata/tree/develop/script/config-center
```

<!--将config.txt和nacos-config.sh拷贝到同一文件加下，运行nacos-config.sh-->

```
./nacos-config.sh localhost
```

<!--配置文件就写入nacos配置中心了 localhost为nacos地址-->

#### 7、启动seata

<!--进入bin目录-->

```
./seata-server.sh
```

<!--启动如果报内存分配不够，修改seata-server.sh，调整内存-->

```
vim seata-server.sh
```
<!--Xmx256m -Xms256m  从2048修改为256-->

```
exec "$JAVACMD" $JAVA_OPTS -server -Xmx256m -Xms256m -Xmn1024m -Xss512k -XX:SurvivorRatio=10 -XX:MetaspaceSize=128m
```

#### 8、cloud应用配置(程序和seata必须可以互相访问)

##### (1)、maven引入

```
<dependency>
	<groupId>com.alibaba.cloud</groupId>
	<artifactId>spring-cloud-alibaba-seata</artifactId>
	<version>2.1.0.RELEASE/version>
</dependency>
```

~~~
<dependency>
 	<groupId>io.seata</groupId>
	<artifactId>seata-all</artifactId>
 	<version>1.0.0</version>
</dependency>
~~~

##### (2)、resource文件夹下加入register.conf

<!--默认和seata下的register.con一样，只要能成功访问同一nacos就行，如果不加入改文件，程序启动会报错-->

##### (3)、applicatiom.yml文件配置

```
spring:
  cloud:
    alibaba:
      seata:
        tx-service-group: wfe-user-consumer
```

#####  (4)、nacos配置中心也需加入配置文件

<!-- Data Id:service.vgroup_mapping.wfe-user-consumer -->

<!-- 配置内容：default -->

##### (5)、代理数据源配置

<!--官网配置-->

```
https://github.com/seata/seata-samples/blob/master/springcloud-nacos-seata/base-framework-mysql-support/src/main/java/com/work/framework/config/MyBatisConfig.java
```

```
package xyz.imlent.wfe.transaction.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/**
 * 分布式事务数据源配置
 *
 * @author wfee
 */
@Configuration
public class DataSourceConfiguration {

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(DataSourceProxy dataSourceProxy) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSourceProxy);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath:xyz/imlent/wfe/**/mapper/*Mapper.xml"));

        SqlSessionFactory factory;
        try {
            factory = bean.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return factory;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 从配置文件获取属性构造datasource
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }

    /**
     * 构造datasource代理对象
     */
    @Primary
    @Bean("dataSource")
    public DataSourceProxy dataSourceProxy(DataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }


    /**
     * MP 自带分页插件
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }
}

```

##### (6)全局事务

```
@GlobalTransactional(rollbackFor = Exception.class)
```

<!--只需要在需要的业务类上添加注解即可-->

#### 9、将seata信息保存在数据库
<!--store.mode改为db-->

```
store.mode=db
store.db.datasource=druid
store.db.db-type=mysql
store.db.driver-class-name=com.mysql.jdbc.Driver
store.db.url=jdbc:mysql://127.0.0.1:3306/seata?useUnicode=true
store.db.user=root
store.db.password=ZTWFEE
store.db.min-conn=1
store.db.max-conn=3
store.db.global.table=global_table
store.db.branch.table=branch_table
store.db.query-limit=100
store.db.lock-table=lock_table
```

