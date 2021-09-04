package srygos.pizza.backend

import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.module.SimpleModule
import srygos.pizza.backend.model.util.HttpErrorMessage
import javax.persistence.EntityManager
import javax.servlet.http.HttpServletResponse

fun HttpServletResponse.sendError(error: HttpErrorMessage) =
    this.sendError(error.status, error.message)

fun all(vararg condition: Boolean) = condition.all { it }

// jpa
inline fun <reified T> EntityManager.find(primaryKey: Any): T? =
    this.find(T::class.java, primaryKey)

// jackson
inline fun <reified T> SimpleModule.addDeserializerForType(deserializer: JsonDeserializer<T>) {
    this.addDeserializer(T::class.java, deserializer)
}

inline fun <reified T> SimpleModule.addSerializerForType(serializer: JsonSerializer<T>) {
    this.addSerializer(T::class.java, serializer)
}
