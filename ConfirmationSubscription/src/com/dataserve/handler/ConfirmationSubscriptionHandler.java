package com.dataserve.handler;

import com.dataserve.dao.HandlerDAO;
import com.dataserve.dao.HijriCalendar;
import com.dataserve.dao.WebEditorDAO;
import com.dataserve.entities.Employee;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.engine.EventActionHandler;
import com.filenet.api.events.ObjectChangeEvent;
import com.filenet.api.property.FilterElement;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.util.Id;

public class ConfirmationSubscriptionHandler implements EventActionHandler {

	public static final long RIGTH_EDIT_WITHOUT_REMOVE_CONFIRM = 262144;
	public static final int DELETE_SIGNATURE_AFTER_EDIT_CONTENT = 14;
	public static final int READD_SIGNATURE_AFTER_EDIT_CONTENT = 15;

	public void onEvent(ObjectChangeEvent event, Id subId) {
		try {
			System.out.println("Event received");
			System.out.println("Event Id = " + event.get_Id());
			System.out.println("Event  SourceObjectId = " + event.get_SourceObjectId());
			// As a best practice, fetch the persisted source object of the
			// event,
			// filtered on the two required properties, Owner and Name.
			// Thread.sleep(1000);
			ObjectStore os = event.getObjectStore();
			Id id = event.get_SourceObjectId();
			FilterElement fe = new FilterElement(null, null, null, "ConfirmUser", null);
			FilterElement fCreator = new FilterElement(null, null, null, "Creator", null);
			FilterElement fMN = new FilterElement(null, null, null, "MajorVersionNumber", null);
			FilterElement fcorrespondenceNumber = new FilterElement(null, null, null, "CorrespondenceNumber", null);
			FilterElement fcorrespondenceType = new FilterElement(null, null, null, "CorrespondenceType", null);
			FilterElement fhijricYear = new FilterElement(null, null, null, "HijricYear", null);
			FilterElement fDocumentTitle = new FilterElement(null, null, null, "DocumentTitle", null);
			FilterElement fmimeType = new FilterElement(null, null, null, "mimetype", null);
			FilterElement IsCurrentVersion = new FilterElement(null, null, null, "IsCurrentVersion", null);
			
			
			//FilterElement fVersionSeries = new FilterElement(null, null, null, "VersionSeries", null);
			

			PropertyFilter pf = new PropertyFilter();
			pf.addIncludeProperty(fe);
			pf.addIncludeProperty(fCreator);
			pf.addIncludeProperty(fMN);
			pf.addIncludeProperty(fcorrespondenceNumber);
			pf.addIncludeProperty(fcorrespondenceType);
			pf.addIncludeProperty(fhijricYear);
			pf.addIncludeProperty(fDocumentTitle);
			pf.addIncludeProperty(fmimeType);
			pf.addIncludeProperty(IsCurrentVersion);

			Document doc = Factory.Document.fetchInstance(os, id, pf);
			
			if (!doc.get_IsCurrentVersion())return;
			
			String mimeType = doc.get_MimeType();

			if (mimeType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {

				String confirmUser = doc.getProperties().getStringValue("ConfirmUser");
				String creator = doc.get_Creator();
				String versionNumber = doc.get_MajorVersionNumber().toString();
				Integer correspondenceNumber = doc.getProperties().getInteger32Value("CorrespondenceNumber");
				Integer correspondenceType = doc.getProperties().getInteger32Value("CorrespondenceType");
				Integer hijricYear = doc.getProperties().getInteger32Value("HijricYear");
				String documentTitle = doc.getProperties().getStringValue("DocumentTitle");

				System.out.println("Creator = " + creator);
				System.out.println("versionNumber = " + versionNumber);
				System.out.println("confirmUser   = " + confirmUser);

				HandlerDAO dao = new HandlerDAO();
				Employee employee = dao.getEmployeeByUserId(creator);
				boolean permission = isHasOption(employee.getEnabledOptionsMaskVIP(), RIGTH_EDIT_WITHOUT_REMOVE_CONFIRM);
				System.out.println("User Permission = " + permission);

				HijriCalendar hijriCalendar = new HijriCalendar();
				String currentHihriDate = hijriCalendar.getCurrentHijriDate();
				String actionTime = hijriCalendar.getCurrentHijriDateTime(currentHihriDate, 1);
				long actionTimeInt = hijriCalendar.getCurrentHijriDateTimeAsLong(currentHihriDate, 1);
				WebEditorDAO WebEditorDAO = new WebEditorDAO();
				if (permission && confirmUser != null && !confirmUser.equals("")
						&& !confirmUser.split(":")[1].equals(versionNumber)) {
					String confirmUserId = confirmUser.split(":")[0];
					int confirmVersionNumber = Integer.parseInt(confirmUser.split(":")[1]) + 1;
					if (versionNumber.equals(confirmVersionNumber + "")) {
						String newValue = confirmUserId + ":" + versionNumber;
						doc = Factory.Document.fetchInstance(os, id, null);
						doc.getProperties().putValue("ConfirmUser", newValue);
						System.out.println("update ConfirmUser to " + newValue);
						doc.save(RefreshMode.NO_REFRESH);
						System.out.println("Add Log ");
						WebEditorDAO.addWebEditorLog(doc.get_VersionSeries().get_Id().toString(),
								READD_SIGNATURE_AFTER_EDIT_CONTENT, creator, doc.get_Id().toString(), documentTitle,
								correspondenceNumber, hijricYear, correspondenceType, actionTime, actionTimeInt,
								"application/vnd.openxmlformats-officedocument.wordprocessingml.document",
								confirmUserId, 0);
					} else {
						//doc = Factory.Document.fetchInstance(os, id, null);
						//doc.getProperties().putValue("ConfirmUser", "");
						System.out.println("leave ConfirmUser it is old");
						//doc.save(RefreshMode.NO_REFRESH);
					}
				} else if (permission == false && confirmUser != null && !confirmUser.equals("")) {
					String confirmUserId = confirmUser.split(":")[0];
					System.out.println("clear ConfirmUser user do not have permission");
					doc = Factory.Document.fetchInstance(os, id, null);
					doc.getProperties().putValue("ConfirmUser", "");
					doc.save(RefreshMode.NO_REFRESH);
					WebEditorDAO.addWebEditorLog(doc.get_VersionSeries().get_Id().toString(),
							DELETE_SIGNATURE_AFTER_EDIT_CONTENT, creator, doc.get_Id().toString(), documentTitle,
							correspondenceNumber, hijricYear, correspondenceType, actionTime, actionTimeInt,
							"application/vnd.openxmlformats-officedocument.wordprocessingml.document", confirmUserId,
							1);
				}
			}
		} catch (Exception e) {
			System.out.println("Event Exception");
			e.printStackTrace();
		}
	}

	public boolean isHasOption(long mask, long optionCode) throws Exception {
		return ((mask & optionCode) != 0);
	}

}
