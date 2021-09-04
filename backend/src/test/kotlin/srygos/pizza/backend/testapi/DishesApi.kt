package srygos.pizza.backend.testapi

import org.springframework.test.web.servlet.MockMvc
import srygos.pizza.backend.model.Dish

class DishesApi(mvc: MockMvc) : CrudApi<Dish>(mvc) {

    override val url: String = "/dishes"

}