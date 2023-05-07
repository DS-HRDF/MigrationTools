
--DELETE IO_EMPLOYEES
--WHERE USERID NOT IN('HDF.SRV.SD.FNT.X1','r.torkey-c','not_found_user')

PRINT 'Inserting into IO_EMPLOYEES'
PRINT GETDATE()


BEGIN TRY

	INSERT INTO [dbo].[IO_EMPLOYEES]
           ([EMPLOYEEID]
           ,[NATIONALNUMBER]
           ,[FULLNAME]
           ,[USERID]
           ,[DEPARTMENTID]
           ,[JOBTITLE]
           ,[ISACTIVE]
           ,[NOTHAVINGADACCOUNT]
           ,[USE_MOBILE]
           ,[EMP_Email]
           ,[EMP_Mobile]
           ,[SPECIALIZATION]
           ,[QUALIFICATION]
           ,[DEGREE]
           ,[EXT]
           ,[IS_HIDDEN]
           ,[MIGRATED_EMP_ID]
           ,[IS_MIGRATED]
           ,[MigratedId])
     
	 SELECT [EMPLOYEEID]
      ,[NATIONALNUMBER]
      ,[FULLNAME]
      ,[USERID]
      ,[DEPARTMENTID]
      ,[JOBTITLE]
      ,[ISACTIVE]
      ,[NOTHAVINGADACCOUNT]
      ,[USE_MOBILE]
      ,[EMP_Email]
      ,[EMP_Mobile]
      ,[SPECIALIZATION]
      ,[QUALIFICATION]
      ,[DEGREE]
      ,[EXT]
      ,[IS_HIDDEN]
      ,[MIGRATED_EMP_ID]
      ,[IS_MIGRATED]
      ,[MIGRATED_EMP_ID]
  FROM [dbo].[MIG_IO_EMPLOYEES_VIEW]

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



