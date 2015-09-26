package com.rahul.investopedia.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rahul.investopedia.dao.Alphadetails;

public class WebParser {

	public static void main(String[] args) {
		Alphadetails.getdetails();
		ArrayList<AlphaurlDTO> url=new ArrayList<AlphaurlDTO>();
		url = geturl();
		System.out.println("size of url is"+url.size());
		for(int i=0;i<url.size();i++)
		processPage(url.get(i).getUrl());
	}
	
	public static ArrayList<AlphaurlDTO> geturl()
	{
		Connection con = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		ArrayList<AlphaurlDTO> geturlarry=new ArrayList<AlphaurlDTO>();
		String sql = "SELECT * FROM alphaurl";
		try {
			con = DBconnection.getConnection();
			statement = con.prepareStatement(sql);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				AlphaurlDTO Geturl=new AlphaurlDTO();
				Geturl.setUrl(resultSet.getString(1));
				Geturl.setStatus(Integer.parseInt(resultSet.getString(2)));
				geturlarry.add(Geturl);
			}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		return geturlarry;
	}
	
	
	public static void processPage(String url) {
		try {
			System.out.println("url is"+url);
			String webContent = URLReader.readURL(url);
			String pattern = String.format("(%s).*?(%s)",Pattern.quote("<!-- .alphabet -->"),Pattern.quote("<!-- ad_sponsorlinks -->"));
			Matcher m = Pattern.compile(pattern, Pattern.DOTALL).matcher(
					webContent);
			int i = 1;
			while (m.find()) {
				String matchingvalue = m.group();
				Pattern pattrn = Pattern.compile("<a\\b[^>]*href=\"[^>]*>(.*?)</a>");
				Matcher match = pattrn.matcher(matchingvalue);
				while (match.find()) {
					String matchlist = match.group();
					matchlist = matchlist.replaceAll(LoadUtilConstants.regExp2,
							"");
					matchlist = matchlist.replaceAll(LoadUtilConstants.scriptRegex, "");
					matchlist = matchlist.replaceAll(LoadUtilConstants.commentsRegex, "");
					if (!matchlist.contains(".jpg")) {
						String title=matchlist.split("\"")[2].split("</")[0].replaceAll(">", "").trim();
						String titleURL="http://www.investopedia.com"+matchlist.split("\"")[1];
						System.out.println(i++ + " : title :" + title+ "\t url :"+ titleURL);
						writedetails(title,titleURL);
						
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void writedetails(String title,String titleURL)
	{
		Connection con = null;
		PreparedStatement statement = null;
		String insertqry = "Insert into terms(title,url) values(?,?)";
		try{
		con = DBconnection.getConnection();
		statement = con.prepareStatement(insertqry);
		statement.setString(1, title);
		statement.setString(2, titleURL);
		System.out.println(title+""+titleURL);
		statement.execute();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}		
	
}
