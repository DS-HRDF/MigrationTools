

-- DELETE IO_FORWARDINGDETAILS

PRINT 'Inserting into IO_FORWARDINGDETAILS'
PRINT GETDATE()

BEGIN TRY

INSERT INTO [dbo].[IO_FORWARDINGDETAILS]
           ([FORWARDINGID]
           ,[FORWARDINGTYPE]
           ,[PARTICIPANTUSERID]
           ,[PARTICIPANTDEPTID]
           ,[PARTICIPANTTYPE]
           ,[INSTRUCTION]
           ,[ISIGNORED]
           ,[IGNOREDBY]
           ,[HY]
           ,[sentFromInboxName]
           ,[IS_MIGRATED])

		 SELECT [FORWARDINGID]
		  ,[FORWARDINGTYPE]
		  --,[PARTICIPANTUSERID]
		  ,ISNULL([PARTICIPANTUSERID],'not_found_user')
		  --,[PARTICIPANTDEPTID]
		  ,ISNULL([PARTICIPANTDEPTID],'9999')
		  ,[PARTICIPANTTYPE]
		  ,[INSTRUCTION]
		  ,[ISIGNORED]
		  ,[IGNOREDBY]
		  ,[HY]
		  ,[sentFROMINboxName]
		  ,[IS_MIGRATED]
	  FROM [dbo].[MIG_IO_FORWARDINGDETAILS_VIEW]

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


