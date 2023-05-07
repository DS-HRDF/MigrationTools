
--DELETE IO_OUTGOING

PRINT 'Inserting into IO_OUTGOING'
PRINT GETDATE()

BEGIN TRY

INSERT INTO [dbo].[IO_OUTGOING]
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
           ,[PREPAREDBYDEPARTMENTID]
           ,[MINISTEROFFICENUMBER]
           ,[lastForwardingDate]
           ,[SearchableSubject]
           ,[CREATIONDATE]
           ,[IS_ENCRYPTED]
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
		  ,[PREPAREDBYDEPARTMENTID]
		  ,[MINISTEROFFICENUMBER]
		  ,[lastForwardingDate]
		  ,[SearchableSubject]
		  ,[CREATIONDATE]
		  ,[IS_ENCRYPTED]
		  ,[IS_MIGRATED]
		  ,[OLD_DOC_ID]
	  FROM [dbo].[MIG_IO_OUTGOING_VIEW]
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

