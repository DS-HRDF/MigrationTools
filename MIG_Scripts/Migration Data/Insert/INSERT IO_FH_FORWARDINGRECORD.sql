
-- DELETE IO_FH_FORWARDINGRECORD;

PRINT 'Inserting into IO_FH_FORWARDINGRECORD'
PRINT GETDATE()

SET IDENTITY_INSERT [dbo].[IO_FH_FORWARDINGRECORD] ON

BEGIN TRY

	INSERT INTO [dbo].[IO_FH_FORWARDINGRECORD] 
	(
	--1
	[FORWARDINGID], 
	--2
	[FullSearchID],
	--3
	[CORRESPONDENCENUMBER],
	--4
	[HIJRICYEAR],
	--5
	[TYPEID],
	--6
	[URGENCYLEVEL],
	--7
	[IMPORTANCELEVEL],
	--8
	[REMINDER],
	--9
	[DEADLINE],
	--10
	[FORWARDINGTIME],
	--11
	[H_FORWARDINGDATE],
	--12
	[H_FORWARDINGDATEAsNumber],
	--13
	[PublicINSTRUCTIONS],
	--14
	[MINISTER_FORWARDINGID],
	--15
	[ISIGNORED],
	--16
	[IGNOREDBY],
	--17
	[ATTACHMENTS],
	--18
	[H_CORRESPONDENCESUBJECT],
	--19
	[H_MINISTEROFFICENUMBER],
	--20
	[FromParticipentType],
	--21
	[FromParticipentUserID],
	--22
	[FromParticpentDepartmentID],
	--23
	[FromParticipentQueue],
	--24
	[IS_SIGN_REQUEST],
	--25
	[VIP_ATTACHMENT],
	--26
	[VIP_PRESENTED_TYPE],
	--27
	[PreviousFORWARDINGID],
	--28
	[actionPageSource],
	--29
	[DeviceSource],
	--30
	[ForwardingActionType],
	--31
	[insertedDate],
	--32
	[sentFromInboxNameASIS],
	--33
	[PreviousForwardingDetailsID],
	--34
	FROMMIGRATION,
	--35
	IS_MIGRATED
	)

	SELECT
	--1
	oldFromH.FORWARDINGID, 
	--2
	0,
	--3
	oldFromH.CORRESPONDENCENUMBER,
	--4
	oldFromH.HIJRICYEAR,
	--5
	oldFromH.TYPEID,
	--6
	oldFromH.URGENCYLEVEL,
	--7
	oldFromH.IMPORTANCELEVEL,
	--8
	CASE
		WHEN ((oldFromH.REMINDER is not null) AND (oldFromH.REMINDER<> '') AND (oldFromH.REMINDER<> '1')) THEN (CONVERT(datetime, dbo.Datetime_Manager_Function('dateTimeFHRDFromHtoG', oldFromH.REMINDER)))
	 ELSE (null)
	END As [REMINDER],
	--9
	CASE
		WHEN ((oldFromH.DEADLINE is not null) AND (oldFromH.DEADLINE<> '') AND (oldFromH.DEADLINE<> '1')) THEN (CONVERT(datetime, dbo.Datetime_Manager_Function('dateTimeFHRDFromHtoG', oldFromH.DEADLINE)))
		ELSE (null)
	END As [DEADLINE],
	--10
	CONVERT(DATETIME, dbo.Datetime_Manager_Function('dateTimeFHFromGToH', oldFromH.FORWARDINGTIME)) AS [FORWARDINGTIME],
	--11
	CASE WHEN oldFromH.forwardingdate IS NULL 
		THEN 
			CONCAT(SUBSTRING(oldfromH.forwardingtime,7,4),'/',SUBSTRING(oldfromH.forwardingtime,4,2),'/',SUBSTRING(oldfromH.forwardingtime,1,2))
		ELSE
			CONCAT(CAST(oldFromH.forwardingdate AS INT)/ 10000
					,'/'
					,RIGHT(CAST(oldFromH.forwardingdate AS INT)/ 100,2)
					,'/'
					,RIGHT(oldFromH.forwardingdate,2))
		END,
	--12
	ISNULL(oldFromH.forwardingdate,
		CAST(CONCAT(SUBSTRING(oldfromH.forwardingtime,7,4),SUBSTRING(oldfromH.forwardingtime,4,2),SUBSTRING(oldfromH.forwardingtime,1,2)) AS NUMERIC)
	),
	--13
	oldFromH.INSTRUCTIONS,
	--14
	oldFromH.MINISTER_FORWARDINGID,
	--15
	oldFromH.ISIGNORED,
	--16
	oldFromH.IGNOREDBY,
	--17
	NULL,
	--18
	NULL,
	--19
	NULL,
	--20
	1,
	--21
	oldFromD.PARTICIPANTUSERID As [PARTICIPANTUSERID],
	--22
	oldFromD.PARTICIPANTDEPTID,
	--23
	NULL,
	--24
	NULL,
	--25
	NULL,
	--26
	NULL,
	--27
	0,
	--28
	12,
	--29
	1,
	--30
	1,
	--31
	GETDATE(),
	--32
	NULL,
	--33
	NULL,
	--34
	1,
	--35
	1

	FROM [IO_FORWARDINGSHISTORY] oldFromH With (NOLOCK)

	INNER JOIN IO_FORWARDINGDETAILS oldFromD 
		On oldFromD.FORWARDINGID= oldFromH.FORWARDINGID AND forwardingtype = 1 
		AND oldFromD.ISIGNORED = 0

	WHERE
	 oldFromD.FORWARDINGTYPE= 1
	 AND oldFromH.ISIGNORED = 0

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


SET IDENTITY_INSERT [dbo].[IO_FH_FORWARDINGRecord] OFF


