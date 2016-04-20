package com.example.wescrabble;

import java.util.ArrayList;
import java.util.Random;

import com.example.wescrabble.adapter.AlphabetListAdapter;
import com.example.wescrabble.fragments.GamePlayFragment;
import com.example.wescrabble.fragments.TeamSelectionFragment;
import com.example.wescrabble.utils.MyLetter;
import com.example.wescrabble.utils.WeScrabbleConstants;

import edu.vub.at.IAT;
import edu.vub.at.android.util.IATAndroid;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.SyncStateContract.Constants;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import at.weScrabble.interfaces.ATWeScrabble;
import at.weScrabble.interfaces.JWeScrabble;

public class WeScrabble extends Activity {
	
	private int mResult = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		TeamSelectionFragment teamSelectionFragment = new TeamSelectionFragment();
		getFragmentManager().beginTransaction()
			.add(R.id.fragment_container, teamSelectionFragment)
			.commit();

	}
	
	
	public void changeResult(int result) {
		mResult = result;
	}
	
	public int gameResult() {
		return mResult;
	}
	
	
	public JWeScrabble registerATApp(ATWeScrabble atws) {
		return ((GamePlayFragment) getFragmentManager().findFragmentById(R.id.fragment_container)).registerATApp(atws);
	}
}
