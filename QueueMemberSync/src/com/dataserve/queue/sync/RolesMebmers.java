package com.dataserve.queue.sync;

import java.util.List;
import filenet.vw.api.VWApplicationSpace;
import filenet.vw.api.VWException;
import filenet.vw.api.VWParticipant;
import filenet.vw.api.VWRole;
import filenet.vw.api.VWSession;


public class RolesMebmers {

	
	private static VWSession vwSession;
	private static String ceUri;
	
	public RolesMebmers(VWSession vwSession) {
		super();
		RolesMebmers.vwSession = vwSession;
	}


	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		RolesMebmers.ceUri = "http://D1SHLAPXXTWV3:9080/wsi/FNCEWS40MTOM/";//stg
		RolesMebmers.ceUri = "http://D1SHLAPXXPWV7:9080/wsi/FNCEWS40MTOM/";//prd
		RolesMebmers.vwSession = getVWSession("conn1", ceUri);
		
		RolesMebmers rm = new RolesMebmers(vwSession);
		DepartmentQueueDAO departmentQueueDAO = new DepartmentQueueDAO();
		List<DepartmentQueue> deptQueues = departmentQueueDAO.getDepartmentQueuesList();
		String username = null;

		for (DepartmentQueue deptQueue : deptQueues) {

			VWParticipant[] vWParticipant = rm.getInbasketMembers("Moamalat Application Spaces", deptQueue.getRoleName_English());
			//vWParticipant = rm.getInbasketMembers("Moamalat Application Spaces", "إدارة الإتصالات الإدارية - الإدارية والمالية");
			if (vWParticipant == null || vWParticipant.length <= 0) {
				System.out.println(deptQueue.getDepartmentId() + " vWParticipant  is still null will continue");
                continue;
            }
			
			for (int i = 0; i < vWParticipant.length; i++) {
	            username = vWParticipant[i].getParticipantName();

				boolean b = departmentQueueDAO.addDepartmentQueuesUsers(deptQueue.getDepartmentId(), username);
				if (b) System.out.println(deptQueue.getDepartmentId() + " : "+username+" was added");
				else System.out.println(deptQueue.getDepartmentId() + " : "+username+" NOT added");

				//departmentQueueDAO.addDepartmentQueuesUsers(697, username);
	        }
//			break;
		}
		
		System.out.println("Done");
		
	}
	
	
	private VWParticipant[] getInbasketMembers(String applicationSpaceName, String roleName) {
        try {
            VWSession pe = RolesMebmers.vwSession;
            VWApplicationSpace applicationSpace = pe.fetchApplicationSpace(applicationSpaceName,VWSession.APPLICATIONSPACE_INCLUDING_ROLE_MEMBERS);
            VWRole role = applicationSpace.getRole(roleName);
            VWParticipant[] participants = role.getParticipants();
            
            return participants;

        } catch (Exception e) {
         e.printStackTrace();
        }
        return null;
    }
	
	public static VWSession getVWSession(String conn_point, String ceUri) throws Exception
	{
	   VWSession vwSession=new VWSession();
	   vwSession.setBootstrapCEURI(ceUri);
	   try
	   {
	    //  vwSession.logon(conn_point);
//		   vwSession.logon("P8AdminSTG", "YfANktyCuUduL4RdgrfkffEpF", conn_point);//stg
		   vwSession.logon("p8adminprd", "j7HLtdtNDHR3Hg66ExtZxhTSM", conn_point);//prd
	   }
	   catch (VWException vwe)
	   {
		   vwe.getMessage();
		   vwe.printStackTrace();
	      throw new Exception("Cannot connect to the connection point.");
	      
	   }
	   return vwSession;
	}

}
