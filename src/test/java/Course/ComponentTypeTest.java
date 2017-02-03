package Course;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import Home.TestRunner;
import Person.People;
import controller.ComponentTypeController;
import controller.PersonController;
import dao.ComponentTypeDAO;
import dao.DAOFactory;
import dao.PersonDAO;
import hibernate.HibernateUtil;
import model.ComponentType;
import model.Person;
import model.Person.Role;

public class ComponentTypeTest {
  static PersonDAO prsDAO;
  static ComponentTypeDAO ctDAO;
  
  MockHttpServletResponse res;
  MockHttpServletRequest req;
  People people;

  @BeforeClass
  public static void topSetup() {
    DAOFactory fact = TestRunner.getMockFact();
    prsDAO = mock(PersonDAO.class);
    ctDAO = mock(ComponentTypeDAO.class);
    when(fact.getPersonDAO()).thenReturn(prsDAO);
    when(fact.getComponentTypeDAO()).thenReturn(ctDAO);

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
  }
  
  @Test
  public void ComponentTypeGetTest() {

    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Staff;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);

    ComponentType mockct = mock(ComponentType.class);
    when(ctDAO.findById(1)).thenReturn(mockct);
    ComponentType ct = ComponentTypeController.getComponentType(1, req, res);
    verify(ctDAO, times(1)).findById(1);
    assertEquals(ct, mockct);
  }
  
  @Test
  public void ComponentTypePostTest() {
    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Admin;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);
    

    ComponentType mockct = mock(ComponentType.class);
    when(ctDAO.makePersistent(mockct)).thenReturn(mockct);
    Integer id = ComponentTypeController.postComponentType(mockct, req, res);
    verify(ctDAO, times(1)).makePersistent(mockct);
  }
  
  @Test
  public void ComponentTypePutTest() {
    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Admin;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);
    
    ComponentType mockct = mock(ComponentType.class);
    when(ctDAO.findById(1)).thenReturn(mockct);
    ComponentType otherct = new ComponentType();
    otherct.setName("newComponentName");
    otherct.setDescription("newComponentType");
    ComponentTypeController.putComponentType(otherct, 1, req, res);
    assertEquals(otherct.getName(), mockct.getName());
    assertEquals(otherct.getDescription(), mockct.getDescription());
  }
  
//  @Test
//  public void ComponentTypeDeleteTest() {
//    app.Session mockSession = mock(app.Session.class);
//    mockSession.role = Role.Admin;
//    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);
//    
//    ComponentType mockct = mock(ComponentType.class);
//    when(ctDAO.findById(1)).thenReturn(mockct);
//    Integer id = ComponentTypeController.deleteComponentType(1, req, res);
//    verify(ctDAO, times(1)).makeTransient(mockct);
//  }

}
