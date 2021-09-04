package srygos.pizza.backend.model.user

import org.springframework.security.core.GrantedAuthority

enum class Authority : GrantedAuthority {
    ADMIN,
    EDIT_USERS,
    WATCH_USERS,
    EDIT_LECTURES;

    override fun getAuthority(): String {
        return this.toString()
    }
}