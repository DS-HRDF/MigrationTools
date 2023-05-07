
BEGIN TRY

INSERT INTO [dbo].[IO_FULL_SEARCH_CORRESPONDENCE]
           ([CORRESPONDENCENUMBER]
           ,[HIJRICYEAR]
           ,[CORRESPONDENCETYPE]
           ,[MINISTEROFFICENUMBER]
           ,[CREATIONTYPE]
           ,[CORRESPONDENCEDATE]
           ,[CORRESPONDENCESUBJECT]
           ,[EXTERNALNUMBER]
           ,[EXTERNALUNITID]
           ,[EXTERNALUNITDESC]
           ,[CREATOR]
           ,[CONFIDENTIALITYID]
           ,[WF_LAUNCHED]
           ,[CORRESPONDENCESUBJECTSEARCH]
           ,[EXTERNALUNITDESCSEARCH]
		   ,CREATORDEPARTMENT
		   ,CORRESPONDENCENUMBERSEARCHABLE
		   ,SEARCHALLFILEDS
			)
	 SELECT 

           CORRESPONDENCENUMBER
           ,HIJRICYEAR
           ,44
           ,MINISTEROFFICENUMBER
           ,CASE WHEN MINISTEROFFICENUMBER IS NULL THEN 1 ELSE  2 END
           ,CORRESPONDENCEDATE
           ,CORRESPONDENCESUBJECT
		   --,replace(replace(replace(replace(replace(replace(CORRESPONDENCESUBJECT, 'أ', 'ا'), 'إ', 'ا'), 'ة', 'ه'), 'ي', 'ى'), 'ئ', 'ا'), 'ت', 'ه')
           ,NULL--,EXTERNALNUMBER
           ,NULL--,EXTERNALUNITID
           ,NULL--,EXTERNALUNITDESC
           ,SENTBY
           ,CONFIDENTIALITYID
           ,WF_LAUNCHED
           ,ISNULL(SearchableSUBJECT, '')
		   --,REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(SUBSTRING (SearchableSUBJECT,1,899), 'أ', 'ا'), 'إ', 'ا'), 'ة', 'ه'), 'ي', 'ى'), 'ئ', 'ا'), 'ت', 'ه')
           ,NULL--,EXTERNALUNITDESCSEARCH
		   ,SENTBYDEPARTMENTID
		   ,CASE WHEN MINISTEROFFICENUMBER IS NULL THEN CAST(CORRESPONDENCENUMBER  AS VARCHAR) ELSE  CAST(MINISTEROFFICENUMBER  AS VARCHAR) END
		   --,replace(replace(replace(replace(replace(replace(SUBSTRING(CORRESPONDENCESUBJECT, 1, 899), 'أ', 'ا'), 'إ', 'ا'), 'ة', 'ه'), 'ي', 'ى'), 'ئ', 'ا'), 'ت', 'ه')
		   ,CONCAT(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(SUBSTRING(ISNULL(CORRESPONDENCESUBJECT, ''), 1, 899), 'أ', 'ا'), 'إ', 'ا'), 'ة', 'ه'), 'ي', 'ى'), 'ئ', 'ا'), 'ت', 'ه'), ' ', CORRESPONDENCENUMBER)
		   FROM IO_KARAR
		   --where HIJRICYEAR >= 1440
		   ORDER BY HIJRICYEAR,CORRESPONDENCENUMBER

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