package srygos.pizza.backend.config.jackson.offsetdatetime

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.text.ParseException
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 * Deserializes strings of different formats into OffsetDateTime.
 * If main formatter throws exception, fallback formatters are being used.
 */
internal class OffsetDateTimeDeserializer(
    private val formatter: DateTimeFormatter,
    private vararg val fallbackFormatters: (String) -> OffsetDateTime
) : JsonDeserializer<OffsetDateTime>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): OffsetDateTime {
        return try {
            OffsetDateTime.parse(p.text, formatter).withNano(0).withSecond(0)
        } catch (e: DateTimeParseException) {
            for (formatter in fallbackFormatters) {
                try {
                    return formatter(p.text)
                } catch (formatterNotApplicable: ParseException) {
                    // try another one
                }
            }
            throw e
        }
    }
}