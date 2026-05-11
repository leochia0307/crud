package com.leoh.crud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/login", "/css/**", "/js/**").permitAll() // 允許公開存取登入與註冊頁面、靜態資源
                        .requestMatchers("/customer/*/delete").hasRole("ADMIN") // 僅限管理員刪除
                        .anyRequest().authenticated() // 其他所有請求都需要登入
                )
                .formLogin(form -> form
                        .loginPage("/login") // 自訂登入頁面的 URL
                        .defaultSuccessUrl("/", true) // 登入成功後跳轉至首頁
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout") // 登出後跳轉回登入頁，並附帶 logout 參數
                        .invalidateHttpSession(true) // 清除 Session
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}