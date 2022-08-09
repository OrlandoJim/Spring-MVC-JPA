package com.bolsadeideas.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
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
	
	
	@Bean
	public UserDetailsService userDetailsService() throws Exception {
		
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername("orlando").password(passwordEncoder().encode("123456")).roles("USER").build());
		manager.createUser(
				User.withUsername("admin").password(passwordEncoder().encode("123456")).roles("ADMIN", "USER").build());
 
		return manager;
	}

}
