
--DELETE FROM [IO_FH_FORWARDINGDestination]

BEGIN

SET IDENTITY_INSERT [dbo].[IO_FH_FORWARDINGDESTINATION] ON



    BEGIN TRY

		INSERT INTO [dbo].[IO_FH_FORWARDINGDestination]
		(
			[FORWARDINGDETAILID]
		   ,[FORWARDINGID]
           ,[RecipentTYPE]
           ,[ParticipentType]
           ,[ParticipentUserID]
           ,[ParticpentDepartmentID]
           ,[ParticipentQueue]
           ,[PrivateINSTRUCTION]
           ,[ISIGNORED]
           ,[IGNOREDBY]
           ,[HY]
           ,[sentFromInboxName_notUsed]
           ,[NextForwardingID]
           ,[NextActionType]
           ,[insertedDate]
		   ,IS_MIGRATED
		   )

		SELECT 
			   oldFromD.[FORWARDINGDETAILID]
			  ,oldFromD.[FORWARDINGID]
			  ,oldFromD.[FORWARDINGTYPE]
			  ,oldfromd.PARTICIPANTTYPE
			  ,oldFromD.PARTICIPANTUSERID As [PARTICIPANTUSERID]
			  ,[PARTICIPANTDEPTID]
			 , CASE WHEN PARTICIPANTTYPE = 3 THEN (
					CASE WHEN PARTICIPANTUSERID LIKE 'Q%' OR  PARTICIPANTUSERID LIKE '#%' THEN PARTICIPANTUSERID
				 ELSE NULL END)
				END
			  ,oldFromD.[INSTRUCTION]
			  ,oldFromD.[ISIGNORED]
			  ,oldFromD.[IGNOREDBY]
			  ,oldFromH.HIJRICYEAR
			  ,NULL
			  ,-1
			  ,-1
			  ,GETDATE()
			  ,1

				FROM [io_forwardingshistory] oldFromH WITH (NOLOCK)
				INNER JOIN IO_FORWARDINGDETAILS oldFromD ON oldFromD.FORWARDINGID= oldFromH.FORWARDINGID

				Where 
				oldFromH.ISIGNORED= 0
				And oldFromD.FORWARDINGTYPE IN (2, 3)
				AND oldfromd.ISIGNORED = 0
				AND oldFromD.IS_MIGRATED = 1
				AND oldFromH.IS_MIGRATED = 1

				ORDER BY oldFromD.FORWARDINGID ASC

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

SET IDENTITY_INSERT [dbo].[IO_FH_FORWARDINGDestination] OFF

END