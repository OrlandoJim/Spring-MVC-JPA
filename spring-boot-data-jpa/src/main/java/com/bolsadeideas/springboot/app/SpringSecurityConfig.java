package com.bolsadeideas.springboot.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.bolsadeideas.springboot.app.auth.handler.LoginSuccessHandler;

@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
@Configuration
public class SpringSecurityConfig{
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**","/listar").permitAll()
		/*.antMatchers("/ver/**").hasAuthority("ROLE_USER")
		.antMatchers("/uploads/**").hasAuthority("ROLE_USER")
		.antMatchers("/form/**").hasAuthority("ROLE_ADMIN")
		.antMatchers("/eliminar/**").hasAuthority("ROLE_ADMIN")
		.antMatchers("/factura/**").hasAuthority("ROLE_ADMIN")*/
		.anyRequest().fullyAuthenticated()
		.and()
		.formLogin()
		.successHandler(successHandler).loginPage("/login")
		.permitAll()
		.and()
		.logout().permitAll()
		.and().exceptionHandling().accessDeniedPage("/error_403");
		
		return http.build();
	}
	
	
	//@Bean
	/*public UserDetailsService userDetailsService() throws Exception {
		
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();		
	
		manager.createUser(User.withUsername("orlando").password(passwordEncoder.encode("123456")).roles("USER").build());
		manager.createUser(
				User.withUsername("admin").password(passwordEncoder.encode("123456")).roles("ADMIN", "USER").build());
 
		return manager;
	}*/
	
	@Autowired//Spring security con JDBC 
	public void configAuthentication(AuthenticationManagerBuilder build) throws Exception {
		build.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(passwordEncoder)
		.usersByUsernameQuery("select username, password, enabled from users where username=?")
		.authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?");
	}


}
