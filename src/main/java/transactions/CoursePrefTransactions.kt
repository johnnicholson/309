package transactions

import hibernate.HibernateUtil
import model.CoursePref
import org.springframework.beans.BeanUtils
import org.springframework.http.HttpStatus

class CoursePrefTransactions {
    class GetAllPref : Transaction<List<CoursePref>>() {
        override fun action(): List<CoursePref>? {
            val coursePrefDAO = HibernateUtil.getDAOFact().coursePrefDAO
            if (isAdmin())
                return coursePrefDAO.findAll()
            return null
        }
    }

    class GetPref(val prefId : Int) : Transaction<CoursePref>()  {
        override fun action(): CoursePref? {
            val coursePrefDAO = HibernateUtil.getDAOFact().coursePrefDAO
            val crsPref = coursePrefDAO.findById(prefId)
            if (isAdminOrUser(prefId))
                return crsPref
            responseCode = HttpStatus.UNAUTHORIZED
            return null
        }
    }

    class PostPref(val crsPref: CoursePref) : Transaction<Int?>() {
        override fun action(): Int? {
            val crsPrefDAO = HibernateUtil.getDAOFact().coursePrefDAO
            if (isAdminOrUser(crsPref.prof!!.id) && crsPref.id == null)
                return crsPrefDAO.makePersistent(crsPref).id
            responseCode = HttpStatus.UNAUTHORIZED
            return null
        }
    }

    class PutPref(val prefId: Int, val crsPref: CoursePref) : Transaction<Int?>() {
        override fun action(): Int? {
            val crsPrefDAO = HibernateUtil.getDAOFact().coursePrefDAO
            val dbPref = crsPrefDAO.findById(prefId)
            if (dbPref == null) {
                responseCode = if(isAdmin()) HttpStatus.NOT_FOUND else HttpStatus.UNAUTHORIZED
            }
            else if ( isAdmin() || (dbPref.prof != null && isAdminOrUser(dbPref.prof!!.id)))
               copyNonNulls(crsPref, dbPref, "id");
            else
                responseCode = HttpStatus.UNAUTHORIZED
            return null
        }

    }

    class DeletePref(val prefId: Int) : Transaction<Int?>() {
        override fun action(): Int? {
            val crsPrefDAO = HibernateUtil.getDAOFact().coursePrefDAO
            val dbPref = crsPrefDAO.findById(prefId)
            if (dbPref == null) {
                responseCode = if(isAdmin()) HttpStatus.NOT_FOUND else HttpStatus.UNAUTHORIZED
            }
            else if ( isAdmin() || (dbPref.prof != null && isAdminOrUser(dbPref.prof!!.id)))
               crsPrefDAO.makeTransient(dbPref)
            else
                responseCode = HttpStatus.UNAUTHORIZED
            return null
        }

    }
}


