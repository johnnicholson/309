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

/**This class represents the RoomType controller.
 * We created methods for each of the HTTPS verbs - GET, POST, PUT, and DELETE.
 * @author salonee and ryan
 * @since 2017-02-08
 */
@RestController
@RequestMapping(value = "/api/roomType")
public class RoomTypeController {

	/**This GET method gets the specified room type that the user is looking for.
	 * @param typeId - the ID of the room type in the database.
     * @param req - information about the session
     * @param res - the error responses
     * @return specified room type
     */
   @RequestMapping(value = "/{TypeId}", method = RequestMethod.GET)
   public static RoomType getRoomType(@PathVariable(value = "TypeId") int typeId, HttpServletRequest req, HttpServletResponse res) {
      RoomType r = new GetRoomType(typeId).run(req, res);
	  return r;
	}
   
   /**This GET method gets all the rooms in the database.
    * @param req - information about the session
    * @param res - the error responses
    * @return list of room types in the database representing all the room types in the university for the specified department.
    */
  @RequestMapping(value = "", method = RequestMethod.GET)
  public static List<RoomType> getAllRoomTypes(HttpServletRequest req, HttpServletResponse res) {
    List<RoomType> types = new GetAllRoomTypes().run(req, res);

    return types;
  }

  
  /**This POST method creates a new room type and puts it in the database.
   * @param type - new room type to be added to the database
   * @param req - information about the session
   * @param res - the error responses
   * @return typeId - the ID of the room type added.
   */
  @RequestMapping(value = "", method = RequestMethod.POST)
  public static Integer postRoomType(@RequestBody RoomType type, HttpServletRequest req,
      HttpServletResponse res) {
    Integer typeId = new PostRoomType(type).run(req, res);
    res.setHeader("Location", "roomType/" + typeId);
    return typeId;
  }
  
 
  /**This PUT method edits a room type in the database.
   * @param type - room type to be edited
   * @param typeId - the ID of the room type to be edited
   * @param req - information about the session
   * @param res - the error responses
   */
  @RequestMapping(value = "/{TypeId}", method = RequestMethod.PUT)
  public static void putRoomType(@Valid @RequestBody RoomType type,
      @PathVariable(value = "TypeId") int typeId, HttpServletRequest req, HttpServletResponse res) {
    new PutRoomType(type, typeId).run(req, res);
  }

  
  /**This DELETE method deletes the specified room type.
   * @param typeId - the ID of the room type in the database.
   * @param req - information about the session
   * @param res - the error responses
   */
  @RequestMapping(value = "/{TypeId}", method = RequestMethod.DELETE)
  public static void deleteRoomType(@PathVariable(value = "TypeId") int typeId, HttpServletRequest req,
      HttpServletResponse res) {
    new DeleteRoomType(typeId).run(req, res);
  }


}