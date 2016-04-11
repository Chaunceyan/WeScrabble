package com.example.wescrabble.fragments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.example.wescrabble.R;
import com.example.wescrabble.adapter.AlphabetListAdapter;
import com.example.wescrabble.utils.MyCharacter;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.app.Fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

public class GamePlayFragment extends Fragment {
	
	public GamePlayFragment(){}
	
	private AlphabetListAdapter mAlphabetListAdapter;
	private ArrayList<MyCharacter> characters = new ArrayList<MyCharacter>();

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		
		View returnView = inflater.inflate(R.layout.fragment_game_play, container, false);
		
		ListView characterList = (ListView) returnView.findViewById(R.id.list_view_alphabet);

		generateCharacters();
		
		mAlphabetListAdapter = new AlphabetListAdapter(this.getActivity(), characters);
		characterList.setAdapter(mAlphabetListAdapter);
		
		characterList.setOnItemClickListener();
		
		return returnView;
		
	}
	
	private void generateCharacters() {
		ArrayList<Integer> collection = new ArrayList<Integer>();
		for (int index = 'a'; index < 'z'; index ++) {
			collection.add(index - 'a');
			characters.add(new MyCharacter(index - 'a'));
		}		
		Collections.shuffle(collection);
		Log.d("weScrabble", String.valueOf(collection.toArray().length));
	}
	
	protected class MyOnClickListener implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
            
			ImageView iv = (ImageView) view.findViewById(R.id.image_view_character);

            final int touchedX = (int) (iv.lastTouchedX + 0.5f);
            final int touchedY = (int) (iv.lastTouchedY + 0.5f);

            view.startDrag(null, new DragShadowBuilder(view) {
                @Override
                public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
                    super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
                    shadowTouchPoint.x = touchedX;
                    shadowTouchPoint.y = touchedY;
                }

                @Override
                public void onDrawShadow(Canvas canvas) {
                    super.onDrawShadow(canvas);
                }
            }, view, 0);

            view.setVisibility(View.INVISIBLE);

            return;
		}
	}
}
