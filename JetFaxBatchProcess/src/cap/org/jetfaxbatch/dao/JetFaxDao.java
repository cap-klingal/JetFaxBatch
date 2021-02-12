package cap.org.jetfaxbatch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cap.org.jetfaxbatch.util.CAPProperties;
import cap.org.jetfaxbatch.util.EbsConnection;
import cap.org.jetfaxbatch.util.SQLStatements;

public class JetFaxDao {
	private Connection _conn = null;
	private SQLStatements _sqlstatements = null;
	CAPProperties _appProp;

	public JetFaxDao() {
		_conn = EbsConnection.getEbsConnection().getConnection();
		_sqlstatements = new SQLStatements();
		_appProp = new CAPProperties();
	}

	public List<String> getProductsDropdown(List<Integer> yearsDropdown) {
		PreparedStatement psmt = null;
		ResultSet resultSet = null;

		String productname = "";
		List<String> productList = new ArrayList<String>();
		String groupName = _appProp.getProperty("jetfax.productgroup");

		if (psmt == null)
			try {
				psmt = _conn.prepareStatement(_sqlstatements.PRODUCTS_DROPDOWN);
				for (Integer year : yearsDropdown) {
					psmt.clearParameters();
					psmt.setInt(1, year);
					psmt.setString(2, groupName);
					psmt.setInt(3, year);
					psmt.setInt(4, year);
					psmt.setString(5, groupName);
					psmt.setInt(6, year);
					psmt.setInt(7, year);
					psmt.setString(8, groupName);
					psmt.setInt(9, year);

					resultSet = psmt.executeQuery();

					while (resultSet.next()) {
						productname = resultSet.getString("product");
						productList.add(productname);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (resultSet != null) {
						resultSet.close();
					}
					if (psmt != null) {
						psmt.close();
					}
				} catch (SQLException sqlexp) {
					sqlexp.printStackTrace();
				}
			}

//		System.out.print("AllProductsList:" + productList.toString());

		return productList;
	}

	public int updateJetFaxFlag(String productId,String mailingName) {
		int executeUpdate=0;
		Statement stmt = null;
			try {
				stmt = _conn.createStatement();
				String sql="update apps.pa_proj_elements set attribute3 = 'Y' where project_id='"+productId+"' AND name='"+mailingName.trim()+"'";
				executeUpdate = stmt.executeUpdate(sql);
				_conn.commit();
				System.out.println("Executed Query"+sql);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return executeUpdate;
	}

	public int getFinalMailingsList(Set<String> mailingNames) {
		PreparedStatement psmt = null;
		String productId = "";
		int rows=0;
		Map<String, String> finalMailingNamesList = new HashMap<String, String>();
		if (psmt == null)
			try {
				for (String productname : mailingNames) {
					psmt = _conn.prepareStatement(_sqlstatements.getProductIds);
					psmt.setString(1, productname);

					ResultSet resultSet = psmt.executeQuery();
					while (resultSet.next()) {
						productId = resultSet.getString("project_id");
						finalMailingNamesList.put(productId, productname);
					}
				}
				for(Map.Entry<String, String> entry:finalMailingNamesList.entrySet()) {
				updateJetFaxFlag(entry.getKey().trim(),entry.getValue().trim());
						rows++;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		System.out.println("UpdatedMailingList::" + finalMailingNamesList);
		return rows;

	}
	
}
