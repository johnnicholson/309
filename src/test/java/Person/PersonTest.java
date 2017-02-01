package Person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import app.AuthInterceptor;
import app.Session;
import app.Util;
import controller.PersonController;
import controller.SessionController;
import controller.SessionController.Login;
import dao.PersonDAO;
import hibernate.HibernateUtil;
import model.Person;
import model.Person.Role;


public class PersonTest {

  AuthInterceptor auth;
  MockHttpServletResponse res;
  MockHttpServletRequest req;
  People people = new People();
  static PersonDAO prsDAO;

  @BeforeClass
  public static void topSetup() {
    prsDAO = mock(PersonDAO.class);
    MockDAOFact fact = new MockDAOFact();
    fact.prsDAO = prsDAO;
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

    people = new People();
    auth = new AuthInterceptor();
    res = new MockHttpServletResponse();
    req = new MockHttpServletRequest();
  }

  // Move this to the student tests
  @Test
  public void createStudent() {

    when(prsDAO.findByUsername(people.prsB.getUsername())).thenReturn(null);
    when(prsDAO.makePersistent(people.prsB)).thenReturn(people.prsB);
    /* register prsB */
    req.setServletPath("/api/prss");
    req.setMethod("POST");
    PersonController.postPerson(people.prsB, req, res);
    
    /* Check that the auth is working */
    req.setCookies(res.getCookies());
    assertTrue(auth.preHandle(req, res, null));
    /* Check that prsB was saved */
    verify(prsDAO, Mockito.times(1)).makePersistent(people.prsB);
  }

  @Test
  public void loginCheck() {
    when(prsDAO.findByUsername(people.prsB.getUsername())).thenReturn(people.prsB);
    when(prsDAO.makePersistent(people.prsB)).thenReturn(people.prsB);
    
    req.setServletPath("/api/ssns");
    req.setMethod("POST");
    SessionController.postSession(new Login(people.prsB.getUsername(), people.passB), req, res);
    assertTrue(res.getCookies().length == 1);
  }
  
  @Test
  public void dupRegisterTest() {
    when(prsDAO.findByUsername(people.prsC.getUsername())).thenReturn(people.prsC);
    
    req.setServletPath("/api/prss");
    req.setMethod("POST");
    assertTrue(auth.preHandle(req, res, null));
    PersonController.postPerson(people.prsC, req, res);
    assertEquals(res.getStatus(), HttpStatus.BAD_REQUEST.value());
  }

  // TODO break this test up into multiple tests
  @Test
  public void permissionTest() {

    people.prsC.setId(1);
    when(prsDAO.findByUsername(people.prsC.getUsername())).thenReturn(people.prsC);

    req.setServletPath("/api/ssns");
    req.setMethod("POST");
    SessionController.postSession(new Login(people.prsC.getUsername(), people.passC), req, res);
    req.setCookies(res.getCookies());

    req.setServletPath("/api/prss");
    req.setMethod("GET");
    assertTrue(auth.preHandle(req, res, null));
    List<Person> list = PersonController.getAllPeople(req, res);
    assertEquals(res.getStatus(), HttpStatus.UNAUTHORIZED.value());
    assertNull(list);


    when(prsDAO.findById(1)).thenReturn(people.prsC);
    req.setServletPath("/api/prss/" + 1);
    req.setMethod("GET");
    Person p = PersonController.getPerson(1, req, res);
    assertEquals(res.getStatus(), HttpStatus.OK.value());
    assertEquals(people.prsC, p);

    Person mockPrs = mock(Person.class);
    when(prsDAO.findById(1)).thenReturn(mockPrs);
    when(mockPrs.getUsername()).thenReturn(people.prsC.getUsername());
    req.setServletPath("/api/prss");
    req.setMethod("PUT");
    assertTrue(auth.preHandle(req, res, null));
    people.prsC.setUsername("newUsername");
    PersonController.putPerson(people.prsC, 1, req, res);
    assertEquals(res.getStatus(), HttpStatus.UNAUTHORIZED.value());
    
    people.prsC.setUsername("oldUsername");

    when(prsDAO.findById(1)).thenReturn(mockPrs);
    when(mockPrs.getUsername()).thenReturn(people.prsC.getUsername());
    when(mockPrs.getRole()).thenReturn(Role.Student);

    req.setServletPath("/api/prss");
    req.setMethod("PUT");
    assertTrue(auth.preHandle(req, res, null));
    people.prsC.setFirstName("newName");
    PersonController.putPerson(people.prsC, 1, req, res);
    assertEquals(HttpStatus.OK.value(), res.getStatus());

    verify(mockPrs).setFirstName("newName");


  }


}
