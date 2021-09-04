package srygos.pizza.backend.mockdata

import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Profile
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import srygos.pizza.backend.model.repository.DishRepository

@Profile("mockDishes")
@Component
class DishMenuPreloader(
    private val dishRepository: DishRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {

    @EventListener(ContextRefreshedEvent::class)
    fun onStartup() {
        if (dishRepository.count() == 0L) MOCK_DISHES.forEach {
            it.id = dishRepository.save(it).id
        }
        applicationEventPublisher.publishEvent(Done())
    }

    companion object {
        class Done : ApplicationEvent(DishMenuPreloader)
    }
}