package de.yaacc;

import org.teleal.cling.support.model.DIDLObject;
import org.teleal.cling.support.model.container.Container;
import org.teleal.cling.support.model.item.Item;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class BrowseItemClickListener implements OnItemClickListener, OnItemLongClickListener {


	@Override
	public void onItemClick(AdapterView<?> listView, View arg1, int position, long id) {

		ListView a = (ListView) listView.findViewById(R.id.deviceList);
		BrowseItemAdapter adapter = (BrowseItemAdapter) listView.getAdapter();
		
		DIDLObject currentObject = adapter.getFolder(position);
		
		if(currentObject instanceof Container){
			// if the current id is null, go back to the top level
			String newObjectId = currentObject.getId()==null?"0":adapter.getFolder(position).getId();
			
		BrowseItemAdapter bItemAdapter = new BrowseItemAdapter(listView.getContext(),newObjectId);
    	a.setAdapter(bItemAdapter);
    	
    	BrowseItemClickListener bItemClickListener = new BrowseItemClickListener();
    	a.setOnItemClickListener(bItemClickListener);
		
		} else {
			MainActivity.uClient.playRemote((Item)currentObject, MainActivity.uClient.getReceiverDevice());
		}
						    	
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		return false;
	}
	
}