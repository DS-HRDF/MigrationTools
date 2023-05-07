CREATE OR ALTER PROCEDURE [MIG_GET_MIGRATION_DATA] 
AS 
BEGIN

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
	  ,[migrated_docid]
	FROM [MIG_MIGRATION_DATA];

END