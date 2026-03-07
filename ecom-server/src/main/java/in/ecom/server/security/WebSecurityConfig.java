package in.ecom.server.security;

import in.ecom.server.model.AppRole;
import in.ecom.server.model.Role;
import in.ecom.server.model.User;
import in.ecom.server.repository.RoleRepository;
import in.ecom.server.security.jwt.AuthEntryPointJwt;
import in.ecom.server.security.jwt.AuthTokenFilter;
import in.ecom.server.security.services.impl.UserDetailsServiceImpl;
import in.ecom.server.security.services.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Set;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);

        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/api-docs/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
//                                .requestMatchers("/public/**").permitAll()
//                                .requestMatchers("/admin/**").permitAll()
                                .requestMatchers("/test/**").permitAll()
                                .requestMatchers("/images/**").permitAll()
                                .anyRequest().authenticated());

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.headers(
                headers -> headers.frameOptions(
                        HeadersConfigurer.FrameOptionsConfig::sameOrigin)
        );
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers(
                "/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration-security",
                "/swagger-ui.html",
                "/webjars/**")
        );
    }

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseGet(() -> {
                        Role newUserRole = new Role(AppRole.ROLE_USER);
                        return roleRepository.save(newUserRole);
                    });

            Role sellerRole = roleRepository.findByRoleName(AppRole.ROLE_SELLER)
                    .orElseGet(() -> {
                        Role newSellerRole = new Role(AppRole.ROLE_SELLER);
                        return roleRepository.save(newSellerRole);
                    });

            Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                    .orElseGet(() -> {
                        Role newAdminRole = new Role(AppRole.ROLE_ADMIN);
                        return roleRepository.save(newAdminRole);
                    });

            Set<Role> userRoles = Set.of(userRole);
            Set<Role> sellerRoles = Set.of(sellerRole);
            Set<Role> adminRoles = Set.of(userRole, sellerRole, adminRole);

            if (!userRepository.existByUserName("user1")) {
                User user1 = new User("user1", "user1@example.com", passwordEncoder().encode("password1"));
                userRepository.save(user1);
            }

            if (!userRepository.existByUserName("seller1")) {
                User seller = new User("seller1", "seller1@example.com", passwordEncoder().encode("password2"));
                userRepository.save(seller);
            }

            if (!userRepository.existByUserName("admin")) {
                User admin = new User("admin", "admin@example.com", passwordEncoder().encode("adminPass"));
                userRepository.save(admin);
            }

            /* update roles for existing uses */
            userRepository.findByUserName("user1").ifPresent(
                    user -> {
                        user.setRoles(userRoles);
                        userRepository.save(user);
                    }
            );

            userRepository.findByUserName("seller1").ifPresent(
                    seller -> {
                        seller.setRoles(sellerRoles);
                        userRepository.save(seller);
                    }
            );

            userRepository.findByUserName("admin").ifPresent(
                    admin -> {
                        admin.setRoles(adminRoles);
                        userRepository.save(admin);
                    }
            );

        };
    }

}
