package prog.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class Securityconfig {
@Autowired
private DataSource dataSource;

//        @Autowired
//        PersonUserDetailsService personUserDetailsService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/information")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/information/sighin")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/information/logout")).authenticated()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/information")
                        .permitAll()
                        .successForwardUrl("/information/sighin")
                        .usernameParameter("email")
                        .passwordParameter("password")
                );
        return http.build();
//                        .successHandler(((request, response, authentication) -> {
//                            if (authentication !=null && authentication.isAuthenticated()){
//                                CustomUserDetails person=(CustomUserDetails) authentication.getPrincipal();
//
//                            }
//                        }))




//                .formLogin()
//                .loginPage("/information/sighin")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//        return http.build();





        //        return http
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(withDefaults())
//                .build();

    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}






























//return http.userDetailsService(personUserDetailsService).build();

//                http.csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers(new AntPathRequestMatcher("/"),
//                        new AntPathRequestMatcher("/information"),
//                        new AntPathRequestMatcher("/information/create"))//дозволяє доступ до певних URL без автентифікації(всім доступ буде дозволено)
//                .permitAll()
//                .and()
//                .formLogin(form -> form
//                        .loginPage("/information")
//                        .defaultSuccessUrl("/information/sighin")
//                        .loginProcessingUrl("/information/sighin")
//                        .failureUrl("/information?error=true")
//                        .permitAll()
//                ).logout(
//                  logout -> logout
//                          .logoutRequestMatcher(new AntPathRequestMatcher("/information/logout")).permitAll()
//                );
//
//        return http.build();
