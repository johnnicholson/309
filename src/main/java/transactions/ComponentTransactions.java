package transactions;

import dao.ComponentDAO;
import dao.CourseDAO;
import hibernate.HibernateUtil;
import model.Component;
import model.Course;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;

/**
 * Created by jnicho on 2/4/2017.
 */
public class ComponentTransactions {

    public static class PostComponent extends Transaction<Integer> {
        private int crsId;
        private Component cmp;

        public PostComponent(Component cmp, int crsId) {
            this.crsId = crsId;
            this.cmp = cmp;
        }

        @Override
        public Integer action() {
            ComponentDAO cmpDAO = HibernateUtil.getDAOFact().getComponentDAO();
            CourseDAO crsDAO = HibernateUtil.getDAOFact().getCourseDAO();
            Course crs = crsDAO.findById(crsId);

            if (isAdmin()) {
                if (crs == null) {
                    responseCode = HttpStatus.NOT_FOUND;
                } else {
                    cmpDAO.makePersistent(cmp);
                    crs.addComponent(cmp);
                }

            } else {
                responseCode = HttpStatus.UNAUTHORIZED;
            }
            return cmp.getId();
        }
    }

    public static class GetComponent extends Transaction<Component> {
        private int cmpId;

        public GetComponent(int cmpId) {
            this.cmpId = cmpId;
        }

        @Override public Component action() {
            ComponentDAO cmpDAO = HibernateUtil.getDAOFact().getComponentDAO();

            Component cmp = cmpDAO.findById(cmpId);
            if (isStaff()) {
                if (cmp != null) {
                    Hibernate.initialize(cmp);
                } else {
                    responseCode = HttpStatus.NOT_FOUND;
                }
            } else {
                responseCode = HttpStatus.UNAUTHORIZED;
            }
            return cmp;
        }
    }
}
