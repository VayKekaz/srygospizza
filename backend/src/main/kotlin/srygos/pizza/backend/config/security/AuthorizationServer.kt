package srygos.pizza.backend.config.security

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException.Unauthorized
import org.springframework.web.client.RestTemplate
import srygos.pizza.backend.model.user.Role
import java.util.*

@Component
class AuthorizationServer(
    restTemplateBuilder: RestTemplateBuilder,
    environment: Environment,
) {

    private val restTemplate: RestTemplate = restTemplateBuilder.build()

    /**
     * Accepts raw header value from request. Fetches user email from yandex.passport. If found
     * authenticates user. If not - registers as new one.
     *
     * @param headerValue raw header value like "Bearer {token}"
     * @return succeed authentication or anonymous authentication.
     */
    fun getAuthenticationForHeader(headerValue: String?): Authentication = when {
        (headerValue == null) -> createAnonymousAuthentication()
        (headerValue == AUTHORIZATION_HEADER_PREFIX + adminHeaderPassword) -> createAdminAuthentication()
        (headerValue == AUTHORIZATION_HEADER_PREFIX + editorHeaderPassword) -> createEditorAuthentication()
        else -> try {
            // fetch from database
            createBasicUserAuthentication()
        } catch (e: Unauthorized) { // invalid token
            createAnonymousAuthentication()
        }
    }

    private fun headerToJwt(headerRawValue: String): String {
        return headerRawValue.substring(AUTHORIZATION_HEADER_PREFIX.length)
    }

    /*
    private fun fetchUserFromYandex(token: String): YandexUserDto {
        val url = "https://login.yandex.ru/info"
        val request = RequestEntity.get(url)
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_PREFIX + token)
            .build()
        val response = restTemplate.exchange(request, YandexUserDto::class.java)
        return response.getBody()
    }
     */

    companion object {
        const val AUTHORIZATION_HEADER_KEY: String = "Authorization"
        const val AUTHORIZATION_HEADER_PREFIX: String = "Bearer "

        /**
         * These two passwords should only be used in Api test class.
         */
        var adminHeaderPassword: String? = "admin"
        var editorHeaderPassword: String? = "editor"
    }

    private fun createAdminAuthentication(): UsernamePasswordAuthenticationToken {
        Role.ADMIN.apply {
            return UsernamePasswordAuthenticationToken(
                this,
                "admin",
                this.authorities
            )
        }
    }

    private fun createEditorAuthentication(): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(
            Role.EDITOR,
            "editor",
            Role.EDITOR.authorities,
        )
    }

    private fun createBasicUserAuthentication(): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(
            Role.BASIC_USER,
            "user",
            Role.BASIC_USER.authorities,
        )
    }

    private fun createAnonymousAuthentication(): AnonymousAuthenticationToken {
        return AnonymousAuthenticationToken(
            "anonymousUser",
            "anonymousUser",
            listOf(SimpleGrantedAuthority("ROLE_ANONYMOUS")),
        )
    }

    init {
        val profiles = environment.activeProfiles
        if (Arrays.stream(profiles)
                .noneMatch { e -> (e == "adminHeaderPasswordRequired") }
        ) adminHeaderPassword = null
        if (Arrays.stream(profiles)
                .noneMatch { e -> (e == "editorHeaderPasswordRequired") }
        ) editorHeaderPassword = null
    }
}