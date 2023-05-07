
-- DELETE MIG_MIGRATION_DATA WHERE message = 'UNDONE'

PRINT 'Inserting into MIG_MIGRATION_DATA'
PRINT GETDATE()

BEGIN TRY

INSERT INTO [dbo].[MIG_MIGRATION_DATA]
           ([cn]
           ,[hy]
           ,[typeid]
           ,[docid]
           ,[fileName]
           ,[docTitle]
           ,[classId]
           ,[mimeType]
           ,[createDate]
           ,[username]
           ,[status]
           ,[message]
           ,[documentPath]
           ,[documentSize]
           ,[migrated_docid])

     SELECT [cn]
      ,[hy]
      ,[typeid]
      ,[docid]
      ,[fileName]
      ,[docTitle]
      ,[classId]
      ,[mimeType]
      ,[createDate]
      ,[username]
      ,[status]
      ,[message]
      ,[documentPath]
      ,[documentSize]
      ,[documentId]
  FROM [dbo].[MIG_DOCUMENTS_VIEW]
  WHERE hy = 1444

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


