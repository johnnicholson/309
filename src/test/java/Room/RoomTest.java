package Room;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import Home.TestRunner;
import Person.People;
import controller.RoomController;
import dao.DAOFactory;
import dao.EquipmentDAO;
import dao.PersonDAO;
import dao.RoomDAO;
import hibernate.HibernateUtil;
import model.Equipment;
import model.Person.Role;
import model.Room;
import model.RoomType;

public class RoomTest {
  static PersonDAO prsDAO;
  static RoomDAO rmDAO;
  static EquipmentDAO eqDAO;
  
  MockHttpServletResponse res;
  MockHttpServletRequest req;
  People people;

  @BeforeClass
  public static void topSetup() {
    DAOFactory fact = TestRunner.getMockFact();
    prsDAO = mock(PersonDAO.class);
    rmDAO = mock(RoomDAO.class);
    eqDAO = mock(EquipmentDAO.class);
    when(fact.getPersonDAO()).thenReturn(prsDAO);
    when(fact.getRoomDAO()).thenReturn(rmDAO);
    when(fact.getEquipmentDAO()).thenReturn(eqDAO);

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
    reset(rmDAO);
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
  public void RoomFailGetTest() {

	    app.Session mockSession = mock(app.Session.class);
	    mockSession.role = Role.Student;
	    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);

	    when(rmDAO.findById(1)).thenReturn(null);
	    Room rm = RoomController.getRoom(1, req, res);
	    verify(rmDAO, times(0)).findById(1);
	    assertEquals(rm, null);
	    
  }
  
  @Test
  public void RoomPostTest() {
  
	  app.Session mockSession = mock(app.Session.class);
	  mockSession.role = Role.Admin;
	  when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);
	  
	  RoomType roomType = new RoomType("Lecture Hall");
//	  List<Equipment> listOfEquipment = (List<Equipment>) new Equipment();
	  Room rm = new Room(100, "14-255", roomType); //, listOfEquipment);	// NEED TO FIX THIS
	  RoomController.postRoom(rm, req, res);
	  verify(rmDAO, times(1)).findByRoomNumber("14-255");
	  verify(rmDAO).makePersistent(rm);
	  
  }
  
  @Test
  public void RoomPutTest() {
    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Admin;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);
    
    Room mockrm = mock(Room.class);
    when(rmDAO.findById(1)).thenReturn(mockrm);
    Room otherRM = new Room();
//    List<Equipment> listOfEquipment = (List<Equipment>) new Equipment();
    otherRM.setCapacity(50);
    otherRM.setRoomNumber("14-255");
//    otherRM.setEquipmentList(listOfEquipment);
    RoomType rm = mock(RoomType.class);
    otherRM.setRoomType(rm);
    
    RoomController.putRoom(otherRM, 1, req, res);
    verify(mockrm).setCapacity(50);
    verify(mockrm).setRoomNumber("14-255");
    verify(mockrm).setRoomType(rm);
  }

  @Test
  public void RoomDeleteTest() {

		app.Session mockSession = mock(app.Session.class);
		mockSession.role = Role.Admin;
		when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);

		Room mockRoom = mock(Room.class);
		when(rmDAO.findById(1)).thenReturn(mockRoom);
		RoomController.deleteRoom(1, req, res);
		verify(rmDAO, times(1)).makeTransient(mockRoom);
	}
}
