package dao

import model.CoursePref

class CoursePrefDAO : GenericHibernateDAO<CoursePref>() {
    fun findByUser(prsId: Int): List<CoursePref> {
        return getSession().createQuery("from CoursePref where prof_id = :prof").setInteger("prof", prsId).list() as List<CoursePref>;
    }
}
