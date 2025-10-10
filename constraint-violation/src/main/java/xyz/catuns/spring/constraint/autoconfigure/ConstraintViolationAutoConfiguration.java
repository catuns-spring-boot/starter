package xyz.catuns.spring.constraint.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import xyz.catuns.spring.constraint.autoconfigure.condition.*;
import xyz.catuns.spring.constraint.handler.GlobalDataIntegrityExceptionHandler;
import xyz.catuns.spring.constraint.parser.ConstraintViolationParser;
import xyz.catuns.spring.constraint.parser.ConstraintViolationStrategy;
import xyz.catuns.spring.constraint.strategy.generic.GenericStrategy;
import xyz.catuns.spring.constraint.strategy.h2.H2ForeignKeyStrategy;
import xyz.catuns.spring.constraint.strategy.h2.H2NotNullStrategy;
import xyz.catuns.spring.constraint.strategy.h2.H2UniqueStrategy;
import xyz.catuns.spring.constraint.strategy.mysql.MariaDBUniqueStrategy;
import xyz.catuns.spring.constraint.strategy.mysql.MySQLDuplicateEntryStrategy;
import xyz.catuns.spring.constraint.strategy.mysql.MySQLForeignKeyStrategy;
import xyz.catuns.spring.constraint.strategy.mysql.MySQLNotNullStrategy;
import xyz.catuns.spring.constraint.strategy.oracle.OracleCheckStrategy;
import xyz.catuns.spring.constraint.strategy.oracle.OracleForeignKeyStrategy;
import xyz.catuns.spring.constraint.strategy.oracle.OracleNotNullStrategy;
import xyz.catuns.spring.constraint.strategy.oracle.OracleUniqueStrategy;
import xyz.catuns.spring.constraint.strategy.postgresql.PostgreSQLCheckStrategy;
import xyz.catuns.spring.constraint.strategy.postgresql.PostgreSQLForeignKeyStrategy;
import xyz.catuns.spring.constraint.strategy.postgresql.PostgreSQLNotNullStrategy;
import xyz.catuns.spring.constraint.strategy.postgresql.PostgreSQLUniqueStrategy;
import xyz.catuns.spring.constraint.strategy.sqlserver.SQLServerForeignKeyStrategy;
import xyz.catuns.spring.constraint.strategy.sqlserver.SQLServerNotNullStrategy;
import xyz.catuns.spring.constraint.strategy.sqlserver.SQLServerUniqueStrategy;

import java.util.ArrayList;
import java.util.List;

import static xyz.catuns.spring.constraint.Constants.CONSTRAINT_CONFIG_PROPERTY_PREFIX;

@Slf4j
@AutoConfiguration
@ConditionalOnClass({DataIntegrityViolationException.class})
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = CONSTRAINT_CONFIG_PROPERTY_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(ConstraintViolationProperties.class)
public class ConstraintViolationAutoConfiguration {

    @Bean
    @ConditionalOnPostgreSQL
    @ConditionalOnMissingBean(name = "postgresqlUniqueStrategy")
    public PostgreSQLUniqueStrategy postgresqlUniqueStrategy() {
        log.debug("Registering PostgreSQL unique constraint strategy");
        return new PostgreSQLUniqueStrategy();
    }

    @Bean
    @ConditionalOnPostgreSQL
    @ConditionalOnMissingBean(name = "postgresqlForeignKeyStrategy")
    public PostgreSQLForeignKeyStrategy postgresqlForeignKeyStrategy() {
        log.debug("Registering PostgreSQL foreign key strategy");
        return new PostgreSQLForeignKeyStrategy();
    }

    @Bean
    @ConditionalOnPostgreSQL
    @ConditionalOnMissingBean(name = "postgresqlNotNullStrategy")
    public PostgreSQLNotNullStrategy postgresqlNotNullStrategy() {
        log.debug("Registering PostgreSQL not-null strategy");
        return new PostgreSQLNotNullStrategy();
    }

    @Bean
    @ConditionalOnPostgreSQL
    @ConditionalOnMissingBean(name = "postgresqlCheckStrategy")
    public PostgreSQLCheckStrategy postgresqlCheckStrategy() {
        log.debug("Registering PostgreSQL check constraint strategy");
        return new PostgreSQLCheckStrategy();
    }

    // Oracle Strategies
    @Bean
    @ConditionalOnOracle
    @ConditionalOnMissingBean(name = "oracleUniqueStrategy")
    public OracleUniqueStrategy oracleUniqueStrategy() {
        log.debug("Registering Oracle unique constraint strategy");
        return new OracleUniqueStrategy();
    }

    @Bean
    @ConditionalOnOracle
    @ConditionalOnMissingBean(name = "oracleForeignKeyStrategy")
    public OracleForeignKeyStrategy oracleForeignKeyStrategy() {
        log.debug("Registering Oracle foreign key strategy");
        return new OracleForeignKeyStrategy();
    }

