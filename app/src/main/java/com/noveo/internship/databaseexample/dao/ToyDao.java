package com.noveo.internship.databaseexample.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.noveo.internship.databaseexample.db.ContentDescriptor;
import com.noveo.internship.databaseexample.model.Toy;

import java.sql.SQLException;
import java.util.List;


public class ToyDao extends BaseDaoImpl<Toy, Integer> {
    public ToyDao(ConnectionSource connectionSource, Class<Toy> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Toy> getAllToys() throws SQLException {
        return this.queryForAll();
    }

    public void deleteLastRecord() throws SQLException {
        QueryBuilder<Toy, Integer> qBuilder = queryBuilder();
        qBuilder.orderBy(ContentDescriptor.Toys.Cols.ID, false);
        qBuilder.limit(1L);

        Toy toy = queryForFirst(qBuilder.prepare());

        if (toy != null) {
            DeleteBuilder<Toy, Integer> deleteBuilder = deleteBuilder();
            deleteBuilder.where().eq(ContentDescriptor.Toys.Cols.ID, toy.getID());
            deleteBuilder.delete();
        }
    }
}
