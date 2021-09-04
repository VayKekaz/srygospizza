package srygos.pizza.backend

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl

fun MockMvcResultMatchersDsl.pageContentIsEmpty() {
    content { contentType(MediaType.APPLICATION_JSON) }
    jsonPath("$.elementNumber") {
        isNumber()
        value(0)
    }
    jsonPath("$.content") {
        isArray()
        isEmpty()
    }
}