    @Bean
    @ConditionalOnOracle
    @ConditionalOnMissingBean(name = "oracleNotNullStrategy")
    public OracleNotNullStrategy oracleNotNullStrategy() {
        log.debug("Registering Oracle not-null strategy");
        return new OracleNotNullStrategy();
    }

    @Bean
    @ConditionalOnOracle
    @ConditionalOnMissingBean(name = "oracleCheckStrategy")
    public OracleCheckStrategy oracleCheckStrategy() {
        log.debug("Registering Oracle check constraint strategy");
        return new OracleCheckStrategy();
    }

    // MySQL Strategies
    @Bean
    @ConditionalOnMySQL
    @ConditionalOnMissingBean(name = "mysqlDuplicateEntryStrategy")
    public MySQLDuplicateEntryStrategy mysqlDuplicateEntryStrategy() {
        log.debug("Registering MySQL duplicate entry strategy");
        return new MySQLDuplicateEntryStrategy();
    }

    @Bean
    @ConditionalOnMySQL
    @ConditionalOnMissingBean(name = "mysqlForeignKeyStrategy")
    public MySQLForeignKeyStrategy mysqlForeignKeyStrategy() {
        log.debug("Registering MySQL foreign key strategy");
        return new MySQLForeignKeyStrategy();
    }

    @Bean
    @ConditionalOnMySQL
    @ConditionalOnMissingBean(name = "mysqlNotNullStrategy")
    public MySQLNotNullStrategy mysqlNotNullStrategy() {
        log.debug("Registering MySQL not-null strategy");
        return new MySQLNotNullStrategy();
    }

    // MariaDB Strategies
    @Bean
    @ConditionalOnMariaDB
    @ConditionalOnMissingBean(name = "mariaDBUniqueStrategy")
    public MariaDBUniqueStrategy mariaDBUniqueStrategy() {
        log.debug("Registering MariaDB unique constraint strategy");
        return new MariaDBUniqueStrategy();
    }

    // SQL Server Strategies
    @Bean
    @ConditionalOnSQLServer
    @ConditionalOnMissingBean(name = "sqlServerUniqueStrategy")
    public SQLServerUniqueStrategy sqlServerUniqueStrategy() {
        log.debug("Registering SQL Server unique constraint strategy");
        return new SQLServerUniqueStrategy();
    }

    @Bean
    @ConditionalOnSQLServer
    @ConditionalOnMissingBean(name = "sqlServerForeignKeyStrategy")
    public SQLServerForeignKeyStrategy sqlServerForeignKeyStrategy() {
        log.debug("Registering SQL Server foreign key strategy");
        return new SQLServerForeignKeyStrategy();
    }

    @Bean
    @ConditionalOnSQLServer
    @ConditionalOnMissingBean(name = "sqlServerNotNullStrategy")
    public SQLServerNotNullStrategy sqlServerNotNullStrategy() {
        log.debug("Registering SQL Server not-null strategy");
        return new SQLServerNotNullStrategy();
    }

    // H2 Strategies
    @Bean
    @ConditionalOnH2
    @ConditionalOnMissingBean(name = "h2UniqueStrategy")
    public H2UniqueStrategy h2UniqueStrategy() {
        log.debug("Registering H2 unique constraint strategy");
        return new H2UniqueStrategy();
    }

    @Bean
    @ConditionalOnH2
    @ConditionalOnMissingBean(name = "h2ForeignKeyStrategy")
    public H2ForeignKeyStrategy h2ForeignKeyStrategy() {
        log.debug("Registering H2 foreign key strategy");
        return new H2ForeignKeyStrategy();
    }

    @Bean
    @ConditionalOnH2
    @ConditionalOnMissingBean(name = "h2NotNullStrategy")
    public H2NotNullStrategy h2NotNullStrategy() {
        log.debug("Registering H2 not-null strategy");
        return new H2NotNullStrategy();
    }

    // Generic fallback strategy (always registered)
    @Bean
    @ConditionalOnMissingBean(GenericStrategy.class)
    public GenericStrategy genericStrategy() {
        log.debug("Registering generic fallback strategy");
        return new GenericStrategy();
    }

    // Parser bean
    @Bean
    @ConditionalOnMissingBean
    public ConstraintViolationParser constraintViolationParser(
            List<ConstraintViolationStrategy> strategies,
            GenericStrategy genericStrategy
    ) {
        List<ConstraintViolationStrategy> allStrategies = new ArrayList<>(strategies);

        // Ensure generic strategy is last
        allStrategies.remove(genericStrategy);
        allStrategies.add(genericStrategy);

        log.info("Constraint Violation Parser initialized with {} strategies", allStrategies.size());

        return new ConstraintViolationParser(allStrategies);
    }

    // Exception handler bean
    @Bean
    @ConditionalOnMissingBean
    public GlobalDataIntegrityExceptionHandler globalDataIntegrityExceptionHandler(
            ConstraintViolationParser parser,
            ConstraintViolationProperties properties
    ) {
        log.info("Registering Global Data Integrity Exception Handler");
        return new GlobalDataIntegrityExceptionHandler(parser, properties);
    }
}