package Equipment;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.Session;
import controller.EquipmentController;
import dao.EquipmentDAO;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import Home.TestRunner;
import Person.People;
import dao.DAOFactory;
import dao.PersonDAO;
import hibernate.HibernateUtil;
import model.Person.Role;
import model.Equipment;


public class EquipmentTest {

  static PersonDAO prsDAO;
  static EquipmentDAO equipDAO;

  MockHttpServletResponse res;
  MockHttpServletRequest req;
  People people;

  @BeforeClass
  public static void topSetup() {
    DAOFactory fact = TestRunner.getMockFact();
    prsDAO = mock(PersonDAO.class);
    equipDAO = mock(EquipmentDAO.class);
    when(fact.getPersonDAO()).thenReturn(prsDAO);
    when(fact.getEquipmentDAO()).thenReturn(equipDAO);

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
  public void EquipmentGetTest() {
    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Staff;
    when(req.getAttribute(app.Session.ATTRIBUTE_NAME)).thenReturn(mockSession);

    Equipment mockequip = mock(Equipment.class);
    when(equipDAO.findById(1)).thenReturn(mockequip);
    Equipment equipment = EquipmentController.getEquipment(1, req, res);
    verify(equipDAO, times(1)).findById(1);
    assertEquals(equipment, mockequip);
  }

  @Test
  public void EquipmentPostTest() {
    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Admin;
    when(req.getAttribute(Session.ATTRIBUTE_NAME)).thenReturn(mockSession);

    Equipment mockequip = mock(Equipment.class);
    when(mockequip.getId()).thenReturn(1);

    Equipment equipment = new Equipment();
    when(equipDAO.makePersistent(equipment)).thenReturn(mockequip);
    Integer e = EquipmentController.postEquipment(mockequip, req, res);
    Integer i = 1;
    //expected, actual
    assertEquals(i, e);
  }

}
