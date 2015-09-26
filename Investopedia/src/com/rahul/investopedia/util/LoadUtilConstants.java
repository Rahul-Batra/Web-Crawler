package com.rahul.investopedia.util;

public interface LoadUtilConstants {

	
	public static final String regExp1 = "<[A-Za-z0-9;/:#'%=\\()_\"-.*+!@&?<>{}| ]{1,}>";

	public static final String regExp2 = "&[a-zA-Z]{1,};";

	public static final String regExp3 = "nbsp;";

	public static final String regExp4 = "&#[0-9]{1,};";

	public String scriptRegex = "<(script|style)[^>]*>[^<].*?</(script|style)>";
	
	public String tagRegex = "<[^>]*>";
	
	public String commentsRegex="<!--.*?-->";
}