/*
 * Copyright (C) 2013 www.yaacc.de 
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package de.yaacc.player;

import java.util.List;

import de.yaacc.R;
import de.yaacc.R.layout;
import de.yaacc.R.menu;
import de.yaacc.musicplayer.BackgroundMusicService;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
/**
 * A avtransport player activity controlling the {@link AVTransportPlayer}.
 * @author Tobias Schoene (openbit)  
 *
 */
public class AVTransportPlayerActivity extends Activity {

	private Player player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_avtransport_player);
		// initialize buttons
		
	    List<Player> players = PlayerFactory.getCurrentPlayersOfType(LocalBackgoundMusicPlayer.class);
	    if(players != null && players.size() == 1){ //assume that there is only one background music player on this device  
	    	player = players.get(0);
	    }
	    ImageButton btnPrev = (ImageButton) findViewById(R.id.avtransportPlayerActivityControlPrev);
	    ImageButton btnNext = (ImageButton) findViewById(R.id.avtransportPlayerActivityControlNext);
	    ImageButton btnStop = (ImageButton) findViewById(R.id.avtransportPlayerActivityControlStop);
	    ImageButton btnPlay = (ImageButton) findViewById(R.id.avtransportPlayerActivityControlPlay);
	    ImageButton btnPause = (ImageButton) findViewById(R.id.avtransportPlayerActivityControlPause);
	    ImageButton btnExit = (ImageButton) findViewById(R.id.avtransportPlayerActivityControlExit);
	    if(player == null){
	    	btnPrev.setActivated(false);
	    	btnNext.setActivated(false); 
	    	btnStop.setActivated(false); 
	    	btnPlay.setActivated(false); 
	    	btnPause.setActivated(false);
	    	btnExit.setActivated(false);
	    }else{
	    	btnPrev.setActivated(true);
	    	btnNext.setActivated(true); 
	    	btnStop.setActivated(true); 
	    	btnPlay.setActivated(true); 
	    	btnPause.setActivated(true);
	    	btnExit.setActivated(true);
	    }
		btnPrev.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				player.previous();

			}
		});
		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				player.next();

			}
		});	    
		btnPlay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				player.play();

			}
		});
		btnPause.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				player.pause();

			}
		});
		btnStop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				player.stop();

			}
		});
		btnExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				player.exit();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_avtransport_player, menu);

		return true;
	}

}
