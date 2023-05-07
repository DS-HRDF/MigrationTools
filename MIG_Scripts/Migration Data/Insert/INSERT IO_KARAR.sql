
-- DELETE IO_KARAR

PRINT 'Inserting into IO_KARAR'
PRINT GETDATE()

BEGIN TRY

INSERT INTO [dbo].[IO_KARAR]
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
           ,[SearchableSubject]
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
		  ,[SearchableSubject]
		  ,[CREATIONDATE]
		  ,[IS_MIGRATED]
		  ,[OLD_DOC_ID]
	  FROM [dbo].[MIG_IO_KARAR_VIEW]

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


