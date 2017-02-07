package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import model.RoomType;
import transactions.RoomTypeTransactions.GetAllRoomTypes;
import transactions.RoomTypeTransactions.GetRoomType;
import transactions.RoomTypeTransactions.PostRoomType;
import transactions.RoomTypeTransactions.PutRoomType;
import transactions.RoomTypeTransactions.DeleteRoomType;

@RestController
@RequestMapping(value = "/api/roomType")
public class RoomTypeController {


   @RequestMapping(value = "/{TypeId}", method = RequestMethod.GET)
   public static RoomType getRoomType(@PathVariable(value = "TypeId") int typeId, HttpServletRequest req, HttpServletResponse res) {
      RoomType r = new GetRoomType(typeId).run(req, res);
	  return r;
	}
   
  @RequestMapping(value = "", method = RequestMethod.GET)
  public static List<RoomType> getAllRoomTypes(HttpServletRequest req, HttpServletResponse res) {
    List<RoomType> types = new GetAllRoomTypes().run(req, res);

    return types;
  }

  
  @RequestMapping(value = "", method = RequestMethod.POST)
  public static Integer postRoomType(@RequestBody RoomType type, HttpServletRequest req,
      HttpServletResponse res) {
    Integer typeId = new PostRoomType(type).run(req, res);
    res.setHeader("Location", "roomType/" + typeId);
    return typeId;
  }
  
 

  @RequestMapping(value = "/{TypeId}", method = RequestMethod.PUT)
  public static void putRoom(@Valid @RequestBody RoomType type,
      @PathVariable(value = "TypeId") int typeId, HttpServletRequest req, HttpServletResponse res) {
    new PutRoomType(type, typeId).run(req, res);
    return;
  }

  
  
  @RequestMapping(value = "/{TypeId}", method = RequestMethod.DELETE)
  public static void deleteRoomType(@PathVariable(value = "TypeId") int typeId, HttpServletRequest req,
      HttpServletResponse res) {
    new DeleteRoomType(typeId).run(req, res);
  }


}