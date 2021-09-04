package srygos.pizza.backend.model

import srygos.pizza.backend.model.repository.DishRepository
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EntityNotFoundException
import javax.persistence.OneToOne

@Entity
class OrderItem(
    var quantity: Int,
    @OneToOne var dish: Dish,
) : BaseEntity() {

    init {
        val dishRepository = DishRepository.instance
        if (dishRepository != null) try {
            this.dish = dishRepository.getById(dish.id)
        } catch (e: EntityNotFoundException) {
            // ignore, id not found
        }
    }


    override fun equals(other: Any?): Boolean {
        return other
            ?.let { it.hashCode() == this.hashCode() }
            ?: false
    }

    fun equalsIncludeId(other: Any?): Boolean {
        if (other == null || other !is Dish)
            return false
        return other.hashCodeIncludeId() == this.hashCodeIncludeId()
    }

    override fun hashCode(): Int {
        var result = quantity
        result = 31 * result + dish.hashCode()
        return result
    }

    fun hashCodeIncludeId(): Int =
        31 * hashCode() + this.id
}
