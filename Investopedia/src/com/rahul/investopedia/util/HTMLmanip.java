package com.rahul.investopedia.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class HTMLmanip {
		
		public static void main(String[] args) {
			Connection con = null;
			PreparedStatement statement = null;
			ArrayList<String> getexp=new ArrayList<String>();		
			getexp=getexplain();
			for(int i=0;i<getexp.size();i++)
			{
				String webContent=HtmlManipulator.replaceHtmlEntities(getexp.get(i));
				String sql = "update terms set `explain`=? where `explain`=?";
				try {
					String getvalue=getexp.get(i);
					con = DBconnection.getConnection();
					statement = con.prepareStatement(sql);
					statement.setString(1, webContent);
					statement.setString(2, getvalue);
					System.out.println("new"+webContent+"old"+getexp.get(i));
					statement.execute();				
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		



}
	public static ArrayList<String> getdefinition() {
		Connection con = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		ArrayList<String> geturlarry = new ArrayList<String>();
		String sql = "SELECT definition FROM terms";
		try {
			con = DBconnection.getConnection();
			statement = con.prepareStatement(sql);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				geturlarry.add(resultSet.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return geturlarry;
	}

	public static ArrayList<String> getexplain() {
		Connection con = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		ArrayList<String> geturlarry = new ArrayList<String>();
		String sql = "SELECT `explain` FROM terms";
		try {
			con = DBconnection.getConnection();
			statement = con.prepareStatement(sql);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				geturlarry.add(resultSet.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return geturlarry;
	}
	
	
	
	
}
