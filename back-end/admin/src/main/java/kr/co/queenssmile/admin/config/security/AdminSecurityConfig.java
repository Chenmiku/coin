package kr.co.queenssmile.admin.config.security;

import kr.co.queenssmile.core.config.database.PwdEncConfig;
import kr.co.queenssmile.core.config.security.LoginUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdminSecurityConfig {

  @Autowired
  private LoginUserDetailsService loginUserDetailsService;

  @Autowired
  private PwdEncConfig pwdEncConfig;

  @Autowired
  public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {

    auth.userDetailsService(loginUserDetailsService).passwordEncoder(pwdEncConfig.getPasswordEncoder());
  }

  @Configuration
  @Order(2)
  public static class AdminWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
    protected void configure(HttpSecurity http) throws Exception {
      log.debug("ADMIN WEB SECURITY");
      http
          .antMatcher("/admin/**")
          .authorizeRequests()
          .anyRequest()
          .hasAnyRole("SUPER", "ADMIN")

          .and()
          .formLogin()
          .loginPage("/admin/login")
          .defaultSuccessUrl("/admin")
          .usernameParameter("email")
          .permitAll()

          .and()
          .logout()
          .logoutUrl("/admin/logout")
          .logoutSuccessUrl("/admin/login?logout")
          .permitAll();
    }
  }

  @Configuration
  public static class UserWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      log.debug("USER WEB SECURITY");
      http
          .authorizeRequests().anyRequest()
          .permitAll()
          .and()
          .csrf();
    }
  }
}

