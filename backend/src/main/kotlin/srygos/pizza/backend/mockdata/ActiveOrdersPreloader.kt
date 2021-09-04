package srygos.pizza.backend.mockdata

import org.springframework.context.annotation.Profile
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import srygos.pizza.backend.model.repository.DishRepository
import srygos.pizza.backend.model.repository.OrderRepository

@Profile("mockDishes")
@Component
class ActiveOrdersPreloader(
    private val orderRepository: OrderRepository,
) {

    @EventListener(DishMenuPreloader.Companion.Done::class) fun afterDishesLoaded() {
        if (orderRepository.count() == 0L) MOCK_ORDERS.forEach {
            it.id = orderRepository.save(it).id
        }
    }
}
