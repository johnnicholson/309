package transactions;

import controller.RoomController;
import dao.RoomDAO;
import dao.RoomTypeDAO;
import hibernate.HibernateUtil;
import model.Room;
import model.RoomType;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

import org.hibernate.Hibernate;

import java.util.List;
/**This represents a RoomTransaction class that holds all the transactions (HTTPS verbs).
 * 
 * @author salonee and ryan
 * @since 2017-02-08
 */
public class RoomTransactions {

	/** This class represents how the GetAllRooms method in the Controller interacts with our database.
	 */
	public static class GetAllRooms extends Transaction<List<Room>> {
		/** The action method creates a DAO for rooms and retrieves all of them.
		 * @return - list of rooms in database if there is any, otherwise null.
		 */
		@Override
		public List<Room> action() {
			if (isAdmin()) {
				RoomDAO roomDAO = HibernateUtil.getDAOFact().getRoomDAO();
				List<Room> rooms = roomDAO.findAll();
				return rooms;
			} else {
				responseCode = HttpStatus.UNAUTHORIZED;
				return null;
			}
		}
	}
	
	/** This class represents how the GetRoom method in the Controller interacts with our database.
	 */
	public static class GetRoom extends Transaction<Room> {
		private Integer roomId;

		public GetRoom(Integer roomId) {
			this.roomId = roomId;
		}

		/** The action method creates a DAO for rooms and retrieves the specified one based on the id given in the constructor.
		 * @param roomId - specified in constructor; represents the id of the room to get.
		 * @return - specified room based on id, otherwise sets the response code. 
		 */
		@Override
		public Room action() {
			Room r = null;
			if (isAdmin()) {
				r = HibernateUtil.getDAOFact().getRoomDAO().findById(roomId);
				if (r != null) {
					Hibernate.initialize(r);
				} else {
					// Non admin entities don't need to know if that id exists
					if (isAdmin()) {
						responseCode = HttpStatus.NOT_FOUND;
					}
				}
			} else {
				responseCode = HttpStatus.UNAUTHORIZED;
			}
			return r;
		}

	}

	/** This class represents how the PostRoom method in the Controller interacts with our database.
	 */
	public static class PostRoom extends Transaction<Integer> {
		private Room room;

		public PostRoom(Room room) {
			this.room = room;
		}

		/** The action method adds a new room to the database.
		 * @param room - specified in constructor; represents the room to be added.
		 * @return - the id of the room added, otherwise sets the response code and returns null
		 */
		@Override
		public Integer action() {
			RoomDAO roomDAO = HibernateUtil.getDAOFact().getRoomDAO();
			if (null == roomDAO.findByRoomNumber(room.getRoomNumber())) {
				// && (prs.getRole() == Role.Student || isAdmin())) {
				roomDAO.makePersistent(room);
			} else {
				responseCode = HttpStatus.BAD_REQUEST;
				return null;
			}
			return room.getId();
		}

	}
	
	/** This class represents how the PutRoom method in the Controller interacts with our database.
	 */
	public static class PutRoom extends Transaction<Integer> {
		private Room room;
		private Integer id;

		public PutRoom(Room room, Integer id) {
			this.room = room;
			this.id = id;
		}

		/** The action method edits a room in the database.
		 * @param room - specified in constructor; represents the edited room.
		 * @param id - the id of the room to be changed.
		 * @return - null if successful. 
		 */
		@Override
		public Integer action() {
			RoomDAO roomDAO = HibernateUtil.getDAOFact().getRoomDAO();
			Room dbroom = roomDAO.findById(id);
			if (isAdmin()) {
				BeanUtils.copyProperties(room, dbroom, "id");
			} else {
				this.responseCode = HttpStatus.UNAUTHORIZED;
			}
			
			return null;
		}
	}

	/** This class represents how the DeleteRoom method in the Controller interacts with our database.
	 */
	public static class DeleteRoom extends Transaction<Integer> {

		private int rmID;

		public DeleteRoom(Integer roomID) {
			this.rmID = roomID;
		}

		/** The action method removes specified room from database.
		 * @param roomId - specified in constructor; represents the id of the room to remove.
		 * @return - null if successful. 
		 */
		@Override
		public Integer action() {
			Room room = null;
			RoomDAO roomDao = null;

			// Check permissions
			if (isAdmin()) {
				roomDao = HibernateUtil.getDAOFact().getRoomDAO();
				room = roomDao.findById(rmID);

				// Delete if found
				if (room != null) {
					roomDao.makeTransient(room);
				} else {
					responseCode = HttpStatus.NOT_FOUND;
				}
			} else {
				responseCode = HttpStatus.UNAUTHORIZED;
			}
			return null;
		}
	}
}
