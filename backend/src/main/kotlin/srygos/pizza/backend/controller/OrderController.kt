package srygos.pizza.backend.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import srygos.pizza.backend.model.RestaurantOrder
import srygos.pizza.backend.model.Page
import srygos.pizza.backend.model.repository.OrderRepository

@RestController
@RequestMapping("/orders")
class OrderController(private val repository: OrderRepository) {

    @GetMapping
    fun getAll(
        @RequestParam(required = false, defaultValue = "0") pageNumber: Int = 0,
        @RequestParam(required = false, defaultValue = "10") pageSize: Int = 10,
    ): ResponseEntity<Page<RestaurantOrder>> {
        return ResponseEntity(
            repository.getAll(pageNumber, pageSize),
            HttpStatus.OK
        )
    }

    @PostMapping
    fun createOrder(@Validated @RequestBody order: RestaurantOrder): ResponseEntity<RestaurantOrder> {
        val createdOrder = repository.save(order)
        return ResponseEntity(
            createdOrder,
            HttpStatus.CREATED
        )
    }
}