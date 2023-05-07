/*
 * Employee.java
 *
 * Created on 21 يناير, 2008, 06:08 م
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.dataserve.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */

public class Employee implements Serializable, Comparable<Employee>
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	private PermissionsCodes permissionsCodes = new PermissionsCodes(); 
	private Map<String, Boolean> userPermissions = null;
/*    public PermissionsCodes getPermissionsCodes() {
		return permissionsCodes;
	}

	public void setPermissionsCodes(PermissionsCodes permissionsCodes) {
		this.permissionsCodes = permissionsCodes;
	}*/
	
	private ArrayList<Integer> managedDepartments;

	public static final int RIGHT_NO_OPTIONS = 0;
    public static final int RIGHT_SCAN_BLACK = 0;
    public static final int RIGHT_SCAN_GRAY = 1;
    public static final int RIGHT_SCAN_COLOR = 2;
    public static final int RIGHT_VIEW_MOVIP_ACTION = 8;
    public static final int RIGHT_VIEW_ATTACHMENT = 16;
    public static final int RIGHT_SEND_WITHOUT_ATTACHMENTS = 32;
    public static final int RIGHT_CREATE_ADMINISTRATIVE_DECISIONS = 64;
    //public static final int RIGHT_MINISTER_OFFICE_SEARCH = 128;
    public static final int RIGHT_CREATE_STATISTICAL_REPORTS = 128;
    public static final int RIGHT_ADMIN_COMM_OFFICE_SEARCH = -1; //unused old value was 256
    public static final int RIGHT_FOLLOWUP_REMINDER = 256;
    public static final int RIGHT_SEARCH_DEPT_OPTION = 512;
    
    
    
    public static final int RIGHT_SEARCH_FULL_OPTION = 65536;
    public static final int RIGHT_SEARCH_MAINDEPARTMENTS_OPTION = 8388608;
    public static final int RIGHT_MAIN_DEPARTMENT_MANAGER = 1024;
    
 //   public static final int RIGHT_VIEW_ACVIP_ACTION = 2048;
    
    public static final int RIGHT_SYSTEM_ADMINISTRATOR = 2048;
    
    public static final int RIGHT_VIEW_ALL_MAIN_DEPTS = 4096;
    public static final int RIGHT_EDIT_OUTGOING = 8192;
    public static final int RIGHT_EDIT_INCOMING = 16384;
    public static final int RIGHT_EDIT_INTERNAL = 32768;
    public static final int RIGHT_EDIT_DECISION = 131072;
    public static final int RIGHT_DEPARTMENT_MANAGER_PERMISSION = 262144 ;
    public static final int RIGHT_COMMUNICATE_ALL_EMPLOYEE = 4194304 ;
    public static final int RIGHT_SEND_OUTGOING_WITH_OLD_DATE = 4 ;
    public static final int RIGHT_END_CORRESPONDENCE = 134217728;
    public static final int RIGHT_EDIT_CORR_DETAILS = 16777216;
    public static final int RIGHT_EDIT_CORR_ATTACHMENTS = 33554432;
    public static final int RIGHT_EDIT_CORR_RESEND = 67108864;
    public static final int RIGHT_DELETE_HISTORY_AND_WORITEMS = 268435456;
    public static final int RIGHT_REMINDER_MESSAGE = 536870912;
    public static final int RIGHT_DEADLINE = 1073741824;
    public static final int RIGHT_SHOW_CONFIDENTIALITY_SECURE = 524288;
    public static final int  RIGHT_SHOW_CONFIDENTIALITY_VERY_SECURE = 1048576;
    public static final int RIGHT_SHOW_CONFIDENTIALITY_EXTREME_SECURE = 2097152;

    public static final  long RIGHT_CREATE_INCOMING_EMPLOYEE = 2147483648l;
    public static final  long RIGHT_CREATE_OUTGOING_EMPLOYEE = 4294967296L;
    public static final  long RIGHT_ASAS = 8589934592L;
    public static final  long RIGHT_FAVOURITES = 17179869184L;
     public static final  long RIGHT_DELIVERYREPORT = 34359738368L;
    //public static final  long RIGHT_DELIVERYREPORT_CREATE = 34359738368L;
    //public static final  long RIGHT_DELIVERYREPORT_FIND = 68719476736L;
    public static final  long RIGHT_SEARCH_ADVANCED = 137438953472L;
    public static final  long RIGHT_ARCHIVE_OLD = 274877906944L;
    public static final  long RIGHT_SEARCH_ALL_DEPTS = 549755813888L;
    
    public static final long RIGHT_CREATE_INTERNAL_EMPLOYEE = 1099511627776L;

    public static final long RIGHT_CREATE_CIRCULARS_EMPLOYEE = 2199023255552L;
    public static final long RIGHT_CREATE_DECESIONS_EMPLOYEE = 4398046511104L;
    
    public static final long RIGHT_EDIT_DECISIONS = 8796093022208L;
    public static final long RIGHT_EDIT_CIRCULARS = 17592186044416L;

    public static final long RIGHT_TO_SEND_A_COPY_OF_CORRESPONDANCE = 35184372088832L;
    public static final long RIGHT_TO_REPLAY_ON_A_COPY_OF_CORRESPONDANCE = 70368744177664L;
    
   
    public static final long RIGHT_ReSend_INTERNAL = 140737488355328L;
    public static final long RIGHT_ReSend_INCOMING = 281474976710656L;
    public static final long RIGHT_ReSend_OUTGOING = 562949953421312L;
    public static final long RIGHT_ReSend_CIRCULARS = 1125899906842624L;
    public static final long RIGHT_ReSend_DECISIONS = 2251799813685248L;
    
    public static final long RIGHT_SET_OUT_OF_OFFICE = 4503599627370496L;
    public static final long RIGHT_CUSTOME_LIST = 9007199254740992L;
    
    
    
    
    

    
    public static final int FORWARDING_TO_LIST_DEFAULT = 1;
    public static final int FORWARDING_TO_LIST_CUSTOM = 2;
    public static final int FORWARDING_TO_LIST_BOTH = 3;

    private int employeeId;
    private int nationalNumber;
    private String fullName="";
    private String userId="";
    private int departmentId;
    private String departmentName="";
    
    
    private int managerId;
    private int parentDepartmentId;
        
    private String jobTitle="";
    private int isActive;
    private String emp_Email="";
    private String emp_Mobile="";
    private int useMobile;
    private String  specfialization="";
    private String  qualification="";
    private String degree="";
    
    private long enabledOptionsMask;
    
    private long enabledOptionsMaskVIP;
    
    public long getEnabledOptionsMaskVIP() {
		return enabledOptionsMaskVIP;
	}

	public void setEnabledOptionsMaskVIP(long enabledOptionsMaskVIP) {
		this.enabledOptionsMaskVIP = enabledOptionsMaskVIP;
	}

	private long deptEnabledOptionsMask;
    
    private String departmentCode="";
    private int forwaringToListType;
 
    
    private int backupEmployee;
    private String backupUserid="";
    private int backupStartDate;
    private int backupEndDate;
    private boolean isMOGroup;
    
    private String directManagerForwardingId;
    private Employee directManager;
    
    //mostafa amer 1/6/2016 added empty constructor to resolve json deserialization issue with deserializer object factory for employee class  
    public Employee(){
    	
    }

    public Employee( int employeeId , String fullName , long enabledOptionsMask)
    {
        this.setEmployeeId(employeeId);
        this.setFullName(fullName);
        this.setEnabledOptionsMask(enabledOptionsMask);
     }

    /** Creates a new instance of Employee */
    public Employee(int employeeId, int nationalNumber, String fullName, String userId, int departmentId, String departmentName,
                    String jobTitle, long enabledOptionsMask, String departmentCode, int isActive)
    {
        this(employeeId, nationalNumber, fullName, userId, departmentId, departmentName, jobTitle,
             enabledOptionsMask, departmentCode, Employee.FORWARDING_TO_LIST_DEFAULT,isActive);

    }

    /** Creates a new instance of Employee */
    public Employee(int employeeId,int nationalNumber,String fullName,String userId,int departmentId,String departmentName,String jobTitle,
    		int isActive,String emp_Email,
    	    String emp_Mobile,String  specfialization,String  qualification,String degree,long enabledOptionsMask,
    	    String departmentCode,int forwaringToListType,int backupEmployee,String backupUserid,
    	    int backupStartDate,int backupEndDate , int managerId,int parentDepartmentId)  // ,int managerId is it required?
    {
        this.setEmployeeId(employeeId);
        this.setNationalNumber(nationalNumber);
        this.setFullName(fullName);
        this.setUserId(userId);
        this.setDepartmentId(departmentId);
        this.setDepartmentName(departmentName);
        this.setJobTitle(jobTitle);
        
        this.setEnabledOptionsMask(enabledOptionsMask);
        this.setDepartmentCode(departmentCode);
        this.setForwaringToListType(forwaringToListType);
        this.setIsActive(isActive);
        
        
        this.setManagerId(managerId);
        this.setParentDepartmentId(parentDepartmentId);
		
      
        
        this.setEmp_Email(emp_Email);
        this.setEmp_Mobile(emp_Mobile);
        this.setSpecfialization(specfialization);
        this.setQualification(qualification);
        this.setDegree(degree);
         
      
        
        this.setBackupEmployee(backupEmployee);
        this.setBackupUserid(backupUserid);
        this.setBackupStartDate(backupStartDate);
        this.setBackupEndDate(backupEndDate);
        
    }

    
    public Employee(int employeeId, int nationalNumber, String fullName, String userId, int departmentId, String departmentName,
            String jobTitle, long enabledOptionsMask, String departmentCode, int forwaringToListType ,int isActive)
{
this.setEmployeeId(employeeId);
this.setNationalNumber(nationalNumber);
this.setFullName(fullName);
this.setUserId(userId);
this.setDepartmentId(departmentId);
this.setDepartmentName(departmentName);
this.setJobTitle(jobTitle);
this.setEnabledOptionsMask(enabledOptionsMask);
this.setDepartmentCode(departmentCode);
this.setForwaringToListType(forwaringToListType);
this.setIsActive(isActive);


} 
    
    
    /**
     * @return the employeeId
     */
    public int getEmployeeId()
    {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(int employeeId)
    {
        this.employeeId = employeeId;
    }

    /**
     * @return the nationalNumber
     */
    public int getNationalNumber()
    {
        return nationalNumber;
    }

    /**
     * @param nationalNumber the nationalNumber to set
     */
    public void setNationalNumber(int nationalNumber)
    {
        this.nationalNumber = nationalNumber;
    }

    /**
     * @return the fullName
     */
    public String getFullName()
    {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    /**
     * @return the userId
     */
    public String getUserId()
    {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    /**
     * @return the departmentId
     */
    public int getDepartmentId()
    {
        return departmentId;
    }

    /**
     * @param departmentId the departmentId to set
     */
    public void setDepartmentId(int departmentId)
    {
        this.departmentId = departmentId;
    }

    /**
     * @return the departmentName
     */
    public String getDepartmentName()
    {
        return departmentName;
    }

    /**
     * @param departmentName the departmentName to set
     */
    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    /**
     * @return the departmentCode
     */
    public String getDepartmentCode()
    {
        return departmentCode;
    }

    /**
     * @param departmentCode the departmentCode to set
     */
    public void setDepartmentCode(String departmentCode)
    {
        this.departmentCode = departmentCode;
    }

    /**
     * @return the jobTitle
     */
    public String getJobTitle()
    {
        return jobTitle;
    }

    /**
     * @param jobTitle the jobTitle to set
     */
    public void setJobTitle(String jobTitle)
    {
        this.jobTitle = jobTitle;
    }

    /**
     * @return the enabledOptionsMask
     */
    public long getEnabledOptionsMask()
    {
        return enabledOptionsMask;
    }

    /**
     * @param enabledOptionsMask the enabledOptionsMask to set
     */
    public void setEnabledOptionsMask(long enabledOptionsMask)
    {
        this.enabledOptionsMask = enabledOptionsMask;
    }

    public int compareTo(Employee o)
    {
        return this.getFullName().trim().compareTo(o.getFullName().trim());
    }

    /**
     * @return the forwaringToListType
     */
    public int getForwaringToListType()
    {
        return forwaringToListType;
    }

    /**
     * @param forwaringToList the forwaringToListType to set
     */
    public void setForwaringToListType(int forwaringToListType)
    {
        this.forwaringToListType = forwaringToListType;
    }

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public ArrayList<Integer> getManagedDepartments() {
		return managedDepartments;
	}

	public void setManagedDepartments(ArrayList<Integer> managedDepartments) {
		this.managedDepartments = managedDepartments;
	}

	public int getBackupEmployee() {
		return backupEmployee;
	}

	public void setBackupEmployee(int backupEmployee) {
		this.backupEmployee = backupEmployee;
	}

	public int getBackupStartDate() {
		return backupStartDate;
	}

	public void setBackupStartDate(int backupStartDate) {
		this.backupStartDate = backupStartDate;
	}

	public int getBackupEndDate() {
		return backupEndDate;
	}

	public void setBackupEndDate(int backupEndDate) {
		this.backupEndDate = backupEndDate;
	}

	
	
	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getEmp_Email() {
		return emp_Email;
	}

	public void setEmp_Email(String emp_Email) {
		this.emp_Email = emp_Email;
	}

	public String getEmp_Mobile() {
		return emp_Mobile;
	}

	public void setEmp_Mobile(String emp_Mobile) {
		this.emp_Mobile = emp_Mobile;
	}

	public String getSpecfialization() {
		return specfialization;
	}

	public void setSpecfialization(String specfialization) {
		this.specfialization = specfialization;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getBackupUserid() {
		return backupUserid;
	}

	public void setBackupUserid(String backupUserid) {
		this.backupUserid = backupUserid;
	}

	public int getParentDepartmentId() {
		return parentDepartmentId;
	}

	public void setParentDepartmentId(int parentDepartmentId) {
		this.parentDepartmentId = parentDepartmentId;
	}

	public int getUseMobile() {
		return useMobile;
	}

	public void setUseMobile(int useMobile) {
		this.useMobile = useMobile;
	}

	public long getDeptEnabledOptionsMask() {
		return deptEnabledOptionsMask;
	}

	public void setDeptEnabledOptionsMask(long deptEnabledOptionsMask) {
		this.deptEnabledOptionsMask = deptEnabledOptionsMask;
	}

	public Map<String, Boolean> getUserPermissions() {
		return userPermissions;
	}

	public void setUserPermissions(Map<String, Boolean> userPermissions) {
		this.userPermissions = userPermissions;
	}

	public boolean isMOGroup() {
		return isMOGroup;
	}

	public void setMOGroup(boolean isMOGroup) {
		this.isMOGroup = isMOGroup;
	}

	public Employee getDirectManager() {
		return directManager;
	}

	public void setDirectManager(Employee directManager) {
		this.directManager = directManager;
	}

	public String getDirectManagerForwardingId() {
		return directManagerForwardingId;
	}

	public void setDirectManagerForwardingId(String directManagerForwardingId) {
		this.directManagerForwardingId = directManagerForwardingId;
	}
	
}
