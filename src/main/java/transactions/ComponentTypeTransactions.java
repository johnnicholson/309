package transactions;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

import dao.ComponentTypeDAO;
import dao.PersonDAO;
import hibernate.HibernateUtil;
import model.ComponentType;
import model.Person;

public class ComponentTypeTransactions {
	public static class GetComponentType extends Transaction<ComponentType> {
		private int cmpTypeID;

		public GetComponentType(int cmpTypeID) {
			this.cmpTypeID = cmpTypeID;
		}

		@Override
		public ComponentType action() {
			ComponentType ct = null;
			if (isStaff()) {
				ct = HibernateUtil.getDAOFact().getComponentTypeDAO().findById(cmpTypeID);
				if (ct != null) {
					Hibernate.initialize(ct);
				} else {
					responseCode = HttpStatus.NOT_FOUND;
				}
			} else {
				responseCode = HttpStatus.UNAUTHORIZED;
			}
			return ct;
		}
	}
	
	public static class PostComponentType extends Transaction<Integer> {
		private ComponentType ct;

	    public PostComponentType(ComponentType ct) {
	      this.ct = ct;
	    }

	    @Override
	    public Integer action() {
	      ComponentTypeDAO ctDAO = HibernateUtil.getDAOFact().getComponentTypeDAO();
	      if (isAdmin()) {
	        ctDAO.makePersistent(ct);
	      } else {
	        responseCode = HttpStatus.UNAUTHORIZED;
	        return null;
	      }
	      return ct.getId();
	    }
	}
	
	public static class GetComponentTypeList extends Transaction<List<ComponentType>> {
		@Override
		public List<ComponentType> action() {
			List<ComponentType> cts = null;
			if (isStaff()) {
				cts = HibernateUtil.getDAOFact().getComponentTypeDAO().findAll();
				if (cts != null) {
					Hibernate.initialize(cts);
				} else {
					responseCode = HttpStatus.NOT_FOUND;
				}
			} else {
				responseCode = HttpStatus.UNAUTHORIZED;
			}
			return cts;
		}
	}
	
	public static class PutComponentType extends Transaction<Integer> {
		private ComponentType ct;
	    private Integer id;

	    public PutComponentType(ComponentType ct, Integer id) {
	      this.ct = ct;
	      this.id = id;
	    }

	    @Override
	    public Integer action() {
	      ComponentTypeDAO ctDAO = HibernateUtil.getDAOFact().getComponentTypeDAO();
	      ComponentType dbct = ctDAO.findById(id);
	      if (isAdmin()) {
	    	  if (ct == null) {
	    		  responseCode = HttpStatus.NOT_FOUND;
	    	  }	
	    	  else
	    		  BeanUtils.copyProperties(ct, dbct,"id");
	      }else {
	    	  this.responseCode = HttpStatus.UNAUTHORIZED;
	    	  return null;
	      }
	      return null;
	    }
	}
	
	public static class DeleteComponentType extends Transaction<Integer> {
		private int cmpTypeID;

		public DeleteComponentType(int cmpTypeID) {
			this.cmpTypeID = cmpTypeID;
		}

		@Override
		public Integer action() {
			ComponentType ct = null;
			ComponentTypeDAO ctDAO = null;
			if (isAdmin()) {
				ctDAO = HibernateUtil.getDAOFact().getComponentTypeDAO();
				ct = ctDAO.findById(cmpTypeID);
				if (ct != null) {
					ctDAO.makeTransient(ct);
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
