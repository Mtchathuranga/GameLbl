package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import utility.Constants;

public class LetterEngine {
	private String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	private String[] vowels = { "A", "E", "I", "O", "U" };
	private String[] consonants = { "B", "C", "D", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "V",
			"W", "X", "Y", "Z" };
	private List<String> pair = new ArrayList<String>();
	private int last = -1;

	public List<String> getRandomLetterPairs(String type, int count) {
		final List<String> temp = new ArrayList<String>();
		if (type.equals(Constants.LETTER)) {
			for (int i = 0; i < count; i++) {
				// Generate letters
				getLetter(type, letters, 0, 25);
			}
		} else if (type.equals(Constants.VOWEL)) {
			for (int i = 0; i < count; i++) {
				// Generate letters
				getLetter(type, vowels, 0, 4);
			}
		} else if (type.equals(Constants.CONSONANT)) {
			for (int i = 0; i < count; i++) {
				// Generate letters
				getLetter(type, consonants, 0, 20);
			}
		}

		temp.addAll(pair);
		// clear Letter pair list
		pair.clear();
		last = -1;

		return temp;
	}

	/**
	 * Get Random letter from letter Array
	 */
	private void getLetter(String type, String[] container, int min, int max) {
		int newNo = getRandomNo(min, max);
		if (newNo == last) {
			getLetter(type, container, min, max);
		} else {
			last = newNo;
			pair.add(container[last]);
		}
	}

	/**
	 * Generate Random Number between 0 and 25
	 * 
	 * @return
	 */
	private int getRandomNo(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}

	/**
	 * Get letter points
	 * 
	 * @param letter
	 * @return
	 */
	public int getLetterPoints(String letter) {
		int val = 0;
		if (letter.equals("A") || letter.equals("I") || letter.equals("J") || letter.equals("Q")
				|| letter.equals("Y")) {
			val = 1;
		} else if (letter.equals("B") || letter.equals("K") || letter.equals("R")) {
			val = 2;
		} else if (letter.equals("C") || letter.equals("G") || letter.equals("L") || letter.equals("S")) {
			val = 3;
		} else if (letter.equals("D") || letter.equals("T") || letter.equals("M")) {
			val = 4;
		} else if (letter.equals("E") || letter.equals("H") || letter.equals("N") || letter.equals("X")) {
			val = 5;
		} else if (letter.equals("U") || letter.equals("V") || letter.equals("W")) {
			val = 6;
		} else if (letter.equals("O") || letter.equals("Z")) {
			val = 7;
		} else if (letter.equals("F") || letter.equals("P")) {
			val = 8;
		}

		return val;
	}

	/**
	 * gets the total points for given letters
	 * 
	 * @param word
	 * @return
	 */
	public int getWordPoints(String word) {
		int val = 0;
		for (int i = 0; i < word.length(); i++) {
			String letter = "" + word.charAt(i);
			val += getLetterPoints(letter.toUpperCase());
		}
		return val;
	}

	public boolean checkWordMistakes(String word, List<String> rand, List<String> cons, List<String> vowels) {
		HashMap<String, Integer> mainList = new HashMap<String, Integer>();
		// Check RandomLetters
		for (String key : rand) {
			if (mainList.containsKey(key)) {
				mainList.put(key, (mainList.get(key) + 1));
			} else {
				mainList.put(key, 1);
			}
		}
		// Check Consonants
		for (String key : cons) {
			if (mainList.containsKey(key)) {
				mainList.put(key, (mainList.get(key) + 1));
			} else {
				mainList.put(key, 1);
			}
		}
		// Check Vowels
		for (String key : vowels) {
			if (mainList.containsKey(key)) {
				mainList.put(key, (mainList.get(key) + 1));
			} else {
				mainList.put(key, 1);
			}
		}
		// create temp Word HashMap
		HashMap<String, Integer> wordtemp = new HashMap<String, Integer>();
		for (int i = 0; i < word.length(); i++) {
			String letter = "" + word.charAt(i);
			if (wordtemp.containsKey(letter.toUpperCase())) {
				wordtemp.put(letter.toUpperCase(), (wordtemp.get(letter.toUpperCase()) + 1));
			} else {
				wordtemp.put(letter.toUpperCase(), 1);
			}
		}
		boolean mistake = false;
		for (String key : wordtemp.keySet()) {
			if (wordtemp.get(key) > mainList.get(key)) {
				mistake = true;
			}
		}
		return mistake;
	}
}
