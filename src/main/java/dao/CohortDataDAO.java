package dao;

import model.CohortData;
import model.CohortDataFilter;
import model.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jnicho on 2/4/2017.
 */
public class CohortDataDAO extends GenericHibernateDAO<CohortData> {

    //TODO make this not super vulnerable to SQL injection
    @Nullable
    public CohortData findByFilters(@NotNull CohortDataFilter filter) {
        getSession().createSQLQuery("Select * from CohortData where");
        return null;
    }
}
