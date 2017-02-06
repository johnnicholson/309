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

public class RoomTransactions {

	public static class GetAllRooms extends Transaction<List<Room>> {

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

	public static class GetRoom extends Transaction<Room> {
		private int roomId;

		public GetRoom(int roomId) {
			this.roomId = roomId;
		}

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

	// does the transaction still stay as an Integer type because we are pushing
	// them to the database by their id?
	public static class PostRoom extends Transaction<Integer> {
		private Room room;

		public PostRoom(Room room) {
			this.room = room;
		}

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

	public static class PutRoom extends Transaction<Integer> {
		private Room room;
		private Integer id;

		public PutRoom(Room room, Integer id) {
			this.room = room;
			this.id = id;
		}

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

	public static class DeleteRoom extends Transaction<Integer> {

		private int rmID;

		public DeleteRoom(int roomID) {
			this.rmID = roomID;
		}

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
