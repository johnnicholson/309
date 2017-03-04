package transactions;

import controller.PersonController.PasswordChange;
import dao.CoursePrefDAO;
import dao.PersonDAO;
import dao.TimePrefDAO;
import hibernate.HibernateUtil;
import model.CoursePref;
import model.Person;
import model.Person.Role;

import model.TimePref;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

import org.hibernate.Hibernate;

import java.util.List;

public class PersonTransactions {

  public static class GetAllPeople extends Transaction<List<Person>> {

    @Override
    public List<Person> action() {
      if (isAdmin()) {
        PersonDAO prsDAO = HibernateUtil.getDAOFact().getPersonDAO();
        List<Person> prss = prsDAO.findAll();
        return prss;
      } else {
        responseCode = HttpStatus.UNAUTHORIZED;
        return null;
      }
    }
  }


  public static class GetPerson extends Transaction<Person> {
    private int prsId;

    public GetPerson(int prsId) {
      this.prsId = prsId;
    }

    @Override
    public Person action() {
      Person p = null;
      if (prsId == getSession().prsId || isAdmin()) {
        p = HibernateUtil.getDAOFact().getPersonDAO().findById(prsId);
        if (p != null) {
          Hibernate.initialize(p);
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
      return p;
    }

  }

  public static class PostPerson extends Transaction<Integer> {
    private Person prs;

    public PostPerson(Person prs) {
      this.prs = prs;
    }

    @Override
    public Integer action() {
      PersonDAO prsDAO = HibernateUtil.getDAOFact().getPersonDAO();
      if (null == prsDAO.findByUsername(prs.getUsername())
          && (prs.getRole() == Role.Student || isAdmin())) {
        prsDAO.makePersistent(prs);
      } else {
        responseCode = HttpStatus.BAD_REQUEST;
        return null;
      }
      return prs.getId();
    }

  }

  public static class ChangePassword extends Transaction<Integer> {
    private PasswordChange pwChange;
    private Integer id;

    public ChangePassword(PasswordChange pwChange, Integer id) {
      this.pwChange = pwChange;
      this.id = id;
    }

    @Override
    public Integer action() {
      PersonDAO prsDAO = HibernateUtil.getDAOFact().getPersonDAO();
      Person prs = prsDAO.findById(id);
      if (pwChange.newPassword != null && pwChange.oldPassword != null
          && (prs.checkPassword(pwChange.oldPassword) || isAdmin())) {
        prs.setPassword(pwChange.newPassword);
      } else {
        this.responseCode = HttpStatus.BAD_REQUEST;
      }
      return null;
    }
  }



  public static class PutPerson extends Transaction<Integer> {
    private Person prs;
    private Integer id;

    public PutPerson(Person prs, Integer id) {
      this.prs = prs;
      this.id = id;
    }

    @Override
    public Integer action() {
      PersonDAO prsDAO = HibernateUtil.getDAOFact().getPersonDAO();
      Person dbprs = prsDAO.findById(id);
      if (isAdmin()) {
        BeanUtils.copyProperties(prs, dbprs, "id", "passwordHash");
      } else if ((prs.getUsername() != null && !dbprs.getUsername().equals(prs.getUsername()))
          || (prs.getRole() != null && dbprs.getRole() != prs.getRole())) {
        this.responseCode = HttpStatus.UNAUTHORIZED;
        return null;
      } else {
        BeanUtils.copyProperties(prs, dbprs, "id", "email", "role", "passwordHash");
      }

      return null;

    }
  }

    public static class DeletePerson extends Transaction<Integer>{

    private int prsID;

    public DeletePerson(int prsId) {
      this.prsID = prsId;
    }

    @Override
    public Integer action() {
      PersonDAO prsDAO;
      Person prs;
      // Check permissions
      if (isAdmin()) {
        prsDAO = HibernateUtil.getDAOFact().getPersonDAO();
        prs = prsDAO.findById(prsID);

        // Delete if found
        if (prs != null) {
          prsDAO.makeTransient(prs);
        } else {
          responseCode = HttpStatus.NOT_FOUND;
        }
      } else {
        responseCode = HttpStatus.UNAUTHORIZED;
      }
      return null;
    }
  }


  public static class GetPersonTimePrefs extends Transaction<List<TimePref>>{
    private final int prsId;

    public GetPersonTimePrefs(int prsId) {
      this.prsId = prsId;
    }

    @Override public List<TimePref> action() {
       TimePrefDAO pDAO = HibernateUtil.getDAOFact().getTimePrefDAO();

       if (isAdminOrUser(prsId)) {
         return pDAO.findByUser(prsId);
       }
       responseCode = HttpStatus.UNAUTHORIZED;
       return null;
    }
  }


  public static class GetPersonCoursePrefs extends Transaction<List<CoursePref>>{
    private int prsId;

    public GetPersonCoursePrefs(int prsId) {
      this.prsId = prsId;
    }

    @Override public List<CoursePref> action() {
      CoursePrefDAO cpDAO = HibernateUtil.getDAOFact().getCoursePrefDAO();
      if (isAdminOrUser(prsId)) {
        return cpDAO.findByUser(prsId);
      }
      responseCode = HttpStatus.UNAUTHORIZED;
      return null;
    }
  }
}
