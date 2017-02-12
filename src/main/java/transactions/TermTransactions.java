package transactions;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

import controller.PersonController.PasswordChange;
import dao.ComponentTypeDAO;
import dao.EquipmentDAO;
import dao.PersonDAO;
import dao.TermDAO;
import hibernate.HibernateUtil;
import model.ComponentType;
import model.Equipment;
import model.Term;

public class TermTransactions {
	public static class GetAllTerms extends Transaction<List<Term>> {
	    @Override
	    public List<Term> action() {
	      if (isAdmin()) {
	        TermDAO termDAO = HibernateUtil.getDAOFact().getTermDAO();
	        List<Term> t = termDAO.findAll();
	        return t;
	      } else {
	        responseCode = HttpStatus.UNAUTHORIZED;
	        return null;
	      }
	    }
	  }

	  public static class GetTerm extends Transaction<Term> {
	    private int termID;

	    public GetTerm(int termID) {
	      this.termID = termID;
	    }

	    @Override
	    public Term action() {
	      Term t = null;
	      if (isStaff()) {
	        t = HibernateUtil.getDAOFact().getTermDAO().findById(termID);
	        if (t != null) {
	          Hibernate.initialize(t);
	        } else {
	          // Non admin entities don't need to know if that id exists
	          if (isAdmin()) {
	            responseCode = HttpStatus.NOT_FOUND;
	          } else {
	            responseCode = HttpStatus.UNAUTHORIZED;
	          }
	        }
	      } else {
	        responseCode = HttpStatus.UNAUTHORIZED;
	      }
	      return t;
	    }

	  }

	  public static class PostTerm extends Transaction<Integer> {
	    private Term term;

	    public PostTerm(Term term) {
	      this.term = term;
	    }

	    @Override
	    public Integer action() {
	      TermDAO termDAO = HibernateUtil.getDAOFact().getTermDAO();
	      if (isAdmin()) {
	        termDAO.makePersistent(term);
	      } else {
	        responseCode = HttpStatus.BAD_REQUEST;
	        return null;
	      }
	      return term.getId();
	    }

	  }

	  public static class PutTerm extends Transaction<Integer> {
	    private Term term;
	    private Integer id;

	    public PutTerm(Term term, Integer id) {
	      this.term = term;
	      this.id = id;
	    }

	    @Override
	    public Integer action() {
	      TermDAO termDAO = HibernateUtil.getDAOFact().getTermDAO();
	      Term dbt = termDAO.findById(id);
	      if (isAdmin()) {
	        if (term == null) {
	        	responseCode = HttpStatus.NOT_FOUND;
	        }
	        else {
	        	BeanUtils.copyProperties(term, dbt, "id");
	        } 
	      }else {
	    	  this.responseCode = HttpStatus.UNAUTHORIZED;
	    	  return null;
	      }
	      return null;
	    }
	  }
	  
	  
	  public static class DeleteTerm extends Transaction<Integer> {
			private int termID;

			public DeleteTerm(int termID) {
				this.termID = termID;
			}

			@Override
			public Integer action() {
				Term t = null;
				TermDAO termDAO = null;
				if (isAdmin()) {
					termDAO = HibernateUtil.getDAOFact().getTermDAO();
					t = termDAO.findById(termID);
					if (t != null) {
						termDAO.makeTransient(t);
					} else {
							responseCode = HttpStatus.NOT_FOUND;
					}
				} else {
					responseCode = HttpStatus.UNAUTHORIZED;
				}
				return null;
			}
		}
	  
}
