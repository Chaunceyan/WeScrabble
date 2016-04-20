package com.example.wescrabble.fragments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.example.wescrabble.WeScrabble;
import com.example.wescrabble.R;
import com.example.wescrabble.WeScrabbleAssetInstaller;
import com.example.wescrabble.adapter.AlphabetListAdapter;
import com.example.wescrabble.utils.MyLetter;
import com.example.wescrabble.utils.MyLetterList;
import com.example.wescrabble.utils.WeScrabbleConstants;
import com.example.wescrabble.views.MyLinearLayout;
import com.example.wescrabble.views.MyPartnerListView;

import edu.vub.at.IAT;
import edu.vub.at.android.util.IATAndroid;
import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.text.format.Time;
import android.util.Log;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import at.weScrabble.interfaces.ATWeScrabble;
import at.weScrabble.interfaces.JMyLetter;
import at.weScrabble.interfaces.JMyLetterList;
import at.weScrabble.interfaces.JWeScrabble;

public class GamePlayFragment extends Fragment implements JWeScrabble{
	
	public int mTeamId;
	public ATWeScrabble atws;
	public static Handler mHandler;
	private static IAT iat;
	private AlphabetListAdapter mAlphabetListAdapter;
	private HashMap<Integer, AlphabetListAdapter> mPartnerLetterListAdapter = new HashMap<Integer, AlphabetListAdapter>();
	private LinearLayout mPartnersList;
	
	public GamePlayFragment(int teamId){ mTeamId = teamId; }

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	
		View returnView = inflater.inflate(R.layout.fragment_game_play, container, false);
		View wordHolderLayout = returnView.findViewById(R.id.Linear_layout_word_holder);
		ListView myLetterList = (ListView) returnView.findViewById(R.id.list_view_alphabet);
		mPartnersList = (LinearLayout) returnView.findViewById(R.id.linear_layout_partners_lists);
		Button submitButton = (Button) returnView.findViewById(R.id.button_submit_word);
		
		mAlphabetListAdapter = new AlphabetListAdapter(this.getActivity());
		myLetterList.setAdapter(mAlphabetListAdapter);		
		
		myLetterList.setOnItemLongClickListener(new MyOnClickListener());
		wordHolderLayout.setOnDragListener(new MyOnDragListener());
		
		submitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WeScrabble mActivity = (WeScrabble) v.getContext();
				MyLinearLayout linearLayout = (MyLinearLayout) mActivity.findViewById(R.id.Linear_layout_word_holder);
				String word = linearLayout.getWord();
				if (word == null) {
					Toast.makeText(mActivity, "Drag word from right side!!", Toast.LENGTH_SHORT).show();
				} else {
					mHandler.handleMessage(
							mHandler.obtainMessage(WeScrabbleConstants.SUBMIT_WORD_MSG, word));
				}
			}
		});
		
		if (iat == null) {
			Intent intent = new Intent(this.getActivity(), WeScrabbleAssetInstaller.class);
			startActivityForResult(intent, WeScrabbleConstants.ASSET_INSTALLER);
		}
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Handler handler = GamePlayFragment.mHandler;
						char letter;
						Random random = new Random(System.currentTimeMillis());
						double ratio = Math.random()*3;
						if (ratio > 1.85) {
							int index = random.nextInt(5); 
							letter = (char) ("aeiou".charAt(index));
						} else {
							int index = random.nextInt(21);
							letter = (char) ("bcdfghjklmnpqrstvwxyz".charAt(index));
						}
					
						
						handler.handleMessage(handler.obtainMessage(WeScrabbleConstants.PUT_LETTER_BACK_MSG,
								letter));
						mAlphabetListAdapter.addLetter(letter);
					}
				});
			}}, 30000, 30000);
	
		return returnView;
		
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.v("WeScribble", "Return of Asset Installer activity");
		switch (requestCode) {
		case (WeScrabbleConstants.ASSET_INSTALLER):
			if (resultCode == Activity.RESULT_OK) {
				Interpreter interpreter = new Interpreter();
				interpreter.start();
				mHandler = interpreter.mHandler;
				new StartIATTask().execute((Void) null);
			}
		}
	}
	
	public JWeScrabble registerATApp(ATWeScrabble atws) {
		this.atws = atws;
		return this;
	}
	
	class StartIATTask extends AsyncTask<Void, String, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				iat = IATAndroid.create(GamePlayFragment.this.getActivity());
				
				Log.d("weScrabble", "Loading weScribble code");
				iat.evalAndPrint("import /.weScrabble.weScrabble.makeWeScrabble()", System.err);
			} catch (Exception e) {
				Log.e("weScrabble", "Could not start IAT", e);
			}
			return null;
		}
	}
	
	// AmbientTalk Event Handler
	class Interpreter extends Thread {
		public Handler mHandler = new Handler () {
			public void handleMessage(Message msg){
				if (atws == null) {
					return;
				}
				switch (msg.what) {
					case WeScrabbleConstants.MSG_SHOW_MSG:
						atws.showMessage((String) msg.obj);
						break;
					case WeScrabbleConstants.TAKING_LETTER_OFF_MSG:
						atws.removePartnerLetter((Character) msg.obj);
						break;
					case WeScrabbleConstants.PUT_LETTER_BACK_MSG:
						atws.addPartnerLetter((Character) msg.obj);
						break;
					case WeScrabbleConstants.REQUEST_LETTER_MSG:
						atws.requestLetter(msg.arg1, (Character) msg.obj);
						Toast.makeText(getActivity(), "Requesting letter: " + (Character) msg.obj, Toast.LENGTH_SHORT)
							.show();
						break;
					case WeScrabbleConstants.ACCEPT_REQUEST_MSG:
						atws.acceptRequest(msg.arg1, (Character) msg.obj);
						break;
					case WeScrabbleConstants.REJECT_REQUEST_MSG:
						atws.rejectRequest(msg.arg1);
						break;
					case WeScrabbleConstants.SUBMIT_WORD_MSG:
						atws.submitWord((String) msg.obj);
						break;
					case WeScrabbleConstants.GAME_FINISHED_MSG:
						atws.finishGame();
						break;
				}	
			}
		};
		
		public void run() {
			Looper.prepare();
			Looper.loop();
		}
	}

	protected class MyOnClickListener implements AdapterView.OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

            view.startDrag(null, shadowBuilder, view, 0);
            
            mHandler.handleMessage(
            		mHandler.obtainMessage(
            				WeScrabbleConstants.TAKING_LETTER_OFF_MSG, ((MyLetter)view.getTag(R.id.image_tag)).getChar()));

            return true;
		}

	}
	
	protected class MyOnDragListener implements OnDragListener {

		@Override
		public boolean onDrag(View v, DragEvent event) {
			
			// TODO Auto-generated method stub
			int action = event.getAction();
			View view = (View) event.getLocalState();
			switch(event.getAction()) {
				case DragEvent.ACTION_DRAG_EXITED:
					v.setBackgroundColor(Color.WHITE);
					break;
				case DragEvent.ACTION_DRAG_ENTERED:
					v.setBackgroundColor(Color.GRAY);
					break;
				case DragEvent.ACTION_DROP:
					MyLetter droppedLetter = (MyLetter) view.getTag(R.id.image_tag);
					mAlphabetListAdapter.removeItem(droppedLetter);
					((ListView)view.getParent()).removeViewInLayout(view);
					
					// Change to only the ImageView
					view = populateImageView(droppedLetter);
					view.setOnClickListener(new MyImageOnClickListener());

					((MyLinearLayout) v).addView(view);
					v.invalidate();
					break;
				case DragEvent.ACTION_DRAG_ENDED:
					if (!event.getResult()) {
						v.setBackgroundColor(Color.WHITE);
						mHandler.handleMessage(
				           		mHandler.obtainMessage(
				           				WeScrabbleConstants.PUT_LETTER_BACK_MSG, ((MyLetter)view.getTag(R.id.image_tag)).getChar()));
						break;
					}
			}
			return true;
		}	
	}
	
	protected class MyImageOnClickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			Log.d("weScrabble", "Click event received");
			// TODO Auto-generated method stub
			MyLetter character = (MyLetter) v.getTag(R.id.image_tag);
			LinearLayout linearLayout = (LinearLayout) v.getParent();
			linearLayout.removeView(v);
			GamePlayFragment.this.mAlphabetListAdapter.addItem(character);
			 mHandler.handleMessage(
	            		mHandler.obtainMessage(
	            				WeScrabbleConstants.PUT_LETTER_BACK_MSG, character.getChar()));
		}
		
	}
	
	private View populateImageView(MyLetter myCharacter) {
		ImageView  imageView = new ImageView(this.getActivity());
		int heightAndWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getResources().getDisplayMetrics());
		imageView.setLayoutParams(new LinearLayout.LayoutParams(heightAndWidth, heightAndWidth));
		imageView.setImageDrawable(this.getResources().getDrawable(myCharacter.getBitmapDrawable()));
		imageView.setScaleType(ScaleType.FIT_XY);
		imageView.setAdjustViewBounds(true);
		imageView.setTag(R.id.image_tag, myCharacter);
		return imageView;
	}

	@Override
	public void showMessage(final String message) {
		// TODO Auto-generated method stub
		getActivity().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (message == null) {
					Toast.makeText(getActivity(), "Null Message", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	@Override
	public void addPartnerLetter(final int userId, final char letter) {
		// TODO Auto-generated method stub
		getActivity().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mPartnerLetterListAdapter.get(userId).addLetter(letter);
			}
		});
	}

	@Override
	public int getTeam() {
		// TODO Auto-generated method stub
		return mTeamId;
	}

	@Override
	public int[] getLetterList() {
		// TODO Auto-generated method stub
		return mAlphabetListAdapter.getLetterListAsArray();
	}

	@Override
	public void showPartner(int id, int[] letters) {
		// TODO Auto-generated method stub
		synchronized (mPartnerLetterListAdapter) {
			MyLetterList partnerLetterList = new MyLetterList(letters);
			if (mPartnerLetterListAdapter.containsKey(id)) {
				Log.d("weScrabble", "Id exits");
				mPartnerLetterListAdapter.remove(id);	
			}
			AlphabetListAdapter adapter = new AlphabetListAdapter(getActivity(), partnerLetterList);
			mPartnerLetterListAdapter.put(id, adapter);
		}
	}

	@Override
	public void updatePartners() {
		// TODO Auto-generated method stub
		final WeScrabble mWeScrabble = (WeScrabble) this.getActivity();
		mWeScrabble.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mPartnersList.removeAllViews();
				Iterator<Integer> iterator = mPartnerLetterListAdapter.keySet().iterator();
				
				while (iterator.hasNext()) {
					int key = iterator.next();
					MyPartnerListView partnerListView = new MyPartnerListView(getActivity(), null);
					partnerListView.setContent(key, mPartnerLetterListAdapter.get(key));
					partnerListView.setOnItemLongClickListener(new MyPartnerListOnClickListener());
					mPartnersList.addView(partnerListView);
				}
			}
		});
	}

	protected class MyPartnerListOnClickListener implements AdapterView.OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			mHandler.handleMessage(
				mHandler.obtainMessage(WeScrabbleConstants.REQUEST_LETTER_MSG, 
						((MyPartnerListView) parent).getUserId(), 
						0, 
						((MyLetter) view.getTag(R.id.image_tag)).getChar()));
			return false;
		}
	}

	@Override
	public void removePartnerLetter(final int userId, final char letter) {
		getActivity().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.d("weScrabble", "the char: " + String.valueOf(letter));
				mPartnerLetterListAdapter.get(userId).removeLetter((char) letter);		
			}
		});

	}

	@Override
	public void requestLetter(final int userId, final char letter) {
		
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

			    builder.setTitle("Word Request");
			    builder.setMessage("Teammate request word: '" + letter + "' from you.\r\n"
			    		+ "Please grant or reject the request.");

			    builder.setPositiveButton("GRANT", new DialogInterface.OnClickListener() {

			        public void onClick(DialogInterface dialog, int which) {
			            // Do nothing but close the dialog
			        	if (mAlphabetListAdapter.removeLetter(letter)){
			        		Random random = new Random(System.currentTimeMillis());
			        		char newLetter = (char) (random.nextInt(26)+ 'a');
			        		mAlphabetListAdapter.addLetter(newLetter);
			        		mHandler.handleMessage(
		        				mHandler.obtainMessage(WeScrabbleConstants.ACCEPT_REQUEST_MSG, userId, 0, letter));
			        		mHandler.handleMessage(mHandler.obtainMessage(WeScrabbleConstants.PUT_LETTER_BACK_MSG,
									newLetter));
			        	} else {
			        		mHandler.handleMessage(
			        			mHandler.obtainMessage(WeScrabbleConstants.REJECT_REQUEST_MSG, userId, 0));
			        	};
			            dialog.dismiss();
			        }

			    });

			    builder.setNegativeButton("REJECT", new DialogInterface.OnClickListener() {

			        @Override
			        public void onClick(DialogInterface dialog, int which) {
			            // Do nothing
			      		mHandler.handleMessage(
			        			mHandler.obtainMessage(WeScrabbleConstants.REJECT_REQUEST_MSG, userId, 0));
			            dialog.dismiss();
			        }
			    });

			    AlertDialog alert = builder.create();
			    alert.setCancelable(false);
			    alert.show();
			}
		});
	}

	@Override
	public void addLetter(final char letter) {
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mAlphabetListAdapter.addLetter(letter);
			}
			
		});
	}

	@Override
	public void removeLetter(final char letter) {
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mAlphabetListAdapter.removeLetter(letter);
			}
			
		});
	}

	@Override
	public void removeWord() {
		// TODO Auto-generated method stub
		getActivity().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				LinearLayout wordHolderLayout = (LinearLayout) getActivity().findViewById(R.id.Linear_layout_word_holder);
				wordHolderLayout.removeAllViewsInLayout();
			}
		});
	}

	@Override
	public void completeCheck() {
		// TODO Auto-generated method stub
		getActivity().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (mAlphabetListAdapter.isEmpty()) {
					mHandler.handleMessage(mHandler.obtainMessage(WeScrabbleConstants.GAME_FINISHED_MSG));
					((WeScrabble) getActivity()).changeResult(1);
					GameFinishFragment gameFinishFragment = new GameFinishFragment();
					getFragmentManager().beginTransaction()
						.add(R.id.fragment_container, gameFinishFragment)
						.commit();

				}
			}
		});
	}

	@Override
	public void removePartner(int userId) {
		// TODO Auto-generated method stub
		mPartnerLetterListAdapter.remove(userId);
	}

	@Override
	public void looseGame() {
		// TODO Auto-generated method stub
		getActivity().runOnUiThread(new Runnable() {
				
			@Override
			public void run() {
				((WeScrabble) getActivity()).changeResult(0);
				GameFinishFragment gameFinishFragment = new GameFinishFragment();
				getFragmentManager().beginTransaction()
					.add(R.id.fragment_container, gameFinishFragment)
					.commit();
			}
		});
	}

	@Override
	public void winGame() {
		// TODO Auto-generated method stub
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				((WeScrabble) getActivity()).changeResult(1);
				GameFinishFragment gameFinishFragment = new GameFinishFragment();
				getFragmentManager().beginTransaction()
					.add(R.id.fragment_container, gameFinishFragment)
					.commit();
			}
		});
	}
}
