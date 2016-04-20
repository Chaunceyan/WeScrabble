package com.example.wescrabble.utils;

import com.example.wescrabble.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import at.weScrabble.interfaces.JMyLetter;


public class MyLetter implements JMyLetter {

	private Integer mBitmapDrawable;
	private char mChar;
	
	public MyLetter (Integer index) {
		mBitmapDrawable = resources[index];
		mChar = (char) (index + 'a');
	}

	public Integer getBitmapDrawable () {
		return mBitmapDrawable;
	}
	
	public char getChar() {
		return mChar;
	}
	
	private Integer[] resources = {
			R.drawable.a, R.drawable.b,
			R.drawable.c, R.drawable.d,
			R.drawable.e, R.drawable.f,
			R.drawable.g, R.drawable.h,
			R.drawable.i, R.drawable.j,
			R.drawable.k, R.drawable.l,
			R.drawable.m, R.drawable.n,
			R.drawable.o, R.drawable.p,
			R.drawable.q, R.drawable.r,
			R.drawable.s, R.drawable.t,
			R.drawable.u, R.drawable.v,
			R.drawable.w, R.drawable.x,
			R.drawable.y, R.drawable.z
	};

	public MyLetter asMyLetter() {
		// TODO Auto-generated method stub
		return this;
	}
}
