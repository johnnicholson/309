package model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.DayOfWeek
import java.util.*
import javax.persistence.*

/**
 * Created by jnicho on 2/24/17.
 */
@Entity
data class TimePref(@ManyToOne var prof: Person? = null,
                    var day : DayOfWeek? = null,
                    var level : Int? = 1,
                    @Temporal(TemporalType.TIMESTAMP) @JsonFormat(pattern = "HH:00") var startTime: Date? = null,
                    @Temporal(TemporalType.TIMESTAMP) @JsonFormat(pattern = "HH:00") var endTime: Date? = null,
                    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id : Int?=null)

