package srygos.pizza.backend.model.util

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.lang.Exception
import javax.servlet.http.HttpServletResponse

class HttpErrorMessage(
    val status: Int,
    val message: String = "",
) {
    constructor(
        status: HttpStatus,
        message: String
    ) : this(
        status.value(),
        message
    )

    constructor(
        status: Int,
        exception: Exception
    ) : this(
        status,
        exception.message ?: ""
    )

    constructor(
        status: HttpStatus,
        exception: Exception
    ) : this(
        status,
        exception.message ?: ""
    )

    fun asResponseEntity(): ResponseEntity<HttpErrorMessage> =
        ResponseEntity(this, HttpStatus.valueOf(this.status))
}
