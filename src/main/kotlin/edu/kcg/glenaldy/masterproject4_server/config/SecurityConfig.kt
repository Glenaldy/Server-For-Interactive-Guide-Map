package edu.kcg.glenaldy.masterproject4_server.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ApiKeyFilter(private val environment: Environment) : OncePerRequestFilter() {

    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
    ) {
        val apiKeyParam = request.getParameter("api_key")
        val apiKeyEnv = environment.getProperty("API_KEY")
        if (isValidApiKey(apiKeyParam, apiKeyEnv)) {
            val auth = ApiKeyAuthentication(apiKeyParam)
            SecurityContextHolder.getContext().authentication = auth
        }
        filterChain.doFilter(request, response)
    }

    private fun isValidApiKey(apiKeyHeader: String?, apiKeyEnv: String?): Boolean {
        return apiKeyHeader != null && apiKeyHeader == apiKeyEnv
    }
}

class ApiKeyAuthentication(private val apiKey: String) : AbstractAuthenticationToken(null), Authentication {
    override fun getCredentials(): Any = apiKey
    override fun getPrincipal(): Any = apiKey
    init {
        isAuthenticated = true
        details = apiKey
    }
}

@Configuration
@EnableWebSecurity
class SecurityConfig(private val environment: Environment) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
                .cors().and()
                .csrf().disable()
                .addFilterBefore(ApiKeyFilter(environment), BasicAuthenticationFilter::class.java)
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return Argon2PasswordEncoder()
    }
}
