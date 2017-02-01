package Course;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import Person.People;
import app.AuthInterceptor;
import controller.ComponentController;
import controller.SessionController;
import controller.SessionController.Login;
import dao.ComponentTypeDAO;
import dao.DAOFactory;
import dao.PersonDAO;
import hibernate.HibernateUtil;
import model.ComponentType;
import model.Person;
import model.Person.Role;

public class ComponentTest {
  static PersonDAO prsDAO;
  static ComponentTypeDAO ctDAO;
  
  AuthInterceptor auth;
  MockHttpServletResponse res;
  MockHttpServletRequest req;
  People people;

  @BeforeClass
  public static void topSetup() {
    prsDAO = mock(PersonDAO.class);
    ctDAO = mock(ComponentTypeDAO.class);
    DAOFactory fact = mock(DAOFactory.class);
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
    auth = new AuthInterceptor();
    res = new MockHttpServletResponse();
    req = new MockHttpServletRequest();
  }
  
  @Test
  public void testComponentPost() {
    Person mockPrs = mock(Person.class);
    when(prsDAO.findByUsername("username")).thenReturn(mockPrs);
    when(mockPrs.checkPassword("pass")).thenReturn(true);
    when(mockPrs.getRole()).thenReturn(Role.Staff);
    when(mockPrs.getId()).thenReturn(1);

    req.setServletPath("/api/ssns");
    req.setMethod("POST");
    SessionController.postSession(new Login("username", "pass"), req, res);
    req.setCookies(res.getCookies());
    auth.preHandle(req, res, null);
    ComponentType mockct = mock(ComponentType.class);
    when(ctDAO.findById(1)).thenReturn(mockct);
    ComponentType ct = ComponentController.getComponentType(1, req, res);
    verify(ctDAO, times(1)).findById(1);
    assertEquals(ct, mockct);
    
  }

}
