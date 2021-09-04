package srygos.pizza.backend.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import srygos.pizza.backend.model.Dish
import srygos.pizza.backend.model.Page
import srygos.pizza.backend.model.repository.DishRepository

@RestController
@RequestMapping("/dishes")
class DishController(
    val repository: DishRepository
) {

    @GetMapping
    fun getAll(
        @RequestParam(required = false, defaultValue = "0") pageNumber: Int = 0,
        @RequestParam(required = false, defaultValue = "10") pageSize: Int = 10,
    ): ResponseEntity<Page<Dish>> {
        return ResponseEntity(
            repository.getAll(pageNumber, pageSize),
            HttpStatus.OK
        )
    }

    @PostMapping
    fun create(@Validated @RequestBody dish: Dish): ResponseEntity<Dish> {
        val createdDish = repository.save(dish)
        return ResponseEntity(
            createdDish,
            HttpStatus.CREATED
        )
    }
}