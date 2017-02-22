package model

import dao.GenericHibernateDAO
import hibernate.HibernateUtil
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import transactions.Transaction
import javax.persistence.Entity
import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


data class CohortDataFilter (val depts : List<String>,
                             val levels: List<Int>,
                             val majors: List<String>,
                             val grades: List<String>)

/**
 * Created by jnicho on 2/21/17.
 */
@Entity
data class CohortData (var dept: String,
                       var level: Int = 100,
                       var major: String = "CSC",
                       var grade: String = "Freshman",
                       var numTaken: Int = 0,
                       @Id val id: Int? = null)

data class CohortQuery(var courses: List<String>)

@RestController
@RequestMapping(value = "/api/filter")
class CohortDataController {
    @RequestMapping(value = "/cohort", method = arrayOf(RequestMethod.POST))
    fun TestGet(@RequestBody filter: CohortDataFilter, req : HttpServletRequest, res : HttpServletResponse) = CohortDataGet(filter).run(req, res);
}

class CohortDataGet(val filter: CohortDataFilter) : Transaction<CohortData>() {
    override fun action(): CohortData? {
        return HibernateUtil.getDAOFact().cohortDataDAO.findByFilters(filter);
    }
}

