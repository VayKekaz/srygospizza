package srygos.pizza.backend.model.repository

import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Repository
import srygos.pizza.backend.createTypedQuery
import srygos.pizza.backend.find
import srygos.pizza.backend.model.Dish
import srygos.pizza.backend.model.Page
import javax.persistence.EntityManager
import javax.persistence.EntityNotFoundException
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class DishRepository(
    @PersistenceContext private val entityManager: EntityManager,
) : InitializingBean {

    companion object {
        var instance: DishRepository? = null
            private set
    }

    override fun afterPropertiesSet() {
        instance = this
    }

    @Transactional
    fun save(dish: Dish): Dish {
        entityManager.persist(dish)
        return dish
    }

    fun getAll(pageNumber: Int, pageSize: Int): Page<Dish> {
        val query = entityManager.createQuery(
            "SELECT d FROM Dish d ORDER BY d.name",
            Dish::class.java
        )
        query.firstResult = pageNumber * pageSize
        query.maxResults = pageSize
        return Page(
            content = query.resultList,
            pageNumber = pageNumber,
            pageSize = pageSize,
        )
    }

    fun getById(id: Int): Dish {
        return entityManager.find<Dish>(id)
            ?: throw EntityNotFoundException("Dish with id $id not found.")
    }

    fun count(): Long {
        val query = entityManager.createTypedQuery<Long>("SELECT count(d) FROM Dish d")
        return query.singleResult
    }
}
