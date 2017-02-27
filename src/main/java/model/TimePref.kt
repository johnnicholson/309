package model

import java.util.*
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * Created by jnicho on 2/24/17.
 */

data class TimePref(var prof: Person? = null,
                    var startTime: Date? = null,
                    var endTime: Date? = null,
                    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id : Int?=null)

