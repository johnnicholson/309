package model

import java.util.*
import javax.persistence.*

/**
 * Created by jnicho on 2/24/17.
 */
@Entity
data class TimePref(@ManyToOne var prof: Person? = null,
                    var startTime: Date? = null,
                    var endTime: Date? = null,
                    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id : Int?=null)

