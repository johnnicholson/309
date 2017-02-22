package Home;

import Person.People;
import app.AuthInterceptor;
import app.Util;
import controller.PersonController;
import controller.SessionController;
import controller.SessionController.Login;
import hibernate.HibernateUtil;
import model.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.List;

import static org.junit.Assert.*;

public class RegressTest {

  AuthInterceptor auth;
  MockHttpServletResponse res;
  MockHttpServletRequest req;
  People people = new People();


  @Before
  public void setup() {
    HibernateUtil.resetObjects();
    people = new People();
    HibernateUtil.getFactory().getCurrentSession().beginTransaction();
    HibernateUtil.getFactory().getCurrentSession().createSQLQuery("delete from Person")
        .executeUpdate();
    HibernateUtil.getDAOFact().getPersonDAO().makePersistent(people.prsA);
    HibernateUtil.getFactory().getCurrentSession().getTransaction().commit();;

    people = new People();
    auth = new AuthInterceptor();
    res = new MockHttpServletResponse();
    req = new MockHttpServletRequest();
  }

  @After
  public void cleanup() {
    HibernateUtil.resetObjects();
    TestRunner.resetObjects();
  }

  // Move this to the student tests
  @Test
  public void createStudent() {
    req.setServletPath("/api/prss");
    req.setMethod("POST");

    PersonController.postPerson(people.prsB, req, res);
    req.setServletPath("/api/ssns");
    req.setMethod("POST");
    SessionController.postSession(new Login(people.prsB.getUsername(), people.passB), req, res);

    req.setCookies(res.getCookies());
    assertTrue(auth.preHandle(req, res, null));

    Person b =
        PersonController.getPerson(Util.getFinalId((String) res.getHeader("Location")), req, res);

    assertEquals(people.prsB, b);
  }

  @Test
  public void loginCheck() {
    req.setServletPath("/api/prss");
    req.setMethod("GET");
    assertFalse(auth.preHandle(req, res, null));
  }

  // TODO break this test up into multiple tests
  @Test
  public void permissionTest() {


    req.setServletPath("/api/prss");
    req.setMethod("POST");
    assertTrue(auth.preHandle(req, res, null));
    PersonController.postPerson(people.prsC, req, res);
    int prsId = Util.getFinalId((String) res.getHeader("Location"));
    assertEquals(res.getStatus(), HttpStatus.OK.value());

    req.setServletPath("/api/prss");
    req.setMethod("POST");
    assertTrue(auth.preHandle(req, res, null));
    PersonController.postPerson(people.prsC, req, res);
    assertEquals(res.getStatus(), HttpStatus.BAD_REQUEST.value());

    req.setServletPath("/api/ssns");
    req.setMethod("POST");
    SessionController.postSession(new Login(people.prsC.getUsername(), people.passC), req, res);

    req.setCookies(res.getCookies());
    Person person;

    req.setServletPath("/api/prss");
    req.setMethod("GET");
    assertTrue(auth.preHandle(req, res, null));
    List<Person> list = PersonController.getAllPeople(req, res);
    assertEquals(res.getStatus(), HttpStatus.UNAUTHORIZED.value());
    assertNull(list);



    req.setServletPath("/api/prss/" + people.prsC.getId());
    req.setMethod("GET");
    Person p = PersonController.getPerson(prsId, req, res);
    assertEquals(res.getStatus(), HttpStatus.OK.value());
    assertEquals(people.prsC, p);

    req.setServletPath("/api/prss");
    req.setMethod("PUT");
    assertTrue(auth.preHandle(req, res, null));
    people.prsC.setUsername("newEmail@test.com");
    PersonController.putPerson(people.prsC, prsId, req, res);
    assertEquals(res.getStatus(), HttpStatus.UNAUTHORIZED.value());


    p = PersonController.getPerson(prsId, req, res);
    assertNotEquals(people.prsC.getUsername(), p.getUsername());

    req.setServletPath("/api/prss");
    req.setMethod("PUT");
    assertTrue(auth.preHandle(req, res, null));
    people.prsC.setUsername(p.getUsername());
    people.prsC.setFirstName("newName");
    PersonController.putPerson(people.prsC, prsId, req, res);
    assertEquals(res.getStatus(), HttpStatus.OK.value());

    p = PersonController.getPerson(prsId, req, res);
    assertEquals(people.prsC.getFirstName(), p.getFirstName());


  }


}
