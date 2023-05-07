
--DELETE IO_FORWARDINGSHISTORY

PRINT 'Inserting into IO_FORWARDINGSHISTORY'
PRINT GETDATE()

BEGIN TRY

INSERT INTO [dbo].[IO_FORWARDINGSHISTORY]
           (
		   [FORWARDINGID],
		   [CORRESPONDENCENUMBER]
           ,[HIJRICYEAR]
           ,[TYPEID]
           ,[URGENCYLEVEL]
           ,[IMPORTANCELEVEL]
           ,[REMINDER]
           ,[DEADLINE]
           ,[FORWARDINGTIME]
           ,[INSTRUCTIONS]
           ,[MINISTER_FORWARDINGID]
           ,[ISIGNORED]
           ,[IGNOREDBY]
           ,[FORWARDINGDATE]
           ,[ATTACHMENTS]
           ,[DEADLINE_backup]
           ,[H_CORRESPONDENCESUBJECT]
           ,[H_SENTBY]
           ,[H_MINISTEROFFICENUMBER]
           ,[IS_SIGN_REQUEST]
           ,[VIP_ATTACHMENT]
           ,[VIP_PRESENTED_TYPE]
           ,[LETTER_ATTACHMENT]
           ,[IS_CREATED]
           ,[IS_MIGRATED]
           ,[OLD_DOC_ID])
     
			 SELECT [FORWARDINGID]
			  ,[CORRESPONDENCENUMBER]
			  ,[HIJRICYEAR]
			  ,[TYPEID]
			  ,[URGENCYLEVEL]
			  ,[IMPORTANCELEVEL]
			  ,[REMINDER]
			  ,[DEADLINE]
			  ,[FORWARDINGTIME]
			  ,[INSTRUCTIONS]
			  ,[MINISTER_FORWARDINGID]
			  ,[ISIGNORED]
			  ,[IGNOREDBY]
			  ,[FORWARDINGDATE]
			  ,[ATTACHMENTS]
			  ,[DEADLINE_backup]
			  ,[H_CORRESPONDENCESUBJECT]
			  ,[H_SENTBY]
			  ,[H_MINISTEROFFICENUMBER]
			  ,[IS_SIGN_REQUEST]
			  ,[VIP_ATTACHMENT]
			  ,[VIP_PRESENTED_TYPE]
			  ,[LETTER_ATTACHMENT]
			  ,[IS_CREATED]
			  ,[IS_MIGRATED]
			  ,[OLD_DOC_ID]
		  FROM [dbo].[MIG_IO_FORWARDINGSHISTORY_VIEW]

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



