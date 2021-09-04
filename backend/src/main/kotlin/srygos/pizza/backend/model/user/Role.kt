package srygos.pizza.backend.model.user

import srygos.pizza.backend.model.user.Authority.*
import java.util.*
import java.util.stream.Collectors

enum class Role(vararg authorities: Authority) {
    ADMIN(
        Authority.ADMIN,
        EDIT_USERS,
        WATCH_USERS,
        EDIT_LECTURES
    ),
    EDITOR(
        EDIT_LECTURES
    ),
    BASIC_USER(

    ),
    ;

    val authorities: Set<Authority> =
        Arrays.stream(authorities).collect(Collectors.toUnmodifiableSet())
}