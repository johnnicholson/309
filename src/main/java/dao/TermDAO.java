package dao;

import java.util.List;
import model.Term;

/* This class is the DAO for the Term Model.
 * 
 * FILL IN NAMES LATER
 * Created on Feb 10 2017
 */
public class TermDAO extends GenericHibernateDAO<Term> {
  public List<Term> getPublishedTerms() {
      return (List<Term>) getSession().createQuery("from Term where isPublished != 0").list();
  }

}
