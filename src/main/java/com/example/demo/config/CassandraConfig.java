package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.ReactiveSession;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.cql.session.DefaultBridgedReactiveSession;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleTupleTypeFactory;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

@Configuration
@ComponentScan("com.example.demo.service")
@EnableCassandraRepositories(basePackages = { "com.example.demo.service" })
@EnableReactiveCassandraRepositories(basePackages = { "com.example.demo.service" })
public class CassandraConfig {

    @Bean
    public CassandraClusterFactoryBean cluster() {

        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints("localhost");
        cluster.setUsername("cassandra");
        cluster.setPassword("cassandra");
        cluster.setJmxReportingEnabled(false);

        return cluster;
    }

    @Bean
    public CassandraMappingContext mappingContext() {

        CassandraMappingContext mappingContext = new CassandraMappingContext(
                new SimpleUserTypeResolver(cluster().getObject(),"dev"),
                new SimpleTupleTypeFactory(cluster().getObject())
                );
        return mappingContext;
    }

    @Bean
    public CassandraConverter converter() {
        return new MappingCassandraConverter(mappingContext());
    }

    @Bean
    public CassandraSessionFactoryBean session()  {

        CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
        session.setCluster(cluster().getObject());
        session.setKeyspaceName("dev");
        session.setConverter(converter());
        session.setSchemaAction(SchemaAction.NONE);

        return session;
    }

    @Bean
    public CassandraOperations cassandraTemplate() {
        return new CassandraTemplate(session().getObject(), converter());
    }

    @Bean
    public ReactiveCassandraOperations reactiveCassandraTemplate(){
        ReactiveSession session = new DefaultBridgedReactiveSession(session().getObject());
        return new ReactiveCassandraTemplate(session, converter());
    }
}