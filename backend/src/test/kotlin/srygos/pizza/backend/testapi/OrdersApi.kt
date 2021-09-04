package srygos.pizza.backend.testapi

import org.springframework.test.web.servlet.MockMvc
import srygos.pizza.backend.model.RestaurantOrder

class OrdersApi(mvc: MockMvc) : CrudApi<RestaurantOrder>(mvc) {

    override val url: String = "/orders"
}
