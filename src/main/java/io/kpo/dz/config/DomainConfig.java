package io.kpo.dz.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("io.firzer.dz.domain")
@EnableJpaRepositories("io.firzer.dz.repos")
@EnableTransactionManagement
public class DomainConfig {
}
