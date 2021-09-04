package srygos.pizza.backend

import org.intellij.lang.annotations.Language
import javax.persistence.EntityManager
import javax.persistence.TypedQuery

inline fun <reified Result> EntityManager.createTypedQuery(
    @Language("JPAQL")
    query: String
): TypedQuery<Result> {
    return this.createQuery(query, Result::class.java)
}