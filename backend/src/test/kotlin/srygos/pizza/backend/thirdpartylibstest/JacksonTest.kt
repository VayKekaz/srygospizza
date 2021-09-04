package srygos.pizza.backend.thirdpartylibstest

import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import srygos.pizza.backend.config.jackson.Jackson
import srygos.pizza.backend.mockdata.DishMenuPreloader
import srygos.pizza.backend.mockdata.MOCK_DISHES
import srygos.pizza.backend.model.Dish
import srygos.pizza.backend.model.Page

class JacksonTest {
    private val logger: Logger = LoggerFactory.getLogger(JacksonTest::class.java)

    @Test fun `toJson() == toPrettyJson() assertion`() {
        val initialEntity = MOCK_DISHES[0]
        val uglyJson = Jackson.toJson(initialEntity)
        val prettyJson = Jackson.toPrettyJson(initialEntity)

        val uglyEntity = Jackson.fromJson<Dish>(uglyJson)
        val prettyEntity = Jackson.fromJson<Dish>(prettyJson)
        assert(uglyEntity == prettyEntity)
    }

    @Test fun `dish serialization and deserialization test`() {
        val dish = MOCK_DISHES[0]
        val dishJsonString = Jackson.toPrettyJson(dish)
        logger.info("Initial dish=$dishJsonString")
        val jacksonProcessedDish = Jackson.fromJson<Dish>(dishJsonString)
        logger.info("Jackson processed dish=${Jackson.toPrettyJson(jacksonProcessedDish)}")
        assert(jacksonProcessedDish == dish)
    }

    @Test fun `generic page serialization and deserialization test`() {
        val page = Page<Dish>(
            content = MOCK_DISHES.subList(0, 1),
            pageNumber = 0,
            pageSize = 3
        )
        val json = Jackson.toPrettyJson(page)
        logger.info("Initial entity=$json")
        val jacksonProcessedPage = Jackson.fromJson<Page<Dish>>(json)
        logger.info("Processed entity=${Jackson.toPrettyJson(jacksonProcessedPage)}")
        assert(page == jacksonProcessedPage)
    }
}