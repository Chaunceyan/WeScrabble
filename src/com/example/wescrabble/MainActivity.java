package com.example.wescrabble;

import com.example.wescrabble.fragments.GamePlayFragment;
import com.example.wescrabble.fragments.TeamSelectionFragment;

import edu.vub.at.android.util.IATAndroid;
import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	public int teamID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StartIATTask.execute(new Thread());

		TeamSelectionFragment teamSelectionFragment = new TeamSelectionFragment();
		getFragmentManager().beginTransaction()
			.add(R.id.fragment_container, teamSelectionFragment)
			.commit();

	}

	
	public class StartIATTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				IATAndroid iat_ = IATAndroid.create(MainActivity.this);
				
			} catch(Exception e) {
				Log.e("weScrabble","Failed to load IAT", e);
			}
			return null;
		}

	}
}
