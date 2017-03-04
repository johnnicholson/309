package transactions

import hibernate.HibernateUtil
import model.TimePref
import org.springframework.beans.BeanUtils
import org.springframework.beans.BeanWrapperImpl
import org.springframework.http.HttpStatus

class TimePrefTransactions {
    class GetAllPref : Transaction<List<TimePref>?>() {
        override fun action(): List<TimePref>? {
            val timePrefDao = HibernateUtil.getDAOFact().timePrefDAO
            if (isAdmin()) {
                return timePrefDao.findAll();
            }
            responseCode = HttpStatus.UNAUTHORIZED;
            return null
        }

    }

    class GetPref(val prefId: Int) : Transaction<TimePref?>() {
        override fun action(): TimePref? {
            val timePrefDao = HibernateUtil.getDAOFact().timePrefDAO
            val timePref : TimePref? = timePrefDao.findById(prefId)
            if (isAdminOrUser(timePref!!.prof!!.id)) {
                return timePref
            }
            responseCode = HttpStatus.UNAUTHORIZED;
            return null
        }

    }

    class PostPref(val pref: TimePref) : Transaction<Int?>() {
        override fun action(): Int? {
            val timePrefDAO = HibernateUtil.getDAOFact().timePrefDAO
            if (pref.id == null && isAdminOrUser(pref.prof!!.id)) {
                timePrefDAO.makePersistent(pref);
            }
            responseCode = HttpStatus.UNAUTHORIZED;
            return null
        }

    }

    class DeletePref(val prefId: Int) : Transaction<Int?>() {
        override fun action(): Int? {
            val timePrefDAO = HibernateUtil.getDAOFact().timePrefDAO
            val timePref : TimePref? = timePrefDAO.findById(prefId)
            if (isAdminOrUser(timePref!!.prof!!.id)) {
                timePrefDAO.makeTransient(timePref);
            }
            responseCode = HttpStatus.UNAUTHORIZED;
            return null
        }

    }

    class PutPref(val prefId: Int, val pref: TimePref) : Transaction<Int?>() {
        override fun action(): Int? {
            val timePrefDAO = HibernateUtil.getDAOFact().timePrefDAO
            var timePref : TimePref? = timePrefDAO.findById(prefId)
            if (isAdminOrUser(timePref!!.prof!!.id)) {
                copyNonNulls(pref, timePref, "id");
            }
            responseCode = HttpStatus.UNAUTHORIZED;
            return null
        }
    }

}

fun getNullPropertyNames(source: Any) : Array<String> {
    val src = BeanWrapperImpl(source);
    val pds = src.getPropertyDescriptors();

    var emptyNames = mutableSetOf<String>();
    for(pd in pds) {
        val srcValue = src.getPropertyValue(pd.name);
        if (srcValue == null) emptyNames.add(pd.name);
    }
    return emptyNames.toTypedArray();
}

// then use Spring BeanUtils to copy and ignore null
fun copyNonNulls(src: Any, target: Any, vararg ignores : String) {
    BeanUtils.copyProperties(src, target, *getNullPropertyNames(src), *ignores)
}
