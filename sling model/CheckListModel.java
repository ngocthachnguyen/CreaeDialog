package ph.com.globe.oc3.core.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class CheckListModel.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CheckListModel {

	private static final Logger LOG = LoggerFactory.getLogger(CheckListModel.class);
	
	/**
	 * return data multi field
	 */
	private List<CheckList> checkListInformation;
	
	@Inject
	@Named("items")
	private Resource checkList;
	
	/**
	 * method Init
	 */
	@PostConstruct
	protected void init() {
		try {
			checkListInformation = new ArrayList<>();
			if (checkList != null) {
				// get children resource multifield
				checkListInformation = filterItemModel(checkListInformation, checkList);
			}
		} catch (Exception ex) {
			LOG.error("Exception :CheckListModel.init() ", ex);
		}
	}
	 /**
     * set data Multifield.
     * @param List<CheckList> 
     * @param Resource: Inject multifield
     */
	private List<CheckList> filterItemModel(List<CheckList> array, Resource resource) {
		if (resource != null) {
			Iterator<Resource> checkListResource = resource.listChildren();
			while (checkListResource.hasNext()) {
				CheckList lstFil = checkListResource.next().adaptTo(CheckList.class);
				if (lstFil != null) {
					array.add(lstFil);
				}
			}
		}

		return array;
	}
	/**
	 * The Multifield ( Icon and Description )
	 */
	public List<CheckList> getCheckListInformation() {
		return checkListInformation;
	}
}
