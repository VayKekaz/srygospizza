package srygos.pizza.backend.config.jackson

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import srygos.pizza.backend.config.jackson.offsetdatetime.SrygosPizzaJacksonOffsetDateTimeModule

@Configuration
class Jackson {
    companion object {

        val mapper: ObjectMapper = jacksonObjectMapper()
            @Primary @Bean get

        init {
            mapper.registerModules(
                SrygosPizzaJacksonOffsetDateTimeModule,
            )
            mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false)
        }

        fun toJson(obj: Any?): String =
            mapper.writeValueAsString(obj)

        fun toPrettyJson(obj: Any?): String =
            mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj)

        inline fun <reified T> fromJson(json: String): T =
            mapper.readValue(json)
    }
}
