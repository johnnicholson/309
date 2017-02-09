package Course;

import Home.TestRunner;
import controller.ComponentTypeController;
import dao.ComponentTypeDAO;
import dao.DAOFactory;
import dao.PersonDAO;
import hibernate.HibernateUtil;
import model.ComponentType;
import model.Person.Role;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/* This class is the Tests for everything relating to the Component Type Model
 * 
 * Christiana Ushana & John Nicholson
 * Created on Feb 8 2017
 */
public class ComponentTypeTest {
  static PersonDAO prsDAO;
  static ComponentTypeDAO ctDAO;
  
  MockHttpServletResponse res;
  MockHttpServletRequest req;

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
  
  /* Get Test
   * This method tests getting (a) component type object(s).
   */
  @Test
  public void ComponentTypeGetTest() {

    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Staff;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);

    ComponentType mockct = mock(ComponentType.class);
    when(ctDAO.findById(1)).thenReturn(mockct);
    ComponentType ct = ComponentTypeController.getComponentType(1, req, res);
    verify(ctDAO, times(2)).findById(1);
    assertEquals(ct, mockct);
  }
  
  /* Post Test
   * This method tests creating a single component type object.
   */
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
  
  /* Put Test
   * This method tests reassigning data for a single component type object.
   */
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
    verify(mockct).setName("newComponentName");
    verify(mockct).setDescription("newComponentType");
  }
  
  /* Delete Test
   * This method tests deletes a single component type object.
   */
  @Test
  public void ComponentTypeDeleteTest() {
    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Admin;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);
    
    ComponentType mockct = mock(ComponentType.class);
    when(ctDAO.findById(1)).thenReturn(mockct);
    Integer id = ComponentTypeController.deleteComponentType(1, req, res);
    verify(ctDAO, times(1)).makeTransient(mockct);
  }

}
