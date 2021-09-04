package srygos.pizza.backend.mockdata

import srygos.pizza.backend.model.OrderItem
import srygos.pizza.backend.model.RestaurantOrder

val MOCK_ORDERS: List<RestaurantOrder> = listOf(
    RestaurantOrder(
        address = "Puskin's st. Kolotushkin's house",
        dishes = setOf(
            OrderItem(quantity = 2, dish = MOCK_DISHES[0]),
            OrderItem(quantity = 1, dish = MOCK_DISHES[1]),
            OrderItem(quantity = 1, dish = MOCK_DISHES[4]),
            OrderItem(quantity = 1, dish = MOCK_DISHES[8]),
            OrderItem(quantity = 1, dish = MOCK_DISHES[10]),
            OrderItem(quantity = 1, dish = MOCK_DISHES[11]),
        )
    )
)
