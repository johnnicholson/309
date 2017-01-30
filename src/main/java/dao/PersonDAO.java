package dao;

import hibernate.HibernateUtil;
import model.Person;

public class PersonDAO extends GenericHibernateDAO<Person> {

  public Person findByUsername(String username) {
    return (Person) HibernateUtil.getFactory().getCurrentSession()
        .createQuery("from Person where username = :username").setString("username", username.toLowerCase())
        .uniqueResult();
  }

}
