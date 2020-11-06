package com.kero.security.lang;

public class KsdlSpeaker {

	public static String INDENT = "  ";

	private static KsdlSpeaker INSTANCE = new KsdlSpeaker();

	public String addIndentTo(String text) {
		
		text = KsdlSpeaker.INDENT + text;
		text = text.replaceAll("\n", "\n"+KsdlSpeaker.INDENT);
		text = text.substring(0, text.length() - KsdlSpeaker.INDENT.length());
		
		return text;
	}
	
	public static KsdlSpeaker getInstance() {
		
		return INSTANCE;
	}
}
