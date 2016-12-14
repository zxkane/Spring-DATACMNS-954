package com.github.zxkane.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Web security configuration enabling:
 */
@Configuration
@ConditionalOnWebApplication
@Profile("!test")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 60 * 24 * 3)
@Slf4j
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.config.annotation.web.configuration.
     * WebSecurityConfigurerAdapter#configure(org.springframework.security.
     * config.annotation.web.builders.HttpSecurity)
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().cacheControl().disable().and().authorizeRequests()
                .anyRequest().permitAll().and().logout().logoutRequestMatcher(
                new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").and()
                .exceptionHandling();
    }
}
