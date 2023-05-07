

--DELETE IO_INTERNAL

PRINT 'Inserting into IO_INTERNAL'
PRINT GETDATE()

BEGIN TRY

INSERT INTO [dbo].[IO_INTERNAL]
           ([CORRESPONDENCENUMBER]
           ,[HIJRICYEAR]
           ,[CORRESPONDENCEDATE]
           ,[CORRESPONDENCETYPEID]
           ,[CORRESPONDENCECATEGORYID]
           ,[CORRESPONDENCESUBJECT]
           ,[REMARKS]
           ,[CORRESPONDENCEATTACHMENTS]
           ,[SENTBY]
           ,[SENTBYDEPARTMENTID]
           ,[CONFIDENTIALITYID]
           ,[WF_LAUNCHED]
           ,[ISEXPORTED]
           ,[MINISTEROFFICENUMBER]
           ,[lastForwardingDate]
           ,[SearchableSUBJECT]
           ,[SYSTEM_OWNER]
           ,[CREATIONDATE]
           ,[IS_MIGRATED]
		   ,[OLD_DOC_ID])
     
		 SELECT [CORRESPONDENCENUMBER]
		  ,[HIJRICYEAR]
		  ,[CORRESPONDENCEDATE]
		  ,[CORRESPONDENCETYPEID]
		  ,[CORRESPONDENCECATEGORYID]
		  ,[CORRESPONDENCESUBJECT]
		  ,[REMARKS]
		  ,[CORRESPONDENCEATTACHMENTS]
		  ,[SENTBY]
		  ,[SENTBYDEPARTMENTID]
		  ,[CONFIDENTIALITYID]
		  ,[WF_LAUNCHED]
		  ,[ISEXPORTED]
		  ,[MINISTEROFFICENUMBER]
		  ,[lastForwardingDate]
		  ,[SearchableSubject]
		  ,[SYSTEM_OWNER]
		  ,[CREATIONDATE]
		  ,[IS_MIGRATED]
		  ,[OLD_DOC_ID]
	  FROM [dbo].[MIG_IO_INTERNAL_VIEW]
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


