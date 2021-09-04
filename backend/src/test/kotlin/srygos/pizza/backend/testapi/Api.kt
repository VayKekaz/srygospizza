package srygos.pizza.backend.testapi

import org.springframework.test.web.servlet.MockMvc

class Api(private val mvc: MockMvc) {

    val orders: OrdersApi by lazy { OrdersApi(mvc) }
    val dishes: DishesApi by lazy { DishesApi(mvc) }
}