package com.example.wescrabble.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import android.util.Log;
import at.weScrabble.interfaces.JMyLetterList;

public class MyLetterList implements JMyLetterList {
	
	private ArrayList<MyLetter> mAllLetters;
	
	public MyLetterList() {
		mAllLetters = new ArrayList<MyLetter>();
		generateLetters();
	}
	
	public MyLetterList(int[] letters) {
		mAllLetters = new ArrayList<MyLetter>();
		for (int index=0; index<letters.length; index++) {
			mAllLetters.add(new MyLetter(letters[index]));
		}
	}

	private void generateLetters() {
		// TODO Auto-generated method stub
		Random random = new Random(System.currentTimeMillis());
		for (int i=0; i<10; i++) {
			double ratio = Math.random()*3;
			if (ratio > 1.85) {
				int index = random.nextInt(5); 
				mAllLetters.add(new MyLetter("aeiou".charAt(index)-'a'));
			} else {
				int index = random.nextInt(21);
				mAllLetters.add(new MyLetter("bcdfghjklmnpqrstvwxyz".charAt(index)-'a'));
			}
		}
		Collections.shuffle(mAllLetters);
	}

	public int size() {
		// TODO Auto-generated method stub
		return mAllLetters.size();
	}

	public MyLetter get(int position) {
		// TODO Auto-generated method stub
		return mAllLetters.get(position);
	}

	public boolean addAll(ArrayList<MyLetter> letters) {
		// TODO Auto-generated method stub
		return mAllLetters.addAll(letters);
	}

	public void add(MyLetter myLetter) {
		// TODO Auto-generated method stub
		mAllLetters.add(myLetter);
	}

	public boolean removeItem(MyLetter myLetter) {
		// TODO Auto-generated method stub
		if (mAllLetters.remove(myLetter)) return true;
		return false;
	}

	public boolean removeLetter(char letter) {
		Iterator<MyLetter> iterator = mAllLetters.iterator();
		while (iterator.hasNext()){
			MyLetter mLetter = iterator.next();
			if (mLetter.getChar() == letter) {
				Log.d("weScrabble", "Letter: " + letter + " removed");
				iterator.remove();
				return true;
			}
		}
		return false;
	}
	
	public MyLetterList asMyLetterList() {
		// TODO Auto-generated method stub
		return this;
	}

	public int[] toArray() {
		// TODO Auto-generated method stub
		synchronized (mAllLetters) {
			int[] array = new int[mAllLetters.size()];
			for (int index = 0; index < mAllLetters.size(); index++) {
				array[index] = mAllLetters.get(index).getChar() - 'a';
			}
			return array;
		}
	}

}
