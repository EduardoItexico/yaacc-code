package de.yaacc.upnp;

import java.util.List;

import org.teleal.cling.model.XMLUtil;
import org.teleal.cling.model.action.ActionInvocation;
import org.teleal.cling.model.message.UpnpResponse;
import org.teleal.cling.model.meta.Service;
import org.teleal.cling.support.contentdirectory.callback.Browse;
import org.teleal.cling.support.contentdirectory.callback.Browse.Status;
import org.teleal.cling.support.model.BrowseFlag;
import org.teleal.cling.support.model.BrowseResult;
import org.teleal.cling.support.model.DIDLContent;
import org.teleal.cling.support.model.SortCriterion;
import org.teleal.cling.support.model.container.Container;
import org.teleal.cling.support.model.item.Item;
/**
 * 
 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License
 as published by the Free Software Foundation; either version 3
 of the License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/
/**
 * Browser for ContentDirectories. 
 * Connect an instance of this class to a MediaServer-Service.
 * After calling run you will browse the MediaServer-Directory asynchronously 
 * @author Tobias Schöne (openbit)  
 *
 */
public class ContentDirectoryBrowser extends Browse {
	private Status status = Status.NO_CONTENT;
	private List<Container> containers = null;
	private List<Item> items = null;

	/**
	 * @param service
	 * @param objectID
	 * @param flag
	 * @param filter
	 * @param firstResult
	 * @param maxResults
	 * @param orderBy
	 */
	public ContentDirectoryBrowser(Service service, String objectID,
			BrowseFlag flag, String filter, long firstResult, Long maxResults,
			SortCriterion... orderBy) {
		super(service, objectID, flag, filter, firstResult, maxResults, orderBy);

	}

	/**
	 * @param service
	 * @param containerId
	 * @param flag
	 */
	public ContentDirectoryBrowser(Service service, String containerId,
			BrowseFlag flag) {
		super(service, containerId, flag);

	}

	@Override
	public void received(ActionInvocation actionInvocation, DIDLContent didl) {
		containers = didl.getContainers();
		for (Container container : containers) {			
			System.out.println("Container " + container.getTitle() + " Id: " + container.getId() + " (" + container.getChildCount() + ")");
		}
		items = didl.getItems();
		for (Item item : items) {
			System.out.println( "Item: " + item.getTitle() + " Id: " + item.getId());
		}
		
		
	}

	
	
	

	@Override
	public boolean receivedRaw(ActionInvocation actionInvocation,
			BrowseResult browseResult) {
		
		boolean result = super.receivedRaw(actionInvocation, browseResult);
		System.out.println(browseResult.getResult());
		return result; 
	}

	@Override
	public void updateStatus(Status status) {
		System.out.println("updateStatus: " + status);
		this.status = status;
	}

	@Override
	public void failure(ActionInvocation invocation, UpnpResponse operation,
			String defaultMsg) {

	}

	public Status getStatus() {
		return status;
	}

	/**
	 * @return the containers
	 */
	public List<Container> getContainers() {
		return containers;
	}

	/**
	 * @return the items
	 */
	public List<Item> getItems() {
		return items;
	}

}