package com.rahul.investopedia.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Getdefinition {
	public class SimpleThread extends Thread {
		public String url = "";

		public SimpleThread() {
		}
		
		@Override
		public void run() {
			Getdefinition.getdefinition(this.url);
		}
	};

	public static void main(String args[]) {

		ArrayList<String> url = new ArrayList<String>();
		url = geturl();
		System.out.println("size of url" + url.size());
		Getdefinition gd =  new Getdefinition();
		for (int i = 0; i < url.size(); i++) {
			SimpleThread simpleThread = gd.new SimpleThread();
			//simpleThread.url = "http://www.investopedia.com/terms/a/a-b-trust.asp";

			simpleThread.url = url.get(i);
			simpleThread.start();
			try {
				simpleThread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	static void getdefinition(String url) {
		try {
			String webContent = URLReader.readURL(url);			 
			webContent = HtmlManipulator.replaceHtmlEntities(webContent);			
			System.out.println(url);
			String pattern = String.format("(%s).*?(%s)",
					Pattern.quote("<!--node_tags-->"),
					Pattern.quote("<!-- .table-definition -->"));
			Matcher m = Pattern.compile(pattern, Pattern.DOTALL).matcher(
					webContent);
			while (m.find()) {
				String matchingvalue = m.group();
				String webdetail3 = null;
				try{
				webdetail3=m.group().split("center",2)[1].split("img",2)[1].split("src=",2)[1].split(">",2)[0].split("height",2)[0].replaceAll("\"","");
				System.out.println(webdetail3);
				}
				catch(Exception e)
				{
					webdetail3=null;
				}
				matchingvalue = matchingvalue.replaceAll(
						LoadUtilConstants.tagRegex, "\n");
				String title[] = matchingvalue.split("Definition of");
				String webtitle = title[0].split("�")[1].replaceAll("�", "");
				String webdetail[] = title[1].split("Investopedia explains");
				String webdetail1 = webdetail[0].split("\n", 2)[1];
				String webdetail2 = webdetail[1].split("\n", 2)[1];
				System.out.println("title is :" + webtitle + "\n" + webdetail1
						+ "\n" + webdetail2);
				 writedetails(webtitle,webdetail1,webdetail2,webdetail3,url);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> geturl() {
		Connection con = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		ArrayList<String> geturlarry = new ArrayList<String>();
		String sql = "SELECT url FROM terms WHERE definition IS NULL";
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

	static void writedetails(String webdetail, String webdetail1,
			String webdetail2, String webdetail3, String texturl) {
		Connection con = null;
		PreparedStatement statement = null;
		String insertqry = "UPDATE terms SET section=?,definition=?,`explain`=?, image=? where url=?";
		try {
			con = DBconnection.getConnection();
			statement = con.prepareStatement(insertqry);
			statement.setString(1, webdetail);
			statement.setString(2, webdetail1);
			statement.setString(3, webdetail2);
			statement.setString(4, webdetail3);
			statement.setString(5, texturl);
			System.out.println(webdetail + "" + webdetail1 + "" + webdetail2);
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
