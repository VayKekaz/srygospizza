package srygos.pizza.backend.model

import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseEntity {

    @Id
    @GeneratedValue
    var id: Int = 0
}
