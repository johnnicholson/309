package transactions;

import dao.CourseDAO;
import hibernate.HibernateUtil;
import model.Component;
import model.Course;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

import java.util.List;

public class CourseTransactions {
  public static class GetAllCourses extends Transaction<List<Course>> {

    @Override
    public List<Course> action() {
      if (isStaff()) {
        CourseDAO courseDAO = HibernateUtil.getDAOFact().getCourseDAO();
        List<Course> courses = courseDAO.findAll();
        return courses;
      } else {
        responseCode = HttpStatus.UNAUTHORIZED;
        return null;
      }
    }
  }


  public static class GetCourse extends Transaction<Course> {
    private int courseId;

    public GetCourse(int courseId) {
      this.courseId = courseId;
    }

    @Override
    public Course action() {
      Course c = null;
      if (isStaff()) {
        c = HibernateUtil.getDAOFact().getCourseDAO().findById(courseId);
        if (c != null) {
          Hibernate.initialize(c);
        } else {
          // Non admin entities don't need to know if that id exists
          if (isAdmin()) {
            responseCode = HttpStatus.NOT_FOUND;
          } 
        }
      } else {
        responseCode = HttpStatus.UNAUTHORIZED;
      }
      return c;
    }

  }

  //does the transaction still stay as an Integer type because we are pushing them to the database by their id?
  public static class PostCourse extends Transaction<Integer> {
    private Course course;

    public PostCourse(Course course) {
      this.course = course;
    }

    @Override
    public Integer action() {
      CourseDAO courseDAO = HibernateUtil.getDAOFact().getCourseDAO();
      if (isAdmin()) {
        if (course.getName() != null && null == courseDAO.findByName(course.getName())) {
          courseDAO.makePersistent(course);
        } else {
          responseCode = HttpStatus.BAD_REQUEST;
          return null;
        }
      } else {
        responseCode = HttpStatus.UNAUTHORIZED;
      }
      return course.getId();
    }

  }



  public static class PutCourse extends Transaction<Integer> {
    private Course crs;
    private Integer id;

    public PutCourse(Course crs, Integer id) {
      this.crs = crs;
      this.id = id;
    }

   @Override
   public Integer action() {
     CourseDAO crsDAO = HibernateUtil.getDAOFact().getCourseDAO();
     Course dbcrs = crsDAO.findById(id);
     if (isAdmin()) {
       BeanUtils.copyProperties(crs, dbcrs, "id", "passwordHash");
     } else  {
       this.responseCode = HttpStatus.UNAUTHORIZED;
       return null;
     }

     return null;

    }
  }

  public static class DeleteCourse extends Transaction<Integer> {
    private int courseId;

    public DeleteCourse(int courseId) {
      this.courseId = courseId;
    }

    @Override
    public Integer action() {
      Course c = null;
      if (isAdmin()) {
        CourseDAO crsDAO = HibernateUtil.getDAOFact().getCourseDAO();
        c = crsDAO.findById(courseId);
        if (c != null) {
            crsDAO.makeTransient(c);
        } else {
          // Non admin entities don't need to know if that id exists
          if (isAdmin()) {
            responseCode = HttpStatus.NOT_FOUND;
          }
        }
      } else {
        responseCode = HttpStatus.UNAUTHORIZED;
      }
      return null;
    }

  }


  public static class getComponents extends Transaction<List<Component>> {
    private final int crsId;

    public getComponents(int crsId) {
      this.crsId = crsId;
    }


    @Override public List<Component> action() {
      List<Component> cmps = null;
      if (isStaff()) {
        CourseDAO crsDAO = HibernateUtil.getDAOFact().getCourseDAO();
        Course crs = crsDAO.findById(crsId);
        if (crs != null) {
          cmps = crs.getComponents();
        } else {
          responseCode = HttpStatus.NOT_FOUND;
        }
      } else {
        responseCode = HttpStatus.UNAUTHORIZED;
      }
      return cmps;
    }
  }
}
