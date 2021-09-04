package srygos.pizza.backend.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.firewall.RequestRejectedHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import srygos.pizza.backend.model.util.HttpErrorMessage
import srygos.pizza.backend.sendError


@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        // H2 console throws ERR_BLOCKED_BY_RESPONSE,
        http.headers().frameOptions().sameOrigin()
        // Csrf is only dangerous when the browser automatically provides authentication. Since we use Bearer tokens,
        // it's not necessary for us. see: https://security.stackexchange.com/a/170414
        http.csrf().disable()
        // Cors policy, it uses @Bean(name = "corsConfigurationSource") as config source.
        http.cors(Customizer.withDefaults())
        // Disable any endpoint security, controller methods are secured with @PreAuthorize
        http.authorizeRequests().anyRequest().permitAll()
        // disables default /login page
        http.formLogin().disable()
        // No redirect.
        http.exceptionHandling().authenticationEntryPoint(invalidAccessRequestHandler())
        /*
        // JWT Authentication & Authorization
        http.addFilterBefore(
            jwtAuthorizationFilter,
            UsernamePasswordAuthenticationFilter::class.java
        )
         */
    }

    @Bean
    fun requestRejectedHandler() = RequestRejectedHandler { request, response, exception ->
        response.sendError(
            HttpErrorMessage(
                status = HttpStatus.BAD_REQUEST,
                message = exception.message ?: "Potentially malicious request url."
            )
        )
    }

    @Bean
    fun invalidAccessRequestHandler() = AuthenticationEntryPoint { request, response, authException ->
        val auth = SecurityContextHolder.getContext().authentication
        if (auth == null || auth is AnonymousAuthenticationToken) // DiletantUser is not authenticated
            response.sendError(401, authException.message)
        else // DiletantUser does not have proper roles
            response.sendError(403, authException.message)
    }

    // Name here is critical
    @Bean(name = ["corsConfigurationSource"])
    fun corsConfig(): CorsConfigurationSource = UrlBasedCorsConfigurationSource().apply {
        registerCorsConfiguration(
            "/**",
            CorsConfiguration().apply {
                addAllowedOriginPattern("*")
                allowCredentials = true
                addAllowedHeader(CorsConfiguration.ALL)
                addAllowedMethod("GET")
                addAllowedMethod("POST")
                addAllowedMethod("PUT")
                addAllowedMethod("DELETE")
                addAllowedMethod("OPTIONS")
            }
        )
    }
}