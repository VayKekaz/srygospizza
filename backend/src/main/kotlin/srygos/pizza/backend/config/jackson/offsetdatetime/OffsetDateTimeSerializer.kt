package srygos.pizza.backend.config.jackson.offsetdatetime

import java.time.format.DateTimeFormatter
import java.time.OffsetDateTime
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider

/**
 * Serializes OffsetDateTime into string if format like `2019-08-06T19:33:00Z`
 */
internal class OffsetDateTimeSerializer(private val formatter: DateTimeFormatter) : JsonSerializer<OffsetDateTime>() {
    override fun serialize(
        value: OffsetDateTime,
        gen: JsonGenerator,
        serializers: SerializerProvider
    ) {
        gen.writeString(formatter.format(value.withNano(0).withSecond(0)))
    }
}