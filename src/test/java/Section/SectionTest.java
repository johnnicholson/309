package Section;

import Home.TestRunner;
import controller.ComponentTypeController;
import controller.TermController;
import controller.SectionController;
import dao.TermDAO;
import dao.DAOFactory;
import dao.PersonDAO;
import dao.SectionDAO;
import hibernate.HibernateUtil;
import model.ComponentType;
import model.Component;
import model.Course;
import model.Term;
import model.Person.Role;
import model.Person;
import model.Section;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.reset;

public class SectionTest {
  static PersonDAO prsDAO;
  static SectionDAO sectDAO;
  
  MockHttpServletResponse res;
  MockHttpServletRequest req;

  @BeforeClass
  public static void topSetup() {
    DAOFactory fact = TestRunner.getMockFact();
    prsDAO = mock(PersonDAO.class);
    sectDAO = mock(SectionDAO.class);
    when(fact.getPersonDAO()).thenReturn(prsDAO);
    when(fact.getSectionDAO()).thenReturn(sectDAO);

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
    reset(sectDAO);
  }
  
  @Test
  public void SectionGetTest() {

    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Staff;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);

    Section mocksect = mock(Section.class);
    when(sectDAO.findById(1)).thenReturn(mocksect);
    Section s = SectionController.getSection(1, req, res);
    verify(sectDAO, times(1)).findById(1);
    assertEquals(s, mocksect);
  }
  
  @Test
  public void SectionPostTest() {
    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Admin;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);
    
    Section mocksect = mock(Section.class);
    when(sectDAO.makePersistent(mocksect)).thenReturn(mocksect);
    SectionController.postSection(mocksect, req, res);
    verify(sectDAO, times(1)).makePersistent(mocksect);
  }
  
  @Test
  public void SectionPutTest() {
    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Admin;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);
    
    Section mocksect = mock(Section.class);
    when(sectDAO.findById(1)).thenReturn(mocksect);
    Section othersect = new Section();
    othersect.setNameSect("CPE309-01");
    //othersect.setStatus(1);
    Person p = mock(Person.class);
    othersect.setProf(p);
    Component c = mock(Component.class);
    othersect.setComponent(c);
    Course cs = mock(Course.class);
    othersect.setCourse(cs);
//    othersect.setStartTime(5);
    SectionController.putSection(othersect, 1, req, res);
    verify(mocksect).setNameSect("CPE309-01");
    verify(mocksect).setProf(p);
    verify(mocksect).setComponent(c);
    verify(mocksect).setCourse(cs);
//    verify(mocksect).setStartTime(5);
  }
 
  @Test
  public void SectionDeleteTest() {
    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Admin;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);
    
    Section mocksect = mock(Section.class);
    when(sectDAO.findById(1)).thenReturn(mocksect);
    SectionController.deleteSection(1, req, res);
    verify(sectDAO, times(1)).makeTransient(mocksect);
  }

}
