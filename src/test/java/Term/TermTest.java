package Term;

import Home.TestRunner;
import controller.ComponentTypeController;
import controller.TermController;
import dao.TermDAO;
import dao.DAOFactory;
import dao.PersonDAO;
import hibernate.HibernateUtil;
import model.ComponentType;
import model.Term;
import model.Person.Role;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.reset;

public class TermTest {
  static PersonDAO prsDAO;
  static TermDAO termDAO;
  
  MockHttpServletResponse res;
  MockHttpServletRequest req;

  @BeforeClass
  public static void topSetup() {
    DAOFactory fact = TestRunner.getMockFact();
    prsDAO = mock(PersonDAO.class);
    termDAO = mock(TermDAO.class);
    when(fact.getPersonDAO()).thenReturn(prsDAO);
    when(fact.getTermDAO()).thenReturn(termDAO);

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
    reset(termDAO);
  }
  
  @Test
  public void TermGetTest() {

    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Staff;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);

    Term mockterm = mock(Term.class);
    when(termDAO.findById(1)).thenReturn(mockterm);
    Term t = TermController.getTerm(1, req, res);
    verify(termDAO, times(1)).findById(1);
    assertEquals(t, mockterm);
  }
  
  @Test
  public void TermPostTest() {
    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Admin;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);
    
    Term mockterm = mock(Term.class);
    when(termDAO.makePersistent(mockterm)).thenReturn(mockterm);
    Integer id = TermController.postTerm(mockterm, req, res);
    verify(termDAO, times(1)).makePersistent(mockterm);
  }
  
  @Test
  public void TermPutTest() {
    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Admin;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);
    
    Term mockterm = mock(Term.class);
    when(termDAO.findById(1)).thenReturn(mockterm);
    Term otherterm = new Term();
    otherterm.setName("newTermWithDate");
    otherterm.setStatus(1);
    TermController.putTerm(otherterm, 1, req, res);
    verify(mockterm).setName("newTermWithDate");
    verify(mockterm).setStatus(1);
  }
 
  @Test
  public void TermDeleteTest() {
    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Admin;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);
    
    Term mockterm = mock(Term.class);
    when(termDAO.findById(1)).thenReturn(mockterm);
    Integer id = TermController.deleteTerm(1, req, res);
    verify(termDAO, times(1)).makeTransient(mockterm);
  }

}
