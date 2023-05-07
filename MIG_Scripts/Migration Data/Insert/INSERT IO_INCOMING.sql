

--DELETE IO_INCOMING

PRINT 'Inserting into IO_INCOMING'
PRINT GETDATE()

BEGIN TRY

INSERT INTO [dbo].[IO_INCOMING]
           ([CORRESPONDENCENUMBER]
           ,[HIJRICYEAR]
           ,[CORRESPONDENCEDATE]
           ,[CORRESPONDENCETYPEID]
           ,[CORRESPONDENCECATEGORYID]
           ,[CORRESPONDENCESUBJECT]
           ,[REMARKS]
           ,[EXTERNALNUMBER]
           ,[EXTERNALDATE]
           ,[CORRESPONDENCESOURCEID]
           ,[RECEIVEMODEID]
           ,[ISARCHIVED]
           ,[CORRESPONDENCEATTACHMENTS]
           ,[RECEIVEDBY]
           ,[RECEIVEDBYDEPARTMENTID]
           ,[WF_LAUNCHED]
           ,[CONFIDENTIALITYID]
           ,[SENDERDETAILS]
           ,[ISEXPORTED]
           ,[MINISTEROFFICENUMBER]
           ,[CORRESPONDENCESOURCETYPE]
           ,[OWNERDEPARTMENTID]
           ,[RECORDNUMBER]
           ,[SearchableSubject]
           ,[SearchableSENDERDETAILS]
           ,[CIVIL_RECORD]
           ,[COMMERCE_RECORD]
           ,[MOBILE_NUMBER]
           ,[EMAIL]
           ,[SYSTEM_OWNER]
           ,[CREATIONDATE]
           ,[IS_ENCRYPTED]
           ,[DecisionDate]
           ,[DecisionNumber]
           ,[IS_MIGRATED])
     
		 SELECT [CORRESPONDENCENUMBER]
			  ,[HIJRICYEAR]
			  ,[CORRESPONDENCEDATE]
			  ,[CORRESPONDENCETYPEID]
			  ,[CORRESPONDENCECATEGORYID]
			  ,[CORRESPONDENCESUBJECT]
			  ,[REMARKS]
			  ,[EXTERNALNUMBER]
			  ,[EXTERNALDATE]
			  ,[CORRESPONDENCESOURCEID]
			  ,[RECEIVEMODEID]
			  ,[ISARCHIVED]
			  ,[CORRESPONDENCEATTACHMENTS]
			  ,[RECEIVEDBY]
			  ,[RECEIVEDBYDEPARTMENTID]
			  ,[WF_LAUNCHED]
			  ,[CONFIDENTIALITYID]
			  ,[SENDERDETAILS]
			  ,[ISEXPORTED]
			  ,[MINISTEROFFICENUMBER]
			  ,[CORRESPONDENCESOURCETYPE]
			  ,[OWNERDEPARTMENTID]
			  ,[RECORDNUMBER]
			  ,[SearchableSubject]
			  ,[SearchableSENDERDETAILS]
			  ,[CIVIL_RECORD]
			  ,[COMMERCE_RECORD]
			  ,[MOBILE_NUMBER]
			  ,[EMAIL]
			  ,[SYSTEM_OWNER]
			  ,[CREATIONDATE]
			  ,[IS_ENCRYPTED]
			  ,[DecisionDate]
			  ,[DecisionNumber]
			  ,[IS_MIGRATED]
		  FROM [dbo].[MIG_IO_INCOMING_VIEW]
	  WHERE HIJRICYEAR = 1444

END TRY  
BEGIN CATCH

        SELECT  
            ERROR_NUMBER() AS ErrorNumber  
            ,ERROR_SEVERITY() AS ErrorSeverity  
            ,ERROR_STATE() AS ErrorState  
            ,ERROR_PROCEDURE() AS ErrorProcedure  
            ,ERROR_LINE() AS ErrorLine  
            ,ERROR_MESSAGE() AS ErrorMessage;  
END CATCH


