package srygos.pizza.backend.testapi

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import srygos.pizza.backend.config.jackson.Jackson
import srygos.pizza.backend.model.Page

abstract class CrudApi<T>(protected val mvc: MockMvc) {

    abstract val url: String

    open fun get(pageNumber: Int = 0, pageSize: Int = 10): ResultActionsDsl {
        val result =
            if (pageNumber == 0 && pageSize == 10)
                mvc.get(url)
            else
                mvc.get("$url?pageNumber=$pageNumber&pageSize=$pageSize")
        return result.andDo { print() }
    }

    open fun post(dish: T): ResultActionsDsl =
        mvc.post(url) {
            contentType = MediaType.APPLICATION_JSON
            content = Jackson.toJson(dish)
            accept = MediaType.APPLICATION_JSON
        }.andDo { print() }
}

inline fun <reified T> ResultActionsDsl.andReturnOfType(): T =
    Jackson.fromJson<T>(this.andReturn().response.contentAsString)
