package srygos.pizza.backend

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import srygos.pizza.backend.mockdata.MOCK_ORDERS
import srygos.pizza.backend.model.Page
import srygos.pizza.backend.model.RestaurantOrder
import srygos.pizza.backend.testapi.Api
import srygos.pizza.backend.testapi.andReturnOfType

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("mockDishes")
class RestaurantOrderOperations(@Autowired mvc: MockMvc) {

    private val api = Api(mvc)

    @Test fun `should not contain any dishes`() {
        api.orders.get()
            .andDo { print() }
            .andExpect {
                status { isOk() }
                pageContentIsEmpty()
            }
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test fun `create one dish, should return that exact dish`() {
        api.orders.get().andExpect { pageContentIsEmpty() }
        val order = MOCK_ORDERS[0]
        api.orders.post(order).andExpect { status { isCreated() } }
        val responsePage: Page<RestaurantOrder> = api.orders.get().andExpect {
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
        }.andReturnOfType<Page<RestaurantOrder>>()
        val createdOrder: RestaurantOrder = responsePage.content[0]
        assert(createdOrder == order)
    }

}