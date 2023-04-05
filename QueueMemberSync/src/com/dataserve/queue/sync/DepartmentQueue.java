package com.dataserve.queue.sync;

import java.io.Serializable;

/*
 * DepartmentQueue.java
 *
 * Created on 21 يناير, 2008, 06:07 م
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 ities;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class DepartmentQueue implements Serializable // modefied by Bashar
{
    private int departmentId;
    private String departmentNameAr;
    private String departmentNameEn;
    private String queueName;
    private String queueShortName;
    
    private String  applicationSpaceName;
    private String	roleName_English;
    private String	roleName_Arabic;
    private String WorkQueues;
    private String queueHash="";
    private String inbasketName="";
    private String roleName;
    private String InbasketFilter;


    public String getInbasketFilter() {
        return InbasketFilter;
    }

    public void setInbasketFilter(String inbasketFilter) {
        InbasketFilter = inbasketFilter;
    }

    public String getQueueHash() {
        return queueHash;
    }

    public void setQueueHash(String queueHash) {
        this.queueHash = queueHash;
    }

    public String getInbasketName() {
        return inbasketName;
    }

    public void setInbasketName(String inbasketName) {
        this.inbasketName = inbasketName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public DepartmentQueue(){
  }
    
    
    /** Creates a new instance of DepartmentQueue */
    public DepartmentQueue(int departmentId, String departmentNameAr, String departmentNameEn, String queueName, String queueShortName ,
    		  String applicationSpaceName,
    		  String	roleName_English,
    		  String	roleName_Arabic,
    		  String	WorkQueues
    		
    		)
    {
        this.setDepartmentId(departmentId);
        this.setDepartmentNameAr(departmentNameAr);
        this.setDepartmentNameEn(departmentNameEn);
        this.setQueueName(queueName);
        this.setQueueShortName(queueShortName);
        this.setApplicationSpaceName(applicationSpaceName);
        this.setRoleName_English(roleName_English);
        this.setRoleName_Arabic(roleName_Arabic);
        this.setWorkQueues(WorkQueues);
        
    }

    public int getDepartmentId()
    {
        return departmentId;
    }

    public void setDepartmentId(int departmentId)
    {
        this.departmentId = departmentId;
    }

    public String getQueueName()
    {
        return queueName;
    }

    public void setQueueName(String queueName)
    {
        this.queueName = queueName;
    }

    public String getDepartmentNameAr()
    {
        return departmentNameAr;
    }

    public void setDepartmentNameAr(String departmentNameAr)
    {
        this.departmentNameAr = departmentNameAr;
    }
    
    public String getDepartmentNameEn()
    {
        return departmentNameEn;
    }

    public void setDepartmentNameEn(String departmentNameEn)
    {
        this.departmentNameEn = departmentNameEn;
    }
    
    public String getQueueShortName()
    {
        return queueShortName;
    }

    public void setQueueShortName(String queueShortName)
    {
        this.queueShortName = queueShortName;
    }

	public String getApplicationSpaceName() {
		return applicationSpaceName;
	}

	public void setApplicationSpaceName(String applicationSpaceName) {
		this.applicationSpaceName = applicationSpaceName;
	}

	public String getRoleName_Arabic() {
		return roleName_Arabic;
	}

	public void setRoleName_Arabic(String roleName_Arabic) {
		this.roleName_Arabic = roleName_Arabic;
	}

	public String getWorkQueues() {
		return WorkQueues;
	}

	public void setWorkQueues(String workQueues) {
		WorkQueues = workQueues;
	}

	public String getRoleName_English() {
		return roleName_English;
	}

	public void setRoleName_English(String roleName_English) {
		this.roleName_English = roleName_English;
	}
}
