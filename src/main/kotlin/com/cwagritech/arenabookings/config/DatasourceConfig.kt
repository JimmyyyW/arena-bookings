package com.cwagritech.arenabookings.config

import ch.qos.logback.core.db.DataSourceConnectionSource
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI
import java.net.URISyntaxException
import javax.sql.DataSource


@Configuration
@ConditionalOnProperty(value = ["use-heroku-sql"], havingValue = "true")
class DatasourceConfig {

    @Bean
    @Throws(URISyntaxException::class)
    fun dataSource(): DataSource? {
        val dbUri = URI(System.getenv("DATABASE_URL"))
        val userName = dbUri.userInfo.split(":")[0]
        val password = dbUri.userInfo.split(":")[1]
        val host = dbUri.host
        val port = dbUri.port

        return DataSourceBuilder.create()
            .url(System.getenv("DATABASE_URL"))
            .username(userName)
            .password(password)
            .driverClassName("org.postgresql.Driver")
            .build()

    }
}