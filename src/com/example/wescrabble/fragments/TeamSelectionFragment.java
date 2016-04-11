package com.example.wescrabble.fragments;

import com.example.wescrabble.MainActivity;
import com.example.wescrabble.R;
import com.example.wescrabble.R.id;
import com.example.wescrabble.R.layout;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class TeamSelectionFragment extends Fragment{
	
	public TeamSelectionFragment() {}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		
		View returnView = inflater.inflate(R.layout.fragment_team_selection, container, false);
		final MainActivity mMainActivity = (MainActivity) this.getActivity();
		
		Button firstTeamButton = (Button) returnView.findViewById(R.id.button1);
		firstTeamButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mMainActivity.teamID = 0;
			}
		});
		
		Button secondTeamButton = (Button) returnView.findViewById(R.id.button2);
		secondTeamButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mMainActivity.teamID = 1;
				GamePlayFragment gamePlayFragment = new GamePlayFragment();
				mMainActivity.getFragmentManager().beginTransaction()
					.replace(R.id.fragment_container, gamePlayFragment).addToBackStack(null).commit();
			}
		});
				
		return returnView;
	
	}
}
