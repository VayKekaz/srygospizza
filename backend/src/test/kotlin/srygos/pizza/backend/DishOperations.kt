package srygos.pizza.backend

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl
import org.springframework.test.web.servlet.ResultActionsDsl
import srygos.pizza.backend.config.jackson.Jackson
import srygos.pizza.backend.mockdata.DishMenuPreloader
import srygos.pizza.backend.mockdata.MOCK_DISHES
import srygos.pizza.backend.model.Dish
import srygos.pizza.backend.model.Page
import srygos.pizza.backend.testapi.Api
import srygos.pizza.backend.testapi.andReturnOfType

@SpringBootTest
@AutoConfigureMockMvc
class DishOperations(@Autowired mvc: MockMvc) {

    private val api = Api(mvc)

    @Test fun `should not contain any dishes`() {
        api.dishes.get()
            .andDo { print() }
            .andExpect {
                status { isOk() }
                pageContentIsEmpty()
            }
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test fun `create one dish, should return that exact dish`() {
        api.dishes.get().andExpect { pageContentIsEmpty() }
        val dish = MOCK_DISHES[0]
        api.dishes.post(dish).andExpect { status { isCreated() } }
        val responsePage: Page<Dish> = api.dishes.get().andExpect {
            jsonPath("$.elementNumber") {
                isNumber()
                value(1)
            }
            jsonPath("$.content") {
                isArray()
                isNotEmpty()
            }
            jsonPath("$.content[0]") {
                isMap()
            }
        }.andReturnOfType<Page<Dish>>()
        val createdDish: Dish = responsePage.content[0]
        assert(createdDish == dish)
    }

}
