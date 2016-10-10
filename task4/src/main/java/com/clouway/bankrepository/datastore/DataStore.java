package com.clouway.bankrepository.datastore;

import com.clouway.bankrepository.connectionprovider.core.Provider;
import com.google.common.collect.Lists;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class DataStore {
    private Provider<Connection> provider;

    public DataStore(Provider<Connection> provider) {
        this.provider = provider;
    }

    public void update(String query, Object... objects) {
        Connection connection = provider.get();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            fillStatement(statement, objects);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> fetchRows(String query, RowFetcher<T> rowFetcher) {
        List<T> list = Lists.newArrayList();
        Connection connection = provider.get();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                T row = rowFetcher.fetchRow(resultSet);
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void fillStatement(PreparedStatement statement, Object[] objects) throws SQLException {
        for (int i = 0; i < objects.length; i++) {
            statement.setObject(i + 1, objects[i]);
        }
    }
}
