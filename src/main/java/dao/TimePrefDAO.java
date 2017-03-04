package dao;

import model.TimePref;

import java.util.List;

/**
 * Created by jnicho on 2/24/17.
 */
public class TimePrefDAO extends GenericHibernateDAO<TimePref> {
    public List<TimePref> findByUser(int prsId) {
        return (List<TimePref>) getSession().createSQLQuery("select * from timepref where prof_id = :prof").setInteger("prof", prsId).list();
    }
}
