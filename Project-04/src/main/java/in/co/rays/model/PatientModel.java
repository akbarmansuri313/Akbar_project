package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.rays.bean.PatientBean;
import in.co.rays.bean.UserBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.util.JDBCDataSource;

public class PatientModel {

	public int nextPk() {

		Connection conn = null;

		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(ID) from st_patient");

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pk + 1;
	}

	public long add(PatientBean bean) {

		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_patient values(?,?,?,?,?,?,?,?,?)");
			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getMobileNo());
			
			pstmt.setDate(4, new java.sql.Date(new Date().getTime()));
			pstmt.setString(5, bean.getDisease());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());

			int i = pstmt.executeUpdate();
			conn.commit();

			System.out.println("Data Insert" + i);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return pk;

	}

	public void update(PatientBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("update st_patient set name = ?, mobileNo = ?, date_of_visit = ?, "
							+ "disease = ?, created_by = ?, modified_by = ?, "
							+ "created_datetime = ?, modified_datetime = ? where id = ? ");

			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getMobileNo());
			pstmt.setDate(3, new java.sql.Date(bean.getdate_of_visit().getTime()));
			pstmt.setString(4, bean.getDisease());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getId());
			int i = pstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception : Exception Update User" + e2.getMessage());
			}
			throw new DuplicateRecordException("Exception : Exception in user" + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(PatientBean bean) throws ApplicationException {
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_patient where id = ?");
			pstmt.setLong(1, bean.getId());

			int i = pstmt.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in delete data" + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public List list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List search(PatientBean bean, int pageNo, int pageSize) throws ApplicationException {
		Connection conn = null;
		List list = new ArrayList();
		try {
			conn = JDBCDataSource.getConnection();

			StringBuffer sql = new StringBuffer("select * from st_patient where 1=1");

			if (bean != null) {
				if (bean.getName() != null && bean.getName().length() > 0) {
					sql.append(" and name like '" + bean.getName() + "%'");
				}
				if (bean.getId() > 0) {
					sql.append(" and Id like'" + bean.getId() + "%'");
				}

				if (bean.getDisease() != null && bean.getDisease().length()> 0) {
					sql.append(" and disease like '" + bean.getDisease() + "%'");

				}
				if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
					sql.append(" and mobileNo like '" + bean.getMobileNo() + "%'");
				}

				if (pageSize > 0) {
					pageNo = (pageNo - 1) * (pageSize);
					sql.append(" Limit " + pageNo + "," + pageSize);
				}
			}

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new PatientBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setMobileNo(rs.getString(3));
				bean.setdate_of_visit(rs.getDate(4));
				bean.setDisease(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				list.add(bean);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception In Search " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	public PatientBean findByPk(long id) throws ApplicationException {
		Connection conn = null;
		PatientBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_patient where id  = ?");
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new PatientBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setMobileNo(rs.getString(3));
				bean.setdate_of_visit(rs.getDate(4));
				bean.setDisease(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception :Exception in find By Pk" + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

}
