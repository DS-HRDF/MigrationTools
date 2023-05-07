
CREATE OR ALTER VIEW MIG_WORKITEMS_VIEW
AS

SELECT
		trans.TSF_ID AS FORWARDINGID,
		transferToEmps.USERID AS F_BoundUser,
		documents.DOC_SUBJECT AS F_Subject,
		ISNULL(CAST(FORMAT(CAST(documents.DOC_REGISTERDATE AS DATE), 'yyyyMMdd', 'ar-SA' ) AS NUMERIC),'-1') AS CORRESPONDENCEDATE,
		ISNULL(transferFromEmps.FULLNAME,transferFromDepts.DEPARTMENTNAME) AS FromUser,
		ISNULL(CAST(SUBSTRING(ISNULL(CAST(FORMAT(CAST(documents.DOC_REGISTERDATE AS DATE), 'yyyyMMdd', 'ar-SA') AS NVARCHAR),'-1'),0,5) AS NUMERIC),-1) AS HijricYear,
		documents.DOC_PRIORITY AS ImportanceLevel,
		CASE 
			WHEN trans.TSF_ISREAD = 1
				THEN 1
			ELSE 0
		END AS isRead,
		CASE 
			WHEN documents.DOC_CATEGORY = 1
				THEN 1
			WHEN documents.DOC_CATEGORY = 5
				THEN 2
			WHEN documents.DOC_CATEGORY = 7
				THEN 3
			WHEN documents.DOC_CATEGORY = 13   ---- Decisions - Internal Memo
				THEN 44
			WHEN documents.DOC_CATEGORY = 11   ---- Circulars - Internal Broadcast
				THEN 55
		ELSE   -1
		END AS ItemType,
		ISNULL(transferFromEmps.USERID,'HDF.SRV.SD.FNT.X1') AS Originator,   ----- userid
		ISNULL(CAST(FORMAT(Cast(trans.TSF_DATE AS DATETIME), 'yyyyMMddhhmmss', 'ar-SA') AS NVARCHAR),'-1') AS ReceivedOnHijriDate,
		ISNULL(CAST(documents.DOC_SEQUENCE AS NUMERIC),-1) AS SequenceNumber,
		CAST(documents.DOC_IMPORTANCE AS int) UrgencyLevel,
		ISNULL(transferToDepts.DEPARTMENTID,9999) AS departmentID,
		CASE 
			WHEN trans.TSF_TO IS NOT NULL AND trans.TSF_TO_STRUCTURE IS NOT NULL
				THEN 'Inbox'
			WHEN trans.TSF_TO IS NULL AND trans.TSF_TO_STRUCTURE IS NOT NULL
				THEN 'RolesPublicInbox'
			WHEN trans.TSF_TO IS NOT NULL AND trans.TSF_TO_STRUCTURE IS NULL
				THEN 'Inbox'
			--ELSE 
			--	ISNULL(trans.TSF_TO_STRUCTURE,'9999')
		END AS queueName,
		transferToEmps.USERID AS username,
		CASE 
			WHEN trans.TSF_PURPOSE_ID = 29 THEN 0
			ELSE 1
		END AS forwardType,
		'UNDONE' AS status,
		'' AS message,
		CASE 
            WHEN trans.TSF_TO IS NOT NULL AND trans.TSF_TO_STRUCTURE IS NOT NULL
                THEN 1
            WHEN trans.TSF_TO_STRUCTURE IS NOT NULL AND trans.TSF_TO IS NULL
                THEN 2
        END AS participantType,
		transferFromDepts.DEPARTMENTNAME AS ExternalUnit

FROM
CTS_HRDF.dbo.CTS_TRANSFERS trans 
LEFT OUTER JOIN CTS_HRDF.dbo.CTS_DOCUMENTS documents ON trans.DOC_ID = documents.DOC_ID
LEFT OUTER JOIN MOAMALAT.dbo.IO_EMPLOYEES transferToEmps on trans.TSF_TO = transferToEmps.EMPLOYEEID
LEFT OUTER JOIN MOAMALAT.dbo.IO_EMPLOYEES documentEmps on documents.DOC_REGISTEREDBY = documentEmps.EMPLOYEEID
LEFT OUTER JOIN MOAMALAT.dbo.IO_DEPARTMENTS transferToDepts on trans.TSF_TO_STRUCTURE = transferToDepts.DEPARTMENTID
LEFT OUTER JOIN MOAMALAT.dbo.IO_DEPARTMENTS documentDepts on documents.DOC_REGISTERBY_STRUCTURE = documentDepts.DEPARTMENTID
LEFT OUTER JOIN MOAMALAT.dbo.IO_EMPLOYEES transferFromEmps on trans.TSF_FROM = transferFromEmps.EMPLOYEEID
LEFT OUTER JOIN MOAMALAT.dbo.IO_DEPARTMENTS transferFromDepts on trans.TSF_FROM_STRUCTURE = transferFromDepts.DEPARTMENTID

WHERE 
(documents.DOC_STATUS_ID = 1) 
AND 
(ISNULL(trans.IsRecalled, 0) = 0)