package com.kero.security.lang;

import java.util.HashSet;
import java.util.Set;

import com.kero.security.lang.collections.TokenSequence;
import com.kero.security.lang.speakers.BindSpeaker;
import com.kero.security.lang.speakers.SchemeSpeaker;
import com.kero.security.lang.speakers.Speaker;

public class KsdlSpeaker {

	public static String INDENT = "  ";

	private static KsdlSpeaker INSTANCE = new KsdlSpeaker();
	
	private Set<Speaker> speakers;
	
	private KsdlSpeaker() {
		
		this.speakers = new HashSet<>();
			speakers.add(new SchemeSpeaker());
			speakers.add(new BindSpeaker());
	}
	
	public String say(TokenSequence seq) {
		
		StringBuilder builder = new StringBuilder();
		
		while(!seq.isEmpty()) {
			
			speakers.forEach(speaker -> {
				
				if(speaker.isMatch(seq)) {
					
					builder.append(speaker.say(seq) + "\n");
				}
			});
		}
		
		return builder.toString().trim();
	}
	
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
