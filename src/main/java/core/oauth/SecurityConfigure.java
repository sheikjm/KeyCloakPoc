package core.oauth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Configuration
public class SecurityConfigure extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        http.cors().configurationSource(new CorsConfigurationSource() {
                    @Override public CorsConfiguration getCorsConfiguration(HttpServletRequest
                                                                                    request) { CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L); return config;
                    }
                }).and()
                .authorizeRequests()
                .antMatchers("/myAccount").hasAnyRole("USER")
                .antMatchers("/myBalance").hasAnyRole("ADMIN")
                .antMatchers("/login","/dashboard").authenticated()
                .antMatchers("/myCards").hasAnyRole("USER", "ADMIN")
                .antMatchers("/notices").permitAll()
                .antMatchers("/contact").permitAll()
                .and().csrf().disable()
                .oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter);

        http.headers().frameOptions().sameOrigin();

    }
}
