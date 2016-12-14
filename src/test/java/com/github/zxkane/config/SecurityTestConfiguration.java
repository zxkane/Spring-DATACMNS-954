/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.zxkane.config;

import com.github.zxkane.model.UserInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.SessionRepositoryFilter;

import java.util.Map;

/**
 * Web security configuration enabling:
 * <ol>
 * <li>HTTP Basic authentication with Spring Security</li>
 * <li>Configuring Spring Session to store sessions in a {@link Map} and expose
 * the session using an HTTP header.</li>
 * </ol>
 *
 * @author Oliver Gierke
 */
@Configuration
@ConditionalOnWebApplication
@Profile("test")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
public class SecurityTestConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * Configures a {@link SessionRepositoryFilter} to main an authentication
     * session in a plain {@link Map} and exposing a reference to the
     * authentication session in an HTTP header.
     *
     * @return
     */
    @Bean
    public SessionRepositoryFilter<?> sessionRepositoryFilter() {

        SessionRepository<ExpiringSession> repository = new MapSessionRepository();

        SessionRepositoryFilter<ExpiringSession> filter = new SessionRepositoryFilter<ExpiringSession>(repository);
        filter.setHttpSessionStrategy(new HeaderHttpSessionStrategy());

        return filter;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.config.annotation.web.configuration.
     * WebSecurityConfigurerAdapter#configure(org.springframework.security.
     * config.annotation.web.builders.HttpSecurity)
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().anyRequest().permitAll().and()
                .httpBasic().realmName("realm");
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                switch (username) {
                    case "staffUsername":
                        return staffUser();
                }
                throw new UsernameNotFoundException(username + " was not found.");
            }
        };
    }

    @Bean("staff")
    public UserInfo staffUser() {
        return UserInfo.builder().username("staff").build();
    }
}
