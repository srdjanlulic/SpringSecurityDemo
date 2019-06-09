package com.ftn.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ftn.backend.security.JwtAuthenticationEntryPoint;
import com.ftn.backend.security.JwtAuthenticationFilter;

@Configuration
public class SpringSecConfig extends WebSecurityConfigurerAdapter {
 
    private AuthenticationProvider authenticationProvider;
 
    @Autowired
    @Qualifier("daoAuthenticationProvider")
    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }
 
	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	@Autowired
	private UserDetailsService userDetailsService;
    
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
    /*Rukovanje sa medjusobno zavisnim bean-ovima. Ovim osiguravamo da je daoAuthenticationProvider inicijalizovan pre authManager-a*/
    @DependsOn("daoAuthenticationProvider") 
    public void configureAuthManager(AuthenticationManagerBuilder authenticationManagerBuilder){
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
    }
    
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {       
        httpSecurity.cors();
        httpSecurity.csrf().disable()
			.exceptionHandling()
				.authenticationEntryPoint(unauthorizedHandler)
				.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        	.and()
            .authorizeRequests().antMatchers("/", "/api/auth/login", "/api/users/register", "/api/auth/logout")
        		.permitAll()
        		.antMatchers(HttpMethod.OPTIONS)
        		.permitAll()
        		.anyRequest()
        		.authenticated();
		
        
        /*Dodavanje filter pre svakog zasticenog zahteva (koji se ne nalazi u ant matchers)*/
        httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
    
    /**
	 * Creating a <code>JdbcUserDetailsManager</code> object via the 
	 * <code>AuthenticationManagerBuilder</code>. CardionsUserDetailsService and 
	 * BCryptPasswordEncoder set for the manager.
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider);
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
	
	@Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
 
}
