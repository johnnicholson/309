package model

import org.hibernate.validator.constraints.Range
import javax.persistence.*

/**
 * Created by jnicho on 2/23/2017.
 */

@Entity
data class CoursePref(@ManyToOne var prof: Person? = null,
                      @ManyToOne var course: Course? = null,
                      @Range(min=1, max=3) var level: Int? = 1,
                      @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Int? = null)


