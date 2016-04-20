package com.example.wescrabble.fragments;

import com.example.wescrabble.R;
import com.example.wescrabble.WeScrabble;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GameFinishFragment extends Fragment{
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View returnView = inflater.inflate(R.layout.fragment_game_finish, container);
		if (((WeScrabble)getActivity()).gameResult() == 0){
			TextView text = (TextView) returnView.findViewById(R.id.text_result);
			text.setText("Sorry, You lose the game!");
		}
		return returnView;
	}
}
