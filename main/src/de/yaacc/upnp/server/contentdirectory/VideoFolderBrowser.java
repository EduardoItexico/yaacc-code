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
package de.yaacc.upnp.server.contentdirectory;

import java.util.ArrayList;
import java.util.List;

import org.fourthline.cling.support.model.DIDLObject;
import org.fourthline.cling.support.model.Res;
import org.fourthline.cling.support.model.container.Container;
import org.fourthline.cling.support.model.container.PhotoAlbum;
import org.fourthline.cling.support.model.container.StorageFolder;
import org.fourthline.cling.support.model.item.Item;
import org.fourthline.cling.support.model.item.Photo;
import org.fourthline.cling.support.model.item.VideoItem;
import org.seamless.util.MimeType;

import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import de.yaacc.upnp.server.ContentDirectoryFolder;
import de.yaacc.upnp.server.YaaccContentDirectory;
import de.yaacc.upnp.server.YaaccUpnpServerService;
/**
 * Browser  for the video folder.
 * 
 * 
 * @author openbit (Tobias Schoene)
 * 
 */
public class VideoFolderBrowser extends ContentBrowser {

	@Override
	public DIDLObject browseMeta(YaaccContentDirectory contentDirectory, String myId) {
		
		StorageFolder videosFolder = new StorageFolder(ContentDirectoryFolder.MOVIES.getId(), ContentDirectoryIDs.ROOT.getId(), "Videos", "yaacc", getSize(contentDirectory,myId),
				907000L);
		return videosFolder;
	}

	private Integer getSize(YaaccContentDirectory contentDirectory, String myId){
		 Integer result = 0;
				String[] projection = { "count(*) as count" };
				String selection = "";
				String[] selectionArgs = null;
				Cursor cursor = contentDirectory.getContext().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, selection,
						selectionArgs, null);

				if (cursor != null) {
					cursor.moveToFirst();
					result = Integer.valueOf(cursor.getString(0));
					cursor.close();
				}
				return result;
	}
	
	@Override
	public List<Container> browseContainer(YaaccContentDirectory contentDirectory, String myId) {
		
		return new ArrayList<Container>();
	}

	@Override
	public List<Item> browseItem(YaaccContentDirectory contentDirectory, String myId) {
		List<Item> result = new ArrayList<Item>();
		String[] projection = { MediaStore.Video.Media._ID, MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.MIME_TYPE,
				MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION };
		String selection = "";
		String[] selectionArgs = null;
		Cursor mediaCursor = contentDirectory.getContext().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, selection,
				selectionArgs, null);

		if (mediaCursor != null) {
			mediaCursor.moveToFirst();
			while (!mediaCursor.isAfterLast()) {
				String id = mediaCursor.getString(mediaCursor.getColumnIndex(MediaStore.Video.VideoColumns._ID));
				String name = mediaCursor.getString(mediaCursor.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME));
				String duration = mediaCursor.getString(mediaCursor.getColumnIndex(MediaStore.Video.VideoColumns.DURATION));
				duration = contentDirectory.formatDuration(duration);
				Long size = Long.valueOf(mediaCursor.getString(mediaCursor.getColumnIndex(MediaStore.Video.VideoColumns.SIZE)));
				Log.d(getClass().getName(), "Mimetype: " + mediaCursor.getString(mediaCursor.getColumnIndex(MediaStore.Video.VideoColumns.MIME_TYPE)));
				MimeType mimeType = MimeType.valueOf(mediaCursor.getString(mediaCursor.getColumnIndex(MediaStore.Video.VideoColumns.MIME_TYPE)));
				// file parameter only needed for media players which decide the
				// ability of playing a file by the file extension
				String uri = "http://" + contentDirectory.getIpAddress() + ":" + YaaccUpnpServerService.PORT + "/?id=" + id + "&f='" + name + "'";
				Res resource = new Res(mimeType, size, uri);
				resource.setDuration(duration);
				result.add(new VideoItem(ContentDirectoryIDs.VIDEO_PREFIX.getId() +  id, ContentDirectoryIDs.VIDEOS_FOLDER.getId(), name, "", resource));
				Log.d(getClass().getName(), "VideoItem: " + id + " Name: " + name + " uri: " + uri);
				mediaCursor.moveToNext();
			}
			mediaCursor.close();
		} else {
			Log.d(getClass().getName(), "System media store is empty.");
		}
		return result;
		
	}

}
