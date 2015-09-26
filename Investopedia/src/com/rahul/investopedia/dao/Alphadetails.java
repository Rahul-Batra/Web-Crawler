package com.rahul.investopedia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.rahul.investopedia.util.AlphaDTO;
import com.rahul.investopedia.util.DBconnection;

public class Alphadetails {

	public static void getdetails() {
		Connection con = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		PreparedStatement statement1 = null;
		String sql = "SELECT * FROM alpha";
		String insertqry = "Insert into alphaurl(url) values(?)";
		try {
			con = DBconnection.getConnection();
			statement = con.prepareStatement(sql);
			statement1 = con.prepareStatement(insertqry);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				AlphaDTO alphadto = new AlphaDTO();
				alphadto.setLetter(resultSet.getString(1));
				alphadto.setCount(Integer.parseInt(resultSet.getString(2)));
				
				System.out.println(alphadto.getLetter() + " : " + alphadto.getCount());
				for (int i = 0; i <= alphadto.getCount(); i++) {
					String seturl = "http://www.investopedia.com/terms/"+ alphadto.getLetter() + "/?page=" + i;
					statement1.setString(1, seturl);
					statement1.execute();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
