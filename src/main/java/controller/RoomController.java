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

import model.Room;
import transactions.RoomTransactions.GetAllRooms;
import transactions.RoomTransactions.GetRoom;
import transactions.RoomTransactions.PostRoom;
import transactions.RoomTransactions.PutRoom;
import transactions.RoomTransactions.DeleteRoom;
/**This class represents the Room controller.
 * We created methods for each of the HTTPS verbs - GET, POST, PUT, and DELETE.
 * @author salonee and ryan
 * @since 2017-02-08
 */
@RestController
@RequestMapping(value = "/api/room")
public class RoomController {

  /**This GET method gets all the rooms in the database.
   * @param req - information about the session
   * @param res - the error responses
   * @return list of rooms in the database representing all the rooms in the university for the specified department.
   */
  @RequestMapping(value = "", method = RequestMethod.GET)
  public static List<Room> getAllRooms(HttpServletRequest req, HttpServletResponse res) {
    List<Room> room = new GetAllRooms().run(req, res);

    return room;
  }

  /**This POST method creates a new room and puts it in the database.
   * @param room - new room to be added to the database
   * @param req - information about the session
   * @param res - the error responses
   * @return roomId - ID of added room
   */
  @RequestMapping(value = "", method = RequestMethod.POST)
  public static Integer postRoom(@RequestBody Room room, HttpServletRequest req,
      HttpServletResponse res) {
    Integer roomId = new PostRoom(room).run(req, res);
    res.setHeader("Location", "room/" + roomId);
    return roomId;
  }

  /**This PUT method edits a room in the database.
   * @param room - room to be edited
   * @param roomId - the ID of the room in the database
   * @param req - information about the session
   * @param res - the error responses
   */
  @RequestMapping(value = "/{RoomId}", method = RequestMethod.PUT)
  public static void putRoom(@Valid @RequestBody Room room,
      @PathVariable(value = "RoomId") int roomId, HttpServletRequest req, HttpServletResponse res) {
    new PutRoom(room, roomId).run(req, res);
  }

  /**This GET method gets the specified room that the user is looking for.
   * @param roomId - the ID of the room in the database.
   * @param req - information about the session
   * @param res - the error responses
   * @return specified room
   */
  @RequestMapping(value = "/{RoomId}", method = RequestMethod.GET)
  public static Room getRoom(@PathVariable(value = "RoomId") int roomId, HttpServletRequest req,
      HttpServletResponse res) {
    Room r = new GetRoom(roomId).run(req, res);
    return r;
  }
  
  /**This DELETE method deletes the specified room.
   * @param roomId - the ID of the room in the database.
   * @param req - information about the session
   * @param res - the error responses
   */
  @RequestMapping(value = "/{RoomId}", method = RequestMethod.DELETE)
  public static void deleteRoom(@PathVariable(value = "RoomId") int roomId, HttpServletRequest req,
      HttpServletResponse res) {
    new DeleteRoom(roomId).run(req, res);
  }


}