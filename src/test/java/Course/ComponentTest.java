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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import Home.TestRunner;
import Person.People;
import controller.ComponentController;
import dao.ComponentTypeDAO;
import dao.DAOFactory;
import dao.PersonDAO;
import hibernate.HibernateUtil;
import model.ComponentType;
import model.Person.Role;

public class ComponentTest {
  static PersonDAO prsDAO;
  static ComponentTypeDAO ctDAO;
  
  MockHttpServletResponse res;
  MockHttpServletRequest req;
  People people;

  @BeforeClass
  public static void topSetup() {
    DAOFactory fact = TestRunner.getMockFact();

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
  public void ComponentPostTest() {

    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Staff;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);

    ComponentType mockct = mock(ComponentType.class);
    when(ctDAO.findById(1)).thenReturn(mockct);
    ComponentType ct = ComponentController.getComponentType(1, req, res);
    verify(ctDAO, times(1)).findById(1);
    assertEquals(ct, mockct);
    
  }

}
