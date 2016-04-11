package com.example.wescrabble.adapter;

import java.util.ArrayList;

import com.example.wescrabble.R;
import com.example.wescrabble.utils.MyCharacter;

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
import android.widget.RelativeLayout;

public class AlphabetListAdapter extends BaseAdapter{

	private Context mContext;
	private ArrayList<MyCharacter> mAllCharacters;
	
	public AlphabetListAdapter (Context context, ArrayList<MyCharacter> allCharacters) {
		mContext = context;
		mAllCharacters = allCharacters;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mAllCharacters.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mAllCharacters.get(position);
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
			convertView = (RelativeLayout) RelativeLayout.inflate(mContext, R.layout.item_view_character, null);
		}
		
		ImageView v = (ImageView) convertView.findViewById(R.id.image_view_character);
	
		v.setImageDrawable(mContext.getResources().getDrawable(
				mAllCharacters.get(position).getBitmapDrawable()));
		v.setTag(mAllCharacters.get(position));		
		return convertView;
	}

}
