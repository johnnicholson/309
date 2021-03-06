package Course;

import Home.TestRunner;
import controller.CourseController;
import dao.CourseDAO;
import dao.DAOFactory;
import dao.PersonDAO;
import hibernate.HibernateUtil;
import model.Course;
import model.Person.Role;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CourseTest {
  static PersonDAO prsDAO;
  static CourseDAO crsDAO;
  
  MockHttpServletResponse res;
  MockHttpServletRequest req;

  @BeforeClass
  public static void topSetup() {
    DAOFactory fact = TestRunner.getMockFact();
    prsDAO = mock(PersonDAO.class);
    crsDAO = mock(CourseDAO.class);
    when(fact.getPersonDAO()).thenReturn(prsDAO);
    when(fact.getCourseDAO()).thenReturn(crsDAO);

    HibernateUtil.setDAOFactory(fact);
    org.hibernate.Session ssn = mock(org.hibernate.Session.class);
    org.hibernate.Transaction transaction = mock(org.hibernate.Transaction.class);
    SessionFactory sfact = mock(SessionFactory.class);
    when(sfact.getCurrentSession()).thenReturn(ssn);
    when(ssn.getTransaction()).thenReturn(transaction);
    HibernateUtil.setFactory(sfact);
  }
  
  @Before
  public void setup() {
    res = new MockHttpServletResponse();
    req = mock(MockHttpServletRequest.class);
    reset(crsDAO);
  }
  
  @Test
  public void CourseGetTest() {

    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Staff;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);

    Course mkcrs = mock(Course.class);
    when(crsDAO.findById(1)).thenReturn(mkcrs);
    Course crs = CourseController.getCourse(1, req, res);
    verify(crsDAO, times(1)).findById(1);
    assertEquals(crs, mkcrs);
    
  }

  @Test
  public void CoursePostTest() {

    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Admin;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);

    Course course = new Course();
    course.setName("Name");

    when(crsDAO.findByName("Name")).thenReturn(null);
    CourseController.postCourse(course, req, res);
    verify(crsDAO).makePersistent(course);

  }

  @Test
  public void CoursePutTest() {
     app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Admin;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);

    Course course = new Course();
    course.setName("Name");
    course.setId(1);
    Course mkcrs = mock(Course.class);
    when(crsDAO.findById(1)).thenReturn(mkcrs);

    CourseController.putCourse(course, 1, req, res);
    verify(crsDAO).findById(1);
    verify(mkcrs).setName("Name");


  }

  @Test
  public void CourseDeleteTest() {
     app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Admin;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);

    Course mkcrs = mock(Course.class);
    when(crsDAO.findById(1)).thenReturn(mkcrs);
    CourseController.deleteCourse(1, req, res);
    verify(crsDAO).makeTransient(mkcrs);

  }
}
