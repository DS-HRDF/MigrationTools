

--DELETE IO_DEPARTMENTS
--WHERE DEPARTMENTID NOT IN (1000000,9999)

PRINT 'Inserting into IO_DEPARTMENTS'
PRINT GETDATE()


BEGIN TRY

	INSERT INTO [dbo].[IO_DEPARTMENTS]
           ([DEPARTMENTID]
           ,[DEPARTMENTNAME]
           ,[MANAGERID]
           ,[REPRESENTATIVEID]
           ,[PARENTDEPARTMENTID]
           ,[DEPARTMENTLEVEL]
           ,[CANACCESSSYSTEM]
           ,[DEPARTMENTCODE]
           ,[ISACTIVE]
           ,[ALIASNAME]
           ,[ISPUBLICRECEIVER]
           ,[sectorNumber]
           ,[departmentType]
           ,[divisionNumber]
           ,[LocationID]
           ,[ISMINISTEROFFICE]
           ,[IS_HIDDEN]
           ,[GeneralAdministration]
           ,[MigratedId]
           ,[IS_MIGRATED])

     SELECT [DEPARTMENTID]
      ,[DEPARTMENTNAME]
      ,[MANAGERID]
      ,[REPRESENTATIVEID]
      ,[PARENTDEPARTMENTID]
      ,[DEPARTMENTLEVEL]
      ,[CANACCESSSYSTEM]
      ,[DEPARTMENTCODE]
      ,[ISACTIVE]
      ,[ALIASNAME]
      ,[ISPUBLICRECEIVER]
      ,[sectorNumber]
      ,[departmentType]
      ,[divisionNumber]
      ,[LocationID]
      ,[ISMINISTEROFFICE]
      ,[IS_HIDDEN]
      ,[GeneralAdministration]
	  ,[OLD_SEQ_DEPARTMENT_ID]
      ,[IS_MIGRATED]
  FROM [MOAMALAT].[dbo].[MIG_IO_DEPARTMENTS_VIEW]

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




/*    ------ UPDATE MANAGERID,REPRESENTATIVEID
UPDATE IO_DEPARTMENTS
SET IO_DEPARTMENTS.MANAGERID = c.EMPLOYEEID,IO_DEPARTMENTS.REPRESENTATIVEID = c.EMPLOYEEID
FROM(
SELECT v.EMPLOYEEID,a.DEPARTMENTID FROM(
SELECT depts.DEPARTMENTID
,COUNT(empV.EMPLOYEEID) _c
FROM MIG_IO_EMPLOYEES_VIEW empV
LEFT JOIN IO_DEPARTMENTS depts ON empV.DEPARTMENTID = depts.DEPARTMENTID
WHERE PRIVILEGE_ID = 1189
GROUP BY depts.DEPARTMENTID
HAVING COUNT(*) = 1 --- change this number
) AS a
JOIN MIG_IO_EMPLOYEES_VIEW v on v.DEPARTMENTID = a.DEPARTMENTID
WHERE v.PRIVILEGE_ID = 1189 --- change this number for specific privilege
AND v.DEPARTMENTID = a.DEPARTMENTID
) AS c
WHERE c.DEPARTMENTID = IO_DEPARTMENTS.DEPARTMENTID


*/