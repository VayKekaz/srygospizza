package srygos.pizza.backend.config.jackson.offsetdatetime

import java.time.format.DateTimeFormatter
import java.time.OffsetDateTime
import com.fasterxml.jackson.databind.module.SimpleModule
import srygos.pizza.backend.addDeserializerForType
import srygos.pizza.backend.addSerializerForType
import java.text.SimpleDateFormat
import java.time.ZoneOffset

/**
 * Module that provides correct DateTime format.
 */
object SrygosPizzaJacksonOffsetDateTimeModule : SimpleModule() {

    private val dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    private val fallbackFormatters: Array<(String) -> OffsetDateTime> = arrayOf(
        { dtString -> // "28.12.21 24:59:58"
            val date = SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(dtString)
            date.toInstant().atOffset(ZoneOffset.ofHours(+3))
        },
        { dtString -> // "28.12.21"
            val date = SimpleDateFormat("dd.MM.yyyy").parse(dtString)
            date.toInstant().atOffset(ZoneOffset.ofHours(+3))
        },
    )

    init {
        addSerializerForType<OffsetDateTime>(
            OffsetDateTimeSerializer(dateTimeFormatter)
        )
        addDeserializerForType<OffsetDateTime>(
            OffsetDateTimeDeserializer(dateTimeFormatter, *fallbackFormatters)
        )
    }
}