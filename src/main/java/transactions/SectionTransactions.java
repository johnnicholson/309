package transactions;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

import controller.PersonController.PasswordChange;
import dao.ComponentTypeDAO;
import dao.EquipmentDAO;
import dao.PersonDAO;
import dao.SectionDAO;
import hibernate.HibernateUtil;
import model.ComponentType;
import model.Equipment;
import model.Section;

public class SectionTransactions {
	
	public static class GetAllSections extends Transaction<List<Section>> {
	    @Override
	    public List<Section> action() {
	      if (isAdmin()) {
	        SectionDAO sectDAO = HibernateUtil.getDAOFact().getSectionDAO();
	        List<Section> s = sectDAO.findAll();
	        return s;
	      } else {
	        responseCode = HttpStatus.UNAUTHORIZED;
	        return null;
	      }
	    }
	  }

	  public static class GetSection extends Transaction<Section> {
	    private int sectionID;

	    public GetSection(int sectionID) {
	      this.sectionID = sectionID;
	    }

	    @Override
	    public Section action() {
	      Section s = null;
	      if (isStaff()) {
	        s = HibernateUtil.getDAOFact().getSectionDAO().findById(sectionID);
	        if (s != null) {
	          Hibernate.initialize(s);
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
	      return s;
	    }

	  }

	  public static class PostSection extends Transaction<Integer> {
	    private Section s;

	    public PostSection(Section section) {
	      this.s = section;
	    }

	    @Override
	    public Integer action() {
	      SectionDAO sectDAO = HibernateUtil.getDAOFact().getSectionDAO();
	      if (isAdmin()) {
	        sectDAO.makePersistent(s);
	      } else {
	        responseCode = HttpStatus.BAD_REQUEST;
	        return null;
	      }
	      return s.getId();
	    }

	  }

	  public static class PutSection extends Transaction<Integer> {
	    private Section sect;
	    private Integer id;

	    public PutSection(Section sect, Integer id) {
	      this.sect = sect;
	      this.id = id;
	    }

	    @Override
	    public Integer action() {
	      SectionDAO sectDAO = HibernateUtil.getDAOFact().getSectionDAO();
	      Section dbsect = sectDAO.findById(id);
	      if (isAdmin()) {
	        if (sect == null) {
	        	responseCode = HttpStatus.NOT_FOUND;
	        }
	        else {
	        	BeanUtils.copyProperties(sect, dbsect, "id");
	        } 
	      }else {
	    	  this.responseCode = HttpStatus.UNAUTHORIZED;
	    	  return null;
	      }
	      return null;
	    }
	  }
	  
	  
	  public static class DeleteSection extends Transaction<Integer> {
			private int sectID;

			public DeleteSection(int sectID) {
				this.sectID = sectID;
			}

			@Override
			public Integer action() {
				Section s = null;
				SectionDAO sectDAO = null;
				if (isAdmin()) {
					sectDAO = HibernateUtil.getDAOFact().getSectionDAO();
					s = sectDAO.findById(sectID);
					if (s != null) {
						sectDAO.makeTransient(s);
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
