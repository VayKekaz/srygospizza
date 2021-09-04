package srygos.pizza.backend.config.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import srygos.pizza.backend.config.security.AuthorizationServer.Companion.AUTHORIZATION_HEADER_KEY
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthorizationFilter(
    private val authorizationServer: AuthorizationServer,
    // private val userService: UserService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val authHeader = request.getHeader(AUTHORIZATION_HEADER_KEY)
        val auth = authorizationServer.getAuthenticationForHeader(authHeader)
        SecurityContextHolder.getContext().authentication = auth
        filterChain.doFilter(request, response)
    }
}
