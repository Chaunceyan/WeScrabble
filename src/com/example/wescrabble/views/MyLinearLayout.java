package com.example.wescrabble.views;

import java.util.ArrayList;
import java.util.Iterator;

import com.example.wescrabble.R;
import com.example.wescrabble.utils.MyLetter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;

public class MyLinearLayout extends LinearLayout {

	private ArrayList<MyLetter> mLetters = new ArrayList<MyLetter>();
	
	public MyLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void addView (View v) {
		MyLetter letter = (MyLetter) v.getTag(R.id.image_tag);
		mLetters.add(letter);
		super.addView(v);
		this.invalidate();
	}
	
	@Override
	public void removeView(View v) {
		MyLetter letter = (MyLetter) v.getTag(R.id.image_tag);
		mLetters.remove(letter);
		super.removeView(v);
		super.invalidate();
	}
	
	@Override
	public void removeAllViewsInLayout() {
		super.removeAllViewsInLayout();
		mLetters.clear();
		super.invalidate();
	}
	
	public String getWord() {
		if (mLetters.isEmpty()) {
			return null;
		} else {
			Iterator<MyLetter> iterator = mLetters.iterator();
			StringBuilder stringBuilder = new StringBuilder();
			while(iterator.hasNext()) {
				MyLetter letter = iterator.next();
				stringBuilder.append(letter.getChar());
			}
			return stringBuilder.toString();
		}
	}
}
