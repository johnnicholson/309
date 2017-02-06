package Equipment;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.Session;
import controller.EquipmentController;
import dao.EquipmentDAO;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
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

  static EquipmentDAO equipDAO;

  MockHttpServletResponse res;
  MockHttpServletRequest req;

  @BeforeClass
  public static void topSetup() {
    DAOFactory fact = TestRunner.getMockFact();
    equipDAO = mock(EquipmentDAO.class);
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
    reset(equipDAO);
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

  @Test
  public void EquipmentDeleteTest() {
    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Admin;
    when(req.getAttribute(Session.ATTRIBUTE_NAME)).thenReturn(mockSession);

    Equipment mockequip = mock(Equipment.class);
    when(equipDAO.findById(1)).thenReturn(mockequip);
    EquipmentController.deleteEquipment(1, req, res);
    verify(equipDAO, times(1)).makeTransient(mockequip);
  }

  @Test
  public void EquipmentPutTest() {
    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Admin;
    when(req.getAttribute(Session.ATTRIBUTE_NAME)).thenReturn(mockSession);

    Equipment equipment = new Equipment();
    Equipment mockequip = mock(Equipment.class);
    equipment.setName("projector");
    equipment.setDescription("projects stuff");
    when(equipDAO.findById(1)).thenReturn(mockequip);
    EquipmentController.putEquipment(equipment, 1, req, res);

    verify(equipDAO, times(1)).findById(1);
    verify(mockequip, times(0)).setId(1);
    verify(mockequip, times(1)).setName("projector");
    verify(mockequip, times(1)).setDescription("projects stuff");
  }

  @Test
  public void EquipmentGetListTest() {
    app.Session mockSession = mock(app.Session.class);
    mockSession.role = Role.Staff;
    when(req.getAttribute(Session.ATTRIBUTE_NAME)).thenReturn(mockSession);

    ArrayList<Equipment> equipList = new ArrayList<Equipment>();
    Equipment mockEquip1 = mock(Equipment.class);
    Equipment mockEquip2 = mock(Equipment.class);
    equipList.add(mockEquip1);
    equipList.add(mockEquip2);
    when(equipDAO.findAll()).thenReturn(equipList);
    List list  = EquipmentController.getEquipmentList(req, res);

    verify(equipDAO, times(1)).findAll();
    assertEquals(equipList, list);




  }

}
