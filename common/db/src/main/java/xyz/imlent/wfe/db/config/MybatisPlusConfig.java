package xyz.imlent.wfe.db.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.DB2Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wfee
 */
@Configuration
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor().setLimit(100).setDialectType(DbType.MYSQL.getDb());
    }

    /**
     * 填充公告字段
     *
     * @return
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new DbMetaObjectHandler();
    }
}
