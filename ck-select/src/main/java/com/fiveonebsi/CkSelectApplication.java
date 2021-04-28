package com.fiveonebsi;

import com.github.pagehelper.dialect.helper.MySqlDialect;
import com.github.pagehelper.page.PageAutoDialect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.fiveonebsi.dao")
public class CkSelectApplication {

    public static void main(String[] args) {
        PageAutoDialect.registerDialectAlias("clickhouse", MySqlDialect.class);
        SpringApplication.run(CkSelectApplication.class, args);
    }

}
