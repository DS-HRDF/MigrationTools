
-- DELETE IO_RELATIONS

PRINT 'Inserting into IO_RELATIONS'
PRINT GETDATE()

BEGIN TRY

INSERT INTO [dbo].[IO_RELATIONS]
           ([CORRESPONDENCENUMBER]
           ,[HIJRICYEAR]
           ,[TYPEID]
           ,[RELATEDTONUMBER]
           ,[RELATEDTOHIJRICYEAR]
           ,[RELATEDTOTYPEID]
           ,[RELATIONTYPEID]
           ,[RECORDNUMBER]
           ,[RELATEDBY]
           ,[RELATIONDATE]
           ,[IS_MIGRATED])
     
		 SELECT [CORRESPONDENCENUMBER]
			  ,[HIJRICYEAR]
			  ,[TYPEID]
			  ,[RELATEDTONUMBER]
			  ,[RELATEDTOHIJRICYEAR]
			  ,[RELATEDTOTYPEID]
			  ,[RELATIONTYPEID]
			  ,[RECORDNUMBER]
			  ,[RELATEDBY]
			  ,[RELATIONDATE]
			  ,[IS_MIGRATED]
		  FROM [dbo].[MIG_IO_RELATIONS_VIEW]
		  WHERE 
		  [HIJRICYEAR] >= 1442


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


--------------------------------------------------------------------------------------------------------------


-- DELETE IO_RELATIONS

PRINT 'Inserting into IO_RELATIONS'
PRINT GETDATE()

BEGIN TRY

INSERT INTO [dbo].[IO_RELATIONS]
           ([CORRESPONDENCENUMBER]
           ,[HIJRICYEAR]
           ,[TYPEID]
           ,[RELATEDTONUMBER]
           ,[RELATEDTOHIJRICYEAR]
           ,[RELATEDTOTYPEID]
           ,[RELATIONTYPEID]
           ,[RECORDNUMBER]
           ,[RELATEDBY]
           ,[RELATIONDATE]
           ,[IS_MIGRATED])
     
		 SELECT 
			a.CORRESPONDENCENUMBER
			,a.[HIJRICYEAR]
			,a.[TYPEID]
			,a.[RELATEDTONUMBER]
			,a.[RELATEDTOHIJRICYEAR]
			,a.[RELATEDTOTYPEID]
			,a.[RELATIONTYPEID]
			,a.[RECORDNUMBER]
			,a.[RELATEDBY]
			,a.[RELATIONDATE]
			,a.[IS_MIGRATED]
	  
			FROM(

			( SELECT 
					COUNT(*) _count,
				   [CORRESPONDENCENUMBER]
				  ,[HIJRICYEAR]
				  ,[TYPEID]
				  ,[RELATEDTONUMBER]
				  ,[RELATEDTOHIJRICYEAR]
				  ,[RELATEDTOTYPEID]
				  ,[RELATIONTYPEID]
				  ,[RECORDNUMBER]
				  ,[RELATEDBY]
				  ,[RELATIONDATE]
				  ,[IS_MIGRATED]
			  FROM [dbo].[MIG_IO_RELATIONS_VIEW] 
			  --where RELATEDTONUMBER = 12751 and RELATEDTOHIJRICYEAR = 1441 and RELATEDTOTYPEID = 1
			  GROUP BY 
			  [CORRESPONDENCENUMBER]
				  ,[HIJRICYEAR]
				  ,[TYPEID]
				  ,[RELATEDTONUMBER]
				  ,[RELATEDTOHIJRICYEAR]
				  ,[RELATEDTOTYPEID]
				  ,[RELATIONTYPEID]
				  ,[RECORDNUMBER]
				  ,[RELATEDBY]
				  ,[RELATIONDATE]
				  ,[IS_MIGRATED]
			  HAVING  COUNT(*) > 1)
			  ) 
			  AS a
			  WHERE HIJRICYEAR >= 1420


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


