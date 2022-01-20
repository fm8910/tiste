package com.ni.tiste.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        public WebSecurityConfig() {
            super(true); // desactivamos todo
        }

        @Override
        protected void configure(HttpSecurity http) {
            // Dejamos vacio para que no se active el login por defecto

        }
}
