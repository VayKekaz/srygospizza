package srygos.pizza.backend.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class Page<T>(
    val content: List<T>,
    val pageNumber: Int,
    val pageSize: Int,
) : Iterable<T> {

    @get:JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val elementNumber: Int
        get() = content.size

    constructor() : this(
        content = ArrayList(0),
        pageNumber = 0,
        pageSize = 0,
    )

    constructor(pageNumber: Int, pageSize: Int) : this(
        content = ArrayList(0),
        pageNumber = pageNumber,
        pageSize = pageSize,
    )

    constructor(springDataPage: org.springframework.data.domain.Page<T>) : this(
        content = ArrayList(springDataPage.content),
        pageNumber = springDataPage.pageable.pageNumber,
        pageSize = springDataPage.pageable.pageSize,
    )

    override fun iterator(): Iterator<T> =
        this.content.listIterator()

    fun forEach(action: (T) -> Unit): Unit =
        this.content.forEach(action)

    override fun spliterator(): Spliterator<T> =
        this.content.spliterator()

    fun <N> map(mapper: (T) -> N): Page<N> {
        return Page(
            content = this.content.map(mapper),
            pageNumber = pageNumber,
            pageSize = pageSize
        )
    }

    override fun equals(other: Any?): Boolean {
        return other
            ?.let { it.hashCode() == this.hashCode() }
            ?: false
    }

    override fun hashCode(): Int {
        var result = 1
        result = 31 * result + content.hashCode()
        result = 31 * result + pageNumber
        result = 31 * result + pageSize
        return result
    }

}