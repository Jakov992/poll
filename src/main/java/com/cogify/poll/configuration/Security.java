package com.cogify.poll.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.Assert;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toSet;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Profile("!test")
public class Security {

    private final PoolAccessDeniedHandler accessDeniedHandler;

    @Value("keycloak.resource")
    private String clientId;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/swagger-ui/**").permitAll()
                            .requestMatchers("/v3/api-docs/**").permitAll();

                    auth.anyRequest().authenticated();
                })
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandling ->exceptionHandling
                        .accessDeniedHandler(accessDeniedHandler))
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter())));


        return http.build();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(List.of("http://localhost:3000"));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        cfg.setAllowedHeaders(List.of("Authorization","Content-Type"));
        cfg.setAllowCredentials(true);

        System.out.println("Tu smo!!!!!");

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }

    class KeycloakJwtAuthenticationConverter extends JwtAuthenticationConverter {

        public KeycloakJwtAuthenticationConverter() {
            KeycloakJwtAuthoritiesConverter grantedAuthoritiesConverter =
                    new KeycloakJwtAuthoritiesConverter();
            grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

            setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
            setPrincipalClaimName(clientId);
        }
    }

    static class KeycloakJwtAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

        private String authorityPrefix = "";

        public KeycloakJwtAuthoritiesConverter() {}

        public KeycloakJwtAuthoritiesConverter setAuthorityPrefix(String authorityPrefix) {
            Assert.notNull(authorityPrefix, "Authority Prefix should not be null");
            this.authorityPrefix = authorityPrefix;
            return this;
        }

        @Override
        public Collection<GrantedAuthority> convert(Jwt source) {
            Map<String, Object> realmAccess = source.getClaim("realm_access");
            if (isNull(realmAccess)) {
                return Collections.emptySet();
            }

            Object roles = realmAccess.get("roles");
            if (isNull(roles) || !Collection.class.isAssignableFrom(roles.getClass())) {
                return Collections.emptySet();
            }

            Collection<?> rolesCollection = (Collection<?>) roles;

            return rolesCollection.stream().filter(String.class::isInstance)
                    .map(x -> new SimpleGrantedAuthority(authorityPrefix + x)).collect(toSet());
        }
    }
}
