package Room;

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
import controller.RoomController;
import dao.RoomDAO;
import dao.DAOFactory;
import dao.PersonDAO;
import hibernate.HibernateUtil;
import model.Room;
import model.Person.Role;

public class RoomTest {
  static PersonDAO prsDAO;
  static RoomDAO rmDAO;
  
  MockHttpServletResponse res;
  MockHttpServletRequest req;
  People people;

  @BeforeClass
  public static void topSetup() {
    DAOFactory fact = TestRunner.getMockFact();
    prsDAO = mock(PersonDAO.class);
    rmDAO = mock(RoomDAO.class);
    when(fact.getPersonDAO()).thenReturn(prsDAO);
    when(fact.getRoomDAO()).thenReturn(rmDAO);

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
  public void RoomGetTest() {

    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Admin;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);

    Room mockrm = mock(Room.class);
    when(rmDAO.findById(1)).thenReturn(mockrm);
    Room rm = RoomController.getRoom(1, req, res);
    verify(rmDAO, times(1)).findById(1);
    assertEquals(rm, mockrm);
    
  }
  
  @Test
  public void RoomPostTest() {
  
	  app.Session mockSession = mock(app.Session.class);
	  mockSession.role = Role.Admin;
	  when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);
	  
	  Room mockrm = mock(Room.class);
	  when(rmDAO.findById(1)).thenReturn(mockrm);
	  Room rm = new Room(100, "14-255","Lecture Hall");
	  RoomController.postRoom(rm, req, res);
	  verify(rmDAO, times(1)).findById(1);
	  Room rm2 = RoomController.getRoom(1, req, res);
	  verify(rmDAO, times(2)).findById(1);
	  assertEquals(rm2, mockrm);
	  
  }

}
