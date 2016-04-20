package com.example.wescrabble.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import com.example.wescrabble.R;
import com.example.wescrabble.utils.MyLetter;
import com.example.wescrabble.utils.MyLetterList;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import at.weScrabble.interfaces.JMyLetter;

public class AlphabetListAdapter extends BaseAdapter{

	private Context mContext;
	private Integer mId = -1;
	private MyLetterList mLetterList;
	
	public AlphabetListAdapter (Context context) {
		mContext = context;
		mLetterList = generateLetters();
	}
	
	public AlphabetListAdapter (Context context, MyLetterList letterList) {
		mContext = context;
		mLetterList = letterList;
	}
	
	public AlphabetListAdapter (Integer id, Context context, MyLetterList letterList) {
		mId = id;
		mContext = context;
		mLetterList = letterList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mLetterList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mLetterList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = (LinearLayout) LinearLayout.inflate(mContext, R.layout.item_view_character, null);
		}
		ImageView imageView = (ImageView) convertView.findViewById(R.id.image_view_character);
		imageView.setImageDrawable(mContext.getResources().getDrawable(((MyLetter)
				mLetterList.get(position)).getBitmapDrawable()));
		
		convertView.setTag(R.id.image_tag, mLetterList.get(position));		
		return convertView;
	}
	
	
	private MyLetterList generateLetters() {
		return new MyLetterList();
	}
	
	public boolean removeItem (MyLetter myLetter) {
		if (mLetterList.removeItem(myLetter)) {
			notifyDataSetChanged();
			return true;
		}
		return false;
	}
	
	public boolean addItem (MyLetter myLetter) {
		if(myLetter == null) {
			return false;
		} else {
			mLetterList.add(myLetter);
			notifyDataSetChanged();
			return true;
		}
	}
	

	public void addLetter(char letter) {
		// TODO Auto-generated method stub
		mLetterList.add(new MyLetter(letter - 'a'));
		notifyDataSetChanged();
	}
	
	public boolean addLetters(ArrayList<MyLetter> letters) {		
		boolean returnValue = mLetterList.addAll(letters);
		notifyDataSetChanged();
		return returnValue;
	}
	
	public boolean removeLetter(char letter) {
		if (mLetterList.removeLetter(letter)) {
			notifyDataSetChanged();
			return true;
		}
		return false;	
	}
	
	public boolean removeLetters() {
		mLetterList = new MyLetterList();
		notifyDataSetChanged();
		return true;
	}
	
	public MyLetterList getLetterList() {
		return mLetterList;
	}

	public int[] getLetterListAsArray() {
		// TODO Auto-generated method stub
		return mLetterList.toArray();
	}

}
