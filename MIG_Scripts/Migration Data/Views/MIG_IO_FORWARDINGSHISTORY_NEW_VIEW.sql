
CREATE OR ALTER VIEW MIG_IO_FORWARDINGSHISTORY_NEW_VIEW
AS

SELECT DISTINCT
		trans.DOC_ID AS FORWARDINGID
		,
		--documents.DOC_ID,
		ISNULL(CAST(documents.DOC_SEQUENCE AS NUMERIC),-1) AS CORRESPONDENCENUMBER,
		ISNULL(CAST(SUBSTRING(ISNULL(CAST(FORMAT(CAST(documents.DOC_REGISTERDATE AS DATE), 'yyyyMMdd', 'ar-SA') AS NVARCHAR),'-1'),0,5) AS NUMERIC),-1) AS HIJRICYEAR,
		CASE 
			WHEN documents.DOC_CATEGORY = 1
				THEN 1
			WHEN documents.DOC_CATEGORY = 5
				THEN 2
			WHEN documents.DOC_CATEGORY = 7
				THEN 3
			WHEN documents.DOC_CATEGORY = 13
				THEN 44
			WHEN documents.DOC_CATEGORY = 11
				THEN 55
		ELSE   -1
		END AS TYPEID,
		CASE 
			WHEN ISNULL(trans.PriorityId, 0) = 0
				THEN 0
			WHEN trans.PriorityId = 1
				THEN 0
			WHEN trans.PriorityId = 2
				THEN 0
			WHEN trans.PriorityId = 3
				THEN 1
			WHEN trans.PriorityId = 4
				THEN 2
			WHEN trans.PriorityId = 138
				THEN 3
		ELSE   0
		END AS URGENCYLEVEL,
		CASE 
			WHEN ISNULL(documents.DOC_IMPORTANCE, 0) = 0
				THEN 0
			WHEN documents.DOC_IMPORTANCE = 1
				THEN 0
			ELSE 1
		END AS IMPORTANCELEVEL,
		NULL AS REMINDER, 
		NULL AS DEADLINE, 
		--(select top(1) ISNULL(CAST(FORMAT(CAST(trans.TSF_DATE AS DATETIME), 'dd/MM/yyyy hh:mm:ss', 'ar-SA') AS VARCHAR(20)), '01/01/1400 12:00:00')) AS FORWARDINGTIME,
		--trans.TSF_PURPOSE_AR AS INSTRUCTIONS, 
		NULL AS MINISTER_FORWARDINGID, 
		0 AS ISIGNORED, 
		NULL AS IGNOREDBY, 
		--ISNULL(CAST(FORMAT(CAST(trans.TSF_DATE AS Date), 'yyyyMMdd', 'ar-SA') AS NUMERIC), '14000101') AS FORWARDINGDATE, 
		'' AS ATTACHMENTS, 
		NULL AS DEADLINE_backup, 
		documents.DOC_SUBJECT AS H_CORRESPONDENCESUBJECT, 
		ISNULL(emps.USERID,'not_found_user') AS H_SENTBY,
		NULL AS H_MINISTEROFFICENUMBER, 
		NULL AS IS_SIGN_REQUEST, 
		NULL AS VIP_ATTACHMENT, 
		NULL AS VIP_PRESENTED_TYPE, 
		NULL AS LETTER_ATTACHMENT, 
		0 AS IS_CREATED, 
		1 AS IS_MIGRATED--,
		--trans.TSF_ID AS OLD_DOC_ID
		--,*
		
		FROM
		CTS_HRDF.dbo.CTS_TRANSFERS trans 
		 left JOIN CTS_HRDF.dbo.CTS_DOCUMENTS documents ON trans.DOC_ID = documents.DOC_ID
		 left JOIN MOAMALAT.dbo.MIG_IO_EMPLOYEES_VIEW emps on trans.TSF_FROM = emps.EMPLOYEEID
		where 
		--documents.DOC_SEQUENCE = 621
		--and 
		CAST(SUBSTRING(ISNULL(CAST(FORMAT(CAST(documents.DOC_REGISTERDATE AS DATE), 'yyyyMMdd', 'ar-SA') AS NVARCHAR),'-1'),0,5) AS NUMERIC) = 1444
		--and documents.DOC_CATEGORY = 7
		and 
		trans.TSF_PARENT_TRANSFER = 0

		UNION ALL

		SELECT DISTINCT
		trans.TSF_ID AS FORWARDINGID,
		--documents.DOC_ID,
		ISNULL(CAST(documents.DOC_SEQUENCE AS NUMERIC),-1) AS CORRESPONDENCENUMBER,
		ISNULL(CAST(SUBSTRING(ISNULL(CAST(FORMAT(CAST(documents.DOC_REGISTERDATE AS DATE), 'yyyyMMdd', 'ar-SA') AS NVARCHAR),'-1'),0,5) AS NUMERIC),-1) AS HIJRICYEAR,
		CASE 
			WHEN documents.DOC_CATEGORY = 1
				THEN 1
			WHEN documents.DOC_CATEGORY = 5
				THEN 2
			WHEN documents.DOC_CATEGORY = 7
				THEN 3
			WHEN documents.DOC_CATEGORY = 13
				THEN 44
			WHEN documents.DOC_CATEGORY = 11
				THEN 55
		ELSE   -1
		END AS TYPEID,
		CASE 
			WHEN ISNULL(trans.PriorityId, 0) = 0
				THEN 0
			WHEN trans.PriorityId = 1
				THEN 0
			WHEN trans.PriorityId = 2
				THEN 0
			WHEN trans.PriorityId = 3
				THEN 1
			WHEN trans.PriorityId = 4
				THEN 2
			WHEN trans.PriorityId = 138
				THEN 3
		ELSE   0
		END AS URGENCYLEVEL,
		CASE 
			WHEN ISNULL(documents.DOC_IMPORTANCE, 0) = 0
				THEN 0
			WHEN documents.DOC_IMPORTANCE = 1
				THEN 0
			ELSE 1
		END AS IMPORTANCELEVEL,
		NULL AS REMINDER, 
		NULL AS DEADLINE, 
		--(select top(1) ISNULL(CAST(FORMAT(CAST(trans.TSF_DATE AS DATETIME), 'dd/MM/yyyy hh:mm:ss', 'ar-SA') AS VARCHAR(20)), '01/01/1400 12:00:00')) AS FORWARDINGTIME,
		--trans.TSF_PURPOSE_AR AS INSTRUCTIONS, 
		NULL AS MINISTER_FORWARDINGID, 
		0 AS ISIGNORED, 
		NULL AS IGNOREDBY, 
		--ISNULL(CAST(FORMAT(CAST(trans.TSF_DATE AS Date), 'yyyyMMdd', 'ar-SA') AS NUMERIC), '14000101') AS FORWARDINGDATE, 
		'' AS ATTACHMENTS, 
		NULL AS DEADLINE_backup, 
		documents.DOC_SUBJECT AS H_CORRESPONDENCESUBJECT, 
		ISNULL(emps.USERID,'not_found_user') AS H_SENTBY,
		NULL AS H_MINISTEROFFICENUMBER, 
		NULL AS IS_SIGN_REQUEST, 
		NULL AS VIP_ATTACHMENT, 
		NULL AS VIP_PRESENTED_TYPE, 
		NULL AS LETTER_ATTACHMENT, 
		0 AS IS_CREATED, 
		1 AS IS_MIGRATED--,
		--trans.TSF_ID AS OLD_DOC_ID
		--,*

		FROM
		CTS_HRDF.dbo.CTS_TRANSFERS trans 
		 left JOIN CTS_HRDF.dbo.CTS_DOCUMENTS documents ON trans.DOC_ID = documents.DOC_ID
		 left JOIN MOAMALAT.dbo.MIG_IO_EMPLOYEES_VIEW emps on trans.TSF_FROM = emps.MigratedUserId
		 join CTS_HRDF.dbo.CTS_TRANSFERS child on child.TSF_PARENT_TRANSFER = trans.TSF_ID
		where 
		----documents.DOC_SEQUENCE = 621
		----and
		CAST(SUBSTRING(ISNULL(CAST(FORMAT(CAST(documents.DOC_REGISTERDATE AS DATE), 'yyyyMMdd', 'ar-SA') AS NVARCHAR),'-1'),0,5) AS NUMERIC) = 1444
		--and documents.DOC_CATEGORY = 7