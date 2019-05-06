package com.ftn.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecConfig extends WebSecurityConfigurerAdapter {
 
    private AuthenticationProvider authenticationProvider;
 
    @Autowired
    @Qualifier("daoAuthenticationProvider")
    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }
 
    @Bean
    public PasswordEncoder passwordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder){
       return bCryptPasswordEncoder;
    }
 
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder,
                                                               UserDetailsService userDetailsService){
 
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
 
    @Autowired
    public void configureAuthManager(AuthenticationManagerBuilder authenticationManagerBuilder){
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
           httpSecurity
                .authorizeRequests().antMatchers("/", "/api/auth/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().permitAll();
 
        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
    }
 
	/**
	 * Creating bean for accessing the AuthenticationManager object which is configured in
	 * the <code>configure(AuthenticationManagerBuilder builder)</code> method
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
 
}
