package Course;

import Home.TestRunner;
import controller.ComponentController;
import controller.CourseController;
import dao.ComponentDAO;
import dao.CourseDAO;
import dao.DAOFactory;
import dao.PersonDAO;
import hibernate.HibernateUtil;
import model.Component;
import model.Course;
import model.Person;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by jnicho on 2/4/2017.
 */
public class ComponentTest {
  static PersonDAO prsDAO;
  static CourseDAO crsDAO;
  static ComponentDAO cmpDAO;

    MockHttpServletResponse res;
  MockHttpServletRequest req;

  @BeforeClass
  public static void topSetup() {
    DAOFactory fact = TestRunner.getMockFact();
    prsDAO = mock(PersonDAO.class);
    cmpDAO = mock(ComponentDAO.class);
    crsDAO = mock(CourseDAO.class);
    when(fact.getPersonDAO()).thenReturn(prsDAO);
    when(fact.getCourseDAO()).thenReturn(crsDAO);
    when(fact.getComponentDAO()).thenReturn(cmpDAO);

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
    reset(cmpDAO);
  }

  @Test
  public void postComponentTest() {
    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Person.Role.Admin;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);

    Course mkcrs = mock(Course.class);
    when(crsDAO.findById(1)).thenReturn(mkcrs);
    Component cmp = new Component();
    CourseController.postComponent(cmp, 1, req, res);
    verify(cmpDAO).makePersistent(cmp);
    verify(mkcrs).addComponent(cmp);
  }

  @Test
  public void getComponentTest() {
    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Person.Role.Staff;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);

    Component cmp = new Component();

    when(cmpDAO.findById(1)).thenReturn(cmp);
    Component cmpret = ComponentController.getComponent(1, req, res);
    assertEquals(cmp, cmpret);
  }

  @Test
  public void getCourseComponentsTest() {
    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Person.Role.Staff;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);
    Component cmp = new Component();
    Component cmp2 = new Component();
    Course crs = new Course();
    crs.setComponents(new ArrayList<Component>());
    crs.addComponent(cmp);
    crs.addComponent(cmp2);

    when(crsDAO.findById(1)).thenReturn(crs);

    List<Component> cmps = CourseController.getComponents(1, req, res);

    assertEquals(cmps, crs.getComponents());
  }
}
