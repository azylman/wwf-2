package com.zylman.wwf.shared;

public final class InputValidator {
	
	public static boolean validateRack(String word) {
		int wildcardCount = 0;
		for (int i = 0; i < word.length(); ++i) {
			Character c = word.charAt(i);
			if (c.equals('*')) {
				wildcardCount++;
			} else if (!Character.isLetter(c)) {
				return false;
			}
		}
		return wildcardCount < 3 && word.length() < 11;
	}
	
	public static boolean validateOther(String word) {
		for (int i = 0; i < word.length(); ++i) {
			Character c = word.charAt(i);
			if (!Character.isLetter(c)) {
				return false;
			}
		}
		return true;
	}
}
