
PRINT 'Inserting into IO_DEPARTMENTQUEUES'
PRINT GETDATE()


BEGIN TRY

INSERT INTO [IO_DEPARTMENTQUEUES]
           ([DEPARTMENTID]
           ,[DEPARTMENTNAMEAR]
           ,[DEPARTMENTNAMEEN]
           ,[QUEUENAME]
           ,[QUEUESHORTNAME]
           ,[QUEUEHIJRICREATEDATE]
           ,[DEPARTMENTQueueNAMEAR]
           ,[DEPARTMENTQueueNAMEEN]
           ,[WORKQUEUES]
           ,[applicationSpaceName]
           ,[roleName_English]
           ,[roleName_Arabic]
           ,[IS_MIGRATED])
     SELECT [DEPARTMENTID]
      ,[DEPARTMENTNAME]
      ,[DEPARTMENTNAMEEN]
      ,[QUEUENAME]
      ,[QUEUESHORTNAME]
      ,[QUEUEHIJRICREATEDATE]
      ,[DEPARTMENTQueueNAMEAR]
      ,[DEPARTMENTQueueNAMEEN]
      ,[WORKQUEUES]
      ,[applicationSpaceName]
      ,[roleName_English]
      ,[roleName_Arabic]
      ,[IS_MIGRATED]
  FROM [MIG_IO_DEPARTMENTQUEUES_VIEW]

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


