package srygos.pizza.backend.model.repository

import org.springframework.stereotype.Repository
import srygos.pizza.backend.createTypedQuery
import srygos.pizza.backend.model.RestaurantOrder
import srygos.pizza.backend.model.Page
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class OrderRepository(@PersistenceContext private val entityManager: EntityManager) {

    @Transactional
    fun save(order: RestaurantOrder): RestaurantOrder {
        entityManager.persist(order)
        return order
    }

    fun getAll(pageNumber: Int, pageSize: Int): Page<RestaurantOrder> {
        val resultList = entityManager.createTypedQuery<RestaurantOrder>(
            "SELECT d FROM RestaurantOrder d ORDER BY d.dateOrdered ASC"
        ).apply {
            firstResult = pageNumber * pageSize
            maxResults = pageSize
        }.resultList
        return Page(
            content = resultList,
            pageNumber = pageNumber,
            pageSize = pageSize,
        )
    }

    fun count(): Long =
        entityManager.createTypedQuery<Long>(
            "SELECT count(o) FROM RestaurantOrder o"
        ).singleResult
}