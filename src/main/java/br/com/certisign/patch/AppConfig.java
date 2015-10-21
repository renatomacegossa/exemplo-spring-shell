package br.com.certisign.patch;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

@Configuration
@EnableScheduling
@EnableJpaRepositories( basePackages = "br.com.certisign.patch.repo" )
@EnableTransactionManagement
@ComponentScan( "br.com.certisign.patch" )
public class AppConfig {

    private static final Logger log = Logger.getLogger( AppConfig.class );

    Properties dbProp = new Properties();

    @Bean
    public DataSource dataSource() throws PropertyVetoException {

        if ( dbProp.isEmpty() ) {
            dbProp = loadProperties( "/conf/db.properties" );
        }

        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName( dbProp.getProperty( "db.driver" ) );
        ds.setUrl( dbProp.getProperty( "db.url" ) );
        ds.setUsername( dbProp.getProperty( "db.user" ) );
        ds.setPassword( dbProp.getProperty( "db.password" ) );
        ds.setInitialSize( Integer.valueOf( dbProp.getProperty( "db.pool.minPoolSize" ) ) );
        ds.setMaxActive( Integer.valueOf( dbProp.getProperty( "db.pool.maxPoolSize" ) ) );
        ds.setMaxOpenPreparedStatements( Integer.valueOf( dbProp.getProperty( "db.pool.maxPreparedStatements" ) ) );

        if ( dbProp.getProperty( "db.pool.maxWaitMillis" ) != null ) {
            ds.setMaxWait( Integer.valueOf( dbProp.getProperty( "db.pool.maxPreparedStatements" ) ) );
        }

        if ( dbProp.getProperty( "db.pool.testOnBorrow" ) != null ) {
            if ( dbProp.getProperty( "db.pool.testOnBorrow" ).equalsIgnoreCase( "true" ) ) {
                ds.setTestOnBorrow( true );
            }
        }

        if ( dbProp.getProperty( "db.pool.validationQuery" ) != null ) {
            ds.setValidationQuery( dbProp.getProperty( "db.pool.validationQuery" ) );
        }

        if ( dbProp.getProperty( "db.pool.removeAbandoned" ) != null ) {
            if ( dbProp.getProperty( "db.pool.removeAbandoned" ).equalsIgnoreCase( "true" ) ) {
                ds.setRemoveAbandoned( true );
            }
        }

        if ( dbProp.getProperty( "db.pool.removeAbandonedTimeout" ) != null ) {
            ds.setRemoveAbandonedTimeout( Integer.valueOf( dbProp.getProperty( "db.pool.removeAbandonedTimeout" ) ) );
        }

        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws PropertyVetoException {
        if ( dbProp.isEmpty() ) {
            dbProp = loadProperties( "/conf/db.properties" );
        }

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql( false );
        if ( dbProp.getProperty( "db.type" ).equals( "oracle" ) ) {
            vendorAdapter.setDatabase( Database.ORACLE );
        }
        else if ( dbProp.getProperty( "db.type" ).equals( "mysql" ) ) {
            vendorAdapter.setDatabase( Database.MYSQL );
        }
        else if ( dbProp.getProperty( "db.type" ).equals( "h2" ) ) {
            vendorAdapter.setDatabase( Database.H2 );
        }
        vendorAdapter.setGenerateDdl( true );

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter( vendorAdapter );
        factory.setPackagesToScan( getClass().getPackage().getName() );
        factory.setDataSource( dataSource() );
        factory.setJpaProperties( loadProperties( "/conf/hibernate.properties" ) );
        factory.setPersistenceUnitName( "dbadmin" );
        log.info( getClass().getPackage().getName() );
        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws PropertyVetoException {
        return new JpaTransactionManager();
    }

    private Properties loadProperties(String filename) {

        Properties props = new Properties();

        try {

            String workingDir = System.getProperty( "user.dir" );
            String path = workingDir + filename;

            log.info( String.format( "Procurando arquivo ( %s ) no path ( %s ).", filename, workingDir ) );

            if ( !new File( path ).exists() ) {

                log.warn( String.format( "Arquivo ( %s ) nao existe no path ( %s ).", filename, workingDir ) );

                String pathAdminCA = "/usr/certicenter/AdminCA";

                path = pathAdminCA + filename;

                log.info( String.format( "Procurando arquivo ( %s ) no path ( %s ).", filename, pathAdminCA ) );

                if ( !new File( path ).exists() ) {

                    log.warn( String.format( "Arquivo ( %s ) nao existe no path ( %s ).", filename, pathAdminCA ) );

                    throw new RuntimeException( "Não foi possivel carregar arquivo de configuracao: " + filename );
                }
            }

            props.load( new FileInputStream( path ) );

            for ( String s : props.stringPropertyNames() ) {
                log.info( s + " = " + props.getProperty( s ) );
            }
        } catch ( Throwable ex ) {
            log.error( "Erro ao carregar arquivo de propriedades:" + filename, ex );
        }

        return props;
    }
}