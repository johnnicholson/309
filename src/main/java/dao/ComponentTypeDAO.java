package dao;

import hibernate.HibernateUtil;
import model.ComponentType;

/* This class is the DAO for the Component Type Model.
 * 
 * Christiana Ushana & John Nicholson
 * Created on Feb 8 2017
 */
public class ComponentTypeDAO extends GenericHibernateDAO<ComponentType> {

    public ComponentType findByName(String cmpType) {
        return (ComponentType) HibernateUtil.getFactory().getCurrentSession()
        .createQuery("from ComponentType where name = :cmpType").setString("cmpType", cmpType.toLowerCase())
        .uniqueResult();
    }
}
