package com.project.moneyj.auth.config;

import com.project.moneyj.auth.dto.CustomOAuth2User;
import com.project.moneyj.auth.dto.TokenResponse;
import com.project.moneyj.auth.service.CustomOAuth2UserService;
import com.project.moneyj.auth.util.JwtFilter;
import com.project.moneyj.auth.util.JwtUtil;
import com.project.moneyj.auth.dto.TokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.moneyj.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtUtil jwtUtil;

    @Value("${spring.redirect.frontend-url}")
    private String frontendUrl;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 세션 대신 JWT 사용
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // JWT 인증 필터 추가
                .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login/**").permitAll()
                        .requestMatchers("/users/**").authenticated()
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler((request, response, authentication) -> {
                            CustomOAuth2User customUser = (CustomOAuth2User) authentication.getPrincipal();
                            String jwtToken = jwtUtil.generateToken(customUser.getName());

                            String redirectPath = customUser.isFirstLogin() ? "/agree" : "/home";
                            String redirectUrl = frontendUrl + redirectPath +
                                    "?token=" + jwtToken +
                                    "&isFirstLogin=" + customUser.isFirstLogin();

                            response.sendRedirect(redirectUrl);
                        })

                        .failureHandler((request, response, exception) -> {
                            // 로그인 실패 핸들러는 기존과 동일
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            try (PrintWriter writer = response.getWriter()) {
                                writer.write("{\"status\":\"fail\",\"message\":\"로그인 실패\"}");
                                writer.flush();
                            } catch (IOException e) {
                                // TODO: 예외 처리
                            }
                        })
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(request -> "/logout".equals(request.getRequestURI())
                                && HttpMethod.POST.matches(request.getMethod()))
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(HttpStatus.OK.value());
                            try (PrintWriter writer = response.getWriter()) {
                                writer.write("{\"status\":\"success\",\"message\":\"로그아웃 완료\"}");
                                writer.flush();
                            } catch (IOException e) {
                                // TODO: 예외 처리
                            }
                        })
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            try (PrintWriter writer = response.getWriter()) {
                                writer.write("{\"status\":\"fail\",\"message\":\"로그인 필요\"}");
                                writer.flush();
                            } catch (IOException e) {
                                // TODO: 예외 처리
                            }
                        })
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(frontendUrl));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
