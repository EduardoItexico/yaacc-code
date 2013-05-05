/*
 *
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
package de.yaacc.musicplayer;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

/**
 * A simple service for playing music in background.
 * 
 * @author Tobias Schöne (openbit)
 * 
 */
public class BackgroundMusicService extends Service {

	public static final String URIS = "URIS_PARAM"; // String Intent parameter
	MediaPlayer player;

	public BackgroundMusicService() {
		super();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(this.getClass().getName(), "On Create");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		Log.d(this.getClass().getName(), "On Destroy");
		if (player != null) {
			player.stop();
			player.release();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(this.getClass().getName(), "On Bind");
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Service#onStart(android.content.Intent, int)
	 */
	@Override
	public void onStart(Intent intent, int startid) {
		Log.d(this.getClass().getName(), "On Start");
		if (player == null) {
			player = MediaPlayer.create(this, intent.getData());
		} else {
			player.stop();
			try {
				if (intent.getData() != null) {
					player.setDataSource(this, intent.getData());
				}
			} catch (Exception e) {
				Log.e(this.getClass().getName(),
						"Exception while changing datasource uri", e);

			}
		}
		if (player != null) {
			player.setVolume(100, 100);
			player.start();
			Log.i(this.getClass().getName(), "is Playing:" + player.isPlaying());
		}
	}

	/**
	 * stop current music play
	 */
	public void stopMusic() {
		if (player != null) {
			player.stop();
		}
	}

	/**
	 * start current music play
	 */
	public void playMusic() {
		if (player != null) {
			player.start();
		}
	}
	
	/**
	 * change music uri
	 * @param uri
	 */
	public void setMusicUri(Uri uri){
		if (player != null) {
			try {
				player.setDataSource(this, uri);
			} catch (Exception e) {
				Log.e(this.getClass().getName(),
						"Exception while changing datasource uri", e);

			}
		}
	}
}
