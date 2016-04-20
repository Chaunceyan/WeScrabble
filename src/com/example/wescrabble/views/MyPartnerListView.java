package com.example.wescrabble.views;

import com.example.wescrabble.adapter.AlphabetListAdapter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class MyPartnerListView extends ListView {

	private int mUserId = -1;
	public MyPartnerListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void setContent(int userId, AlphabetListAdapter adapter) {
		mUserId = userId;
		this.setAdapter(adapter);
	}
	
	public int getUserId() {
		return mUserId;
	}

}
