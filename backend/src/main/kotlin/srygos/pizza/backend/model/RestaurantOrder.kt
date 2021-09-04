package srygos.pizza.backend.model

import java.time.OffsetDateTime
import javax.persistence.*

@Entity
@Table(name = "restaurant_order")
class RestaurantOrder(
    var address: String = "",
    var dateOrdered: OffsetDateTime = OffsetDateTime.now().withSecond(0).withNano(0),
    @ManyToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.PERSIST],
    ) var dishes: Set<OrderItem> = HashSet(),
) : BaseEntity() {

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
        var result = address.hashCode()
        result = 31 * result + dateOrdered.hashCode()
        result = 31 * result + dishes.hashCode()
        return result
    }

    fun hashCodeIncludeId(): Int =
        31 * hashCode() + this.id
}
