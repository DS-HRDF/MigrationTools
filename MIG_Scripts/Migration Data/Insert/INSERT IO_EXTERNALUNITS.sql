
--DELETE IO_EXTERNALUNITS

PRINT 'Inserting into IO_EXTERNALUNITS'
PRINT GETDATE()

BEGIN TRY


INSERT INTO [dbo].[IO_EXTERNALUNITS]
           ([EXTERNALUNITID]
           ,[EXTERNALUNITDESC]
           ,[EXTERNALUNITTYPE]
           ,[MOCODE]
           ,[ISACTIVE]
           ,[YESSERUNITID]
           ,[YESSER_AVAILABLE]
           ,[category]
           ,[EXTERNALUNITDESCSEARCH]
           ,[IS_MIGRATED])
     
	 SELECT [EXTERNALUNITID]
      ,[EXTERNALUNITDESC]
      ,[EXTERNALUNITTYPE]
      ,[MOCODE]
      ,[ISACTIVE]
      ,[YESSERUNITID]
      ,[YESSER_AVAILABLE]
      ,[category]
      ,[EXTERNALUNITDESCSEARCH]
      ,[IS_MIGRATED]
	FROM [MOAMALAT].[dbo].[MIG_IO_EXTERNALUNITS_VIEW]

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
