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

import de.yaacc.imageviewer.ImageViewerActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * @author Tobias Schoene (openbit)  
 * 
 */
public class MultiContentPlayer extends AbstractPlayer {

	/**
	 * @param context
	 */
	public MultiContentPlayer(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see de.yaacc.player.AbstractPlayer#stopItem(de.yaacc.player.PlayableItem)
	 */
	@Override
	protected void stopItem(PlayableItem playableItem) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.yaacc.player.AbstractPlayer#loadItem(de.yaacc.player.PlayableItem)
	 */
	@Override
	protected Object loadItem(PlayableItem playableItem) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see de.yaacc.player.AbstractPlayer#startItem(de.yaacc.player.PlayableItem, java.lang.Object)
	 */
	@Override
	protected void startItem(PlayableItem playableItem, Object loadedItem) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		intent.setDataAndType(playableItem.getUri(), playableItem.getMimeType());
		getContext().startActivity(intent);

	}

}
