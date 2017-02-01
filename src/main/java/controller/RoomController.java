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

@RestController
@RequestMapping(value = "/api/room")
public class RoomController {


  @RequestMapping(value = "", method = RequestMethod.GET)
  public static List<Room> getAllRooms(HttpServletRequest req, HttpServletResponse res) {
    List<Room> room = new GetAllRooms().run(req, res);

    return room;
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public static void postRoom(@RequestBody Room room, HttpServletRequest req,
      HttpServletResponse res) {
    Integer roomId = new PostRoom(room).run(req, res);
    res.setHeader("Location", "room/" + roomId);
    return;
  }

  @RequestMapping(value = "/{RoomId}", method = RequestMethod.PUT)
  public static void putRoom(@Valid @RequestBody Room room,
      @PathVariable(value = "RoomId") int roomId, HttpServletRequest req, HttpServletResponse res) {
    new PutRoom(room, roomId).run(req, res);
    return;
  }

  @RequestMapping(value = "/{RoomId}", method = RequestMethod.GET)
  public static Room getRoom(@PathVariable(value = "RoomId") int roomId, HttpServletRequest req,
      HttpServletResponse res) {
    Room r = new GetRoom(roomId).run(req, res);
    return r;
  }


}