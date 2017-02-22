package model

import hibernate.HibernateUtil
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import transactions.Transaction
import javax.persistence.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

data class CohortDataFilter (var depts : List<String>?,
                             var levels: List<Int>?,
                             var majors: List<String>?,
                             var grades: List<String>?)


/**
 * Created by jnicho on 2/21/17.
 */
@Entity
data class CohortData (val term : Int,
                       @ManyToOne val course: Course,
                       val courselvl: Int,
                       @Id @GeneratedValue(strategy = GenerationType.AUTO)val id: Int? = null)

data class CohortQuery(var courses: List<String>)

@RestController
@RequestMapping(value = "/api/filter")
class CohortDataController {
    @RequestMapping(value = "/cohort", method = arrayOf(RequestMethod.POST))
    fun TestGet(@RequestBody filter: CohortDataFilter, req : HttpServletRequest, res : HttpServletResponse) = CohortDataGet(filter).run(req, res);
}

class CohortDataGet(val filter: CohortDataFilter) : Transaction<CohortData>() {
    override fun action(): CohortData? {
        return HibernateUtil.getDAOFact().cohortDataDAO.makePersistent(CohortData(1,Course(),1))
    }
}

