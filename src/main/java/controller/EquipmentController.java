package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.Valid;
import model.Equipment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import transactions.EquipmentTransactions.DeleteEquipment;
import transactions.EquipmentTransactions.GetEquipment;
import transactions.EquipmentTransactions.GetEquipmentList;
import transactions.EquipmentTransactions.PostEquipment;
import transactions.EquipmentTransactions.PutEquipment;

@RestController
@RequestMapping(value = "/api/equip")
public class EquipmentController {

  @RequestMapping(value="/{equipID}", method = RequestMethod.GET)
  public static Equipment getEquipment(@PathVariable(value = "equipID")
      int equipID, HttpServletRequest req, HttpServletResponse res) {
    Equipment equipment = new GetEquipment(equipID).run(req, res);
    return equipment;
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public static Integer postEquipment(@RequestBody Equipment equipment,
      HttpServletRequest req, HttpServletResponse res) {
    Integer equipID = new PostEquipment(equipment).run(req, res);
    res.setHeader("Location", "equipID/" + equipID);
    return equipID;
  }

  @RequestMapping(value = "/equip", method = RequestMethod.GET)
  public static List<Equipment> getEquipmentList(HttpServletRequest req,
      HttpServletResponse res) {
    List<Equipment> equipments = new GetEquipmentList().run(req, res);
    return equipments;
  }

  @RequestMapping(value = "/{equipID}", method = RequestMethod.PUT)
  public static Integer putEquipment(@Valid @RequestBody Equipment equipment,
      @PathVariable(value = "equipID") int equipID,
      HttpServletRequest req, HttpServletResponse res) {
    new PutEquipment(equipment, equipID).run(req, res);
    return equipID;
  }

  @RequestMapping(value = "/{equipID}", method = RequestMethod.DELETE)
  public static void deleteEquipment(@PathVariable(value = "equipID") int equipID,
      HttpServletRequest req, HttpServletResponse res) {
    new DeleteEquipment(equipID).run(req, res);
  }
}
