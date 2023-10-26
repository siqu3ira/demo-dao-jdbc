package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }
    @Override
    public void insert(Department dep) {

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(
                    "INSERT INTO department " +
                        "(Name) "
                    +   "VALUES (?) ",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, dep.getName());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    dep.setId(id);
                }

                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error! No rows affected");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeSatement(ps);
        }
    }

    @Override
    public void update(Department dep) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(
                    "UPDATE department " +
                        "SET Name = ? "
                      + "WHERE Id = ? ",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, dep.getName());
            ps.setInt(2, dep.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeSatement(ps);
        }
    }

    @Override
    public void deleteById(Integer id) {

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM department WHERE Id = ?");

            ps.setInt(1, id);

            var rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("Id does not exist in the database");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeSatement(ps);
        }
    }

    @Override
    public Department findById(Integer id) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(
                    "SELECT * "
                    + "FROM department "
                    + "WHERE Id = ? ");

            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                var dep = instatiateDepartment(rs);

                return dep;
            }

            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeSatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(
                    "SELECT * "
                            + "FROM department "
                            + "ORDER BY Name");

            rs = ps.executeQuery();
            List<Department> list = new ArrayList<>();
            while (rs.next()) {

                Department dep = instatiateDepartment(rs);

                list.add(dep);
            }

            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeSatement(ps);
            DB.closeResultSet(rs);
        }
    }

    private Department instatiateDepartment(ResultSet rs) throws SQLException {
        var dep = new Department();
        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));

        return dep;
    }
}
