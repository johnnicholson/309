package dao;

import hibernate.HibernateUtil;
import model.Course;
import model.Person;

public class CourseDAO extends GenericHibernateDAO<Course> {

  public Course findByName(String name) {
    return (Course) HibernateUtil.getFactory().getCurrentSession()
        .createQuery("from Course where name = :name").setString("name", name.toLowerCase())
        .uniqueResult();
  }

}
