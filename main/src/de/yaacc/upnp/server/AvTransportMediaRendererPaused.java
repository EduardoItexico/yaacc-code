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
package de.yaacc.upnp.server;

import java.net.URI;
import java.util.List;

import org.teleal.cling.support.avtransport.impl.state.AbstractState;
import org.teleal.cling.support.avtransport.impl.state.PausedPlay;
import org.teleal.cling.support.avtransport.lastchange.AVTransportVariable;
import org.teleal.cling.support.model.AVTransport;
import org.teleal.cling.support.model.MediaInfo;
import org.teleal.cling.support.model.PositionInfo;

import android.app.ActivityManager;
import android.util.Log;

import de.yaacc.player.AVTransportPlayer;
import de.yaacc.player.Player;
import de.yaacc.player.PlayerFactory;
import de.yaacc.upnp.UpnpClient;

public class AvTransportMediaRendererPaused extends PausedPlay<AVTransport> {

	private UpnpClient upnpClient;
	
	/**
	 * Constructor.
	 * 
	 * @param transport
	 *            the state holder
	 * @param upnpClient
	 *            the upnpclient to use
	 */
	public AvTransportMediaRendererPaused(AVTransport transport,
			UpnpClient upnpClient) {
		super(transport);
		this.upnpClient = upnpClient;
	}

	/* (non-Javadoc)
	 * @see org.teleal.cling.support.avtransport.impl.state.PausedPlay#play(java.lang.String)
	 */
	@Override
	public Class<? extends AbstractState> play(String arg0) {
		Log.d(this.getClass().getName(), "play");
		return AvTransportMediaRendererPlaying.class;
	}

	/* (non-Javadoc)
	 * @see org.teleal.cling.support.avtransport.impl.state.PausedPlay#setTransportURI(java.net.URI, java.lang.String)
	 */
	@Override
	public Class<? extends AbstractState> setTransportURI(URI uri, String metaData) {
		Log.d(this.getClass().getName(), "setTransportURI");
		getTransport().setMediaInfo(new MediaInfo(uri.toString(), metaData));
		// If you can, you should find and set the duration of the track here!
		getTransport().setPositionInfo(
				new PositionInfo(1, metaData, uri.toString()));

		// It's up to you what "last changes" you want to announce to event
		// listeners
		getTransport().getLastChange().setEventedValue(
				getTransport().getInstanceId(),
				new AVTransportVariable.AVTransportURI(uri),
				new AVTransportVariable.CurrentTrackURI(uri));

		// This operation can be triggered in any state, you should think
		// about how you'd want your player to react. If we are in Stopped
		// state nothing much will happen, except that you have to set
		// the media and position info, just like in MyRendererNoMediaPresent.
		// However, if this would be the MyRendererPlaying state, would you
		// prefer stopping first?
		return AvTransportMediaRendererStopped.class;
	}

	/* (non-Javadoc)
	 * @see org.teleal.cling.support.avtransport.impl.state.PausedPlay#stop()
	 */
	@Override
	public Class<? extends AbstractState> stop() {
		Log.d(this.getClass().getName(), "stop");
		return AvTransportMediaRendererStopped.class;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.teleal.cling.support.avtransport.impl.state.Playing#onEntry()
	 */
	@Override
	public void onEntry() {
		Log.d(this.getClass().getName(), "On Entry");
		super.onEntry();		
		Player player = upnpClient.getCurrentPlayer(getTransport());
		if(player != null ){
			player.pause();
		}
	}

	
}
