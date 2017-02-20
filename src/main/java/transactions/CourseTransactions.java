package transactions;

import dao.ComponentDAO;
import dao.ComponentTypeDAO;
import dao.CourseDAO;
import hibernate.HibernateUtil;
import java.util.ArrayList;
import model.Component;
import model.ComponentType;
import model.Course;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
      ComponentDAO componentDAO = HibernateUtil.getDAOFact().getComponentDAO();
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

  public static class ImportCourses extends Transaction<Integer> {
        private String courseFile;

        public ImportCourses(String courseFile) {
            this.courseFile = courseFile;
        }

        @Override public Integer action() {
            ComponentTypeDAO cmpTypeDAO = HibernateUtil.getDAOFact().getComponentTypeDAO();
            CourseDAO crsDAO = HibernateUtil.getDAOFact().getCourseDAO();
            ComponentDAO cmpDAO = HibernateUtil.getDAOFact().getComponentDAO();

            Scanner scanner = new Scanner(courseFile);
            while (scanner.hasNextLine()) {
                String[] courseInfo = scanner.nextLine().split(",");
                Course course = new Course();
                course.setName(courseInfo[0]);
                //course.setDescription(courseInfo[1]);
                course.setUnits(4);
                course.setComponents(new ArrayList<Component>());
                for (int i = 2; i < courseInfo.length; i += 2) {
                    Component comp = new Component();
                    String cmpType = courseInfo[i].trim().split(" ")[1];
                    ComponentType type = cmpTypeDAO.findByName(cmpType);
                    if (type == null) {
                        type = new ComponentType();
                        type.setName(cmpType);
                        type.setDescription("generated");
                        cmpTypeDAO.makePersistent(type);
                    }
                    comp.setComponentType(type);
                    comp.setHours(Integer.parseInt(courseInfo[i].trim().split(" ")[0]));
                    comp.setWorkUnits(Integer.parseInt(courseInfo[i].trim().split(" ")[0]));
                    cmpDAO.makePersistent(comp);
                    course.addComponent(comp);
                }
                crsDAO.makePersistent(course);
            }
            return null;
        }
    }
}
