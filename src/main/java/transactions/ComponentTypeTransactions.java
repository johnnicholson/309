package transactions;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;

import dao.ComponentTypeDAO;
import hibernate.HibernateUtil;
import model.ComponentType;

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
}
