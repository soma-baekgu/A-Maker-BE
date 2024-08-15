package com.backgu.amaker.batch.config

import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.Properties
import javax.sql.DataSource

@Configuration
@Profile("!test")
@EnableConfigurationProperties(DatabaseProperties::class)
class DataSourceConfig(
    private val databaseProperties: DatabaseProperties,
) {
    @Primary
    @Bean
    fun batchDataSource(): DataSource =
        createDataSource(
            databaseProperties.batchDatasource.url,
            databaseProperties.batchDatasource.username,
            databaseProperties.batchDatasource.password,
            databaseProperties.batchDatasource.driverClassName,
        )

    @Bean
    fun domainDataSource(): DataSource =
        createDataSource(
            databaseProperties.domainDatasource.url,
            databaseProperties.domainDatasource.username,
            databaseProperties.domainDatasource.password,
            databaseProperties.domainDatasource.driverClassName,
        )

    @Bean(name = ["entityManagerFactory"])
    fun domainEntityManagerFactory(
        @Qualifier("domainDataSource") domainDataSource: DataSource,
    ): LocalContainerEntityManagerFactoryBean =
        LocalContainerEntityManagerFactoryBean().apply {
            dataSource = domainDataSource
            setPackagesToScan("com.backgu.amaker.infra.jpa")
            jpaVendorAdapter = HibernateJpaVendorAdapter()
            setJpaProperties(hibernateProperties())
        }

    @Bean(name = ["transactionManager"])
    fun domainTransactionManager(
        @Qualifier("entityManagerFactory") domainEntityManagerFactory: EntityManagerFactory,
    ): JpaTransactionManager =
        JpaTransactionManager().apply {
            entityManagerFactory = domainEntityManagerFactory
        }

    private fun createDataSource(
        url: String,
        username: String,
        password: String,
        driverClassName: String,
    ): DataSource =
        DataSourceBuilder
            .create()
            .url(url)
            .username(username)
            .password(password)
            .driverClassName(driverClassName)
            .build()

    private fun hibernateProperties(): Properties {
        val properties = Properties()
        properties["hibernate.physical_naming_strategy"] =
            "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy"
        properties["hibernate.implicit_naming_strategy"] =
            "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy"
        return properties
    }
}
