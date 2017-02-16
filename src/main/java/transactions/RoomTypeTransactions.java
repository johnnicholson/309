package transactions;

import dao.RoomTypeDAO;
import hibernate.HibernateUtil;
import model.RoomType;

import org.springframework.http.HttpStatus;
import org.springframework.beans.BeanUtils;

import org.hibernate.Hibernate;

import java.util.List;
/**This represents a RoomTypeTransaction class that holds all the transactions (HTTPS verbs).
 * 
 * @author salonee and ryan
 * @since 2017-02-08
 */
public class RoomTypeTransactions {

	/** This class represents how the GetAllRoomTypes method in the Controller interacts with our database.
	 */
	public static class GetAllRoomTypes extends Transaction<List<RoomType>> {

		/** The action method creates a DAO for room types and retrieves all of them.
		 * @return - list of room types in database if there is any, otherwise null.
		 */
		@Override
		public List<RoomType> action() {
			if (isAdmin()) {
				RoomTypeDAO typeDAO = HibernateUtil.getDAOFact().getRoomTypeDAO();
				List<RoomType> types = typeDAO.findAll();
				return types;
			} else {
				responseCode = HttpStatus.UNAUTHORIZED;
				return null;
			}
		}
	}

	public static class GetRoomType extends Transaction<RoomType> {
		private int typeId;

		public GetRoomType(int typeId) {
			this.typeId = typeId;
		}

		@Override
		public RoomType action() {
			RoomType r = null;
			if (isAdmin()) {
				r = HibernateUtil.getDAOFact().getRoomTypeDAO().findById(typeId);
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
	public static class PostRoomType extends Transaction<Integer> {
		private RoomType type;

		public PostRoomType(RoomType type) {
			this.type = type;
		}

		@Override
		public Integer action() {
			RoomTypeDAO typeDAO = HibernateUtil.getDAOFact().getRoomTypeDAO();
			if (null == typeDAO.findByRoomType(type.getRoomType())) {
				// && (prs.getRole() == Role.Student || isAdmin())) {
				typeDAO.makePersistent(type);
			} else {
				responseCode = HttpStatus.BAD_REQUEST;
				return null;
			}
			return type.getId();
		}

	}

	public static class PutRoomType extends Transaction<Integer> {
		private RoomType rmtype;
		private Integer id;

		public PutRoomType(RoomType roomtype, Integer id) {
			this.rmtype = roomtype;
			this.id = id;
		}

		@Override
		public Integer action() {
			RoomTypeDAO roomTypeDAO = HibernateUtil.getDAOFact().getRoomTypeDAO();
			RoomType dbroomtype = roomTypeDAO.findById(id);
			if (isAdmin()) {
				BeanUtils.copyProperties(rmtype, dbroomtype, "id");
			} else {
				this.responseCode = HttpStatus.UNAUTHORIZED;
			}
			return null;
		}

	}

	public static class DeleteRoomType extends Transaction<Integer> {

		private int rmTypeID;

		public DeleteRoomType(int roomTypeID) {
			this.rmTypeID = roomTypeID;
		}

		@Override
		public Integer action() {
			RoomType rmType = null;
			RoomTypeDAO rmTypeDao = null;

			// Check permissions
			if (isAdmin()) {
				rmTypeDao = HibernateUtil.getDAOFact().getRoomTypeDAO();
				rmType = rmTypeDao.findById(rmTypeID);

				// Delete if found
				if (rmType != null) {
					rmTypeDao.makeTransient(rmType);
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
