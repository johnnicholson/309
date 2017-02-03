package RoomType;

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
import controller.RoomTypeController;
import dao.RoomTypeDAO;
import dao.DAOFactory;
import dao.PersonDAO;
import hibernate.HibernateUtil;
import model.RoomType;
import model.Person.Role;

public class RoomTypeTest {
  static PersonDAO prsDAO;
  static RoomTypeDAO rtDAO;
  
  MockHttpServletResponse res;
  MockHttpServletRequest req;
  People people;

  @BeforeClass
  public static void topSetup() {
    DAOFactory fact = TestRunner.getMockFact();
    prsDAO = mock(PersonDAO.class);
    rtDAO = mock(RoomTypeDAO.class);
    when(fact.getPersonDAO()).thenReturn(prsDAO);
    when(fact.getRoomTypeDAO()).thenReturn(rtDAO);

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
  public void RoomTypeGetTest() {

    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Admin;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);

    RoomType mockrt = mock(RoomType.class);
    when(rtDAO.findById(1)).thenReturn(mockrt);
    RoomType rt = RoomTypeController.getRoomType(1, req, res);
    verify(rtDAO, times(1)).findById(1);
    assertEquals(rt, mockrt);
    
  }
  
  @Test
  public void RoomTypePostTest() {
  
	  app.Session mockSession = mock(app.Session.class);
	  mockSession.role = Role.Admin;
	  when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);
	  
	  RoomType mockrt = mock(RoomType.class);
	  when(rtDAO.findById(1)).thenReturn(mockrt);
	  RoomType rt = new RoomType("Lecture Hall");
	  RoomTypeController.postRoomType(rt, req, res);
	  verify(rtDAO, times(1)).findById(1);
	  RoomType rt2 = RoomTypeController.getRoomType(1, req, res);
	  verify(rtDAO, times(2)).findById(1);
	  assertEquals(rt2, mockrt);
	  
  }

}
