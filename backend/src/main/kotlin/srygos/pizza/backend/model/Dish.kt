package srygos.pizza.backend.model

import javax.persistence.Entity

@Entity
class Dish(
    var name: String = "",
    var image: String = "",
    var description: String = "",
    var price: Float = 0f,
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
        var result = 1
        result = 31 * result + name.hashCode()
        result = 31 * result + image.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + price.hashCode()
        return result
    }

    fun hashCodeIncludeId(): Int =
        31 * this.hashCode() + this.id.hashCode()
}
