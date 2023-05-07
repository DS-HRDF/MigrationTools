
-- DROP VIEW IF EXISTS MIG_IO_FORWARDINGDETAILS_VIEW;

CREATE OR ALTER VIEW MIG_IO_FORWARDINGDETAILS_VIEW
AS

SELECT DISTINCT
		trans.TSF_ID AS FORWARDINGID,
		1 AS FORWARDINGTYPE,
		CASE 
			WHEN (trans.TSF_FROM IS NOT NULL AND trans.TSF_FROM_STRUCTURE IS NOT NULL)
				THEN transferFromEmps.USERID
			WHEN (trans.TSF_FROM IS NOT NULL AND trans.TSF_FROM_STRUCTURE IS NULL)
				THEN transferFromEmps.USERID
			WHEN (trans.TSF_FROM IS NULL AND trans.TSF_FROM_STRUCTURE IS NULL)
				THEN documentEmps.USERID
			ELSE 
				ISNULL(transferFromEmps.USERID,'not_found_user')
		END AS PARTICIPANTUSERID,

		CASE 
			WHEN (trans.TSF_FROM_STRUCTURE IS NOT NULL AND trans.TSF_FROM IS NOT NULL)
				THEN transferFromDepts.DEPARTMENTID
			WHEN (trans.TSF_FROM_STRUCTURE IS NOT NULL AND trans.TSF_FROM IS NULL)
				THEN transferFromDepts.DEPARTMENTID
			WHEN (trans.TSF_FROM_STRUCTURE IS NULL AND trans.TSF_FROM IS NULL)
				THEN documentDepts.DEPARTMENTID
			ELSE 
				ISNULL(transferFromDepts.DEPARTMENTID,'9999')
		END AS PARTICIPANTDEPTID,
		1 AS PARTICIPANTTYPE,
		trans.TSF_PURPOSE_AR AS INSTRUCTION,
		0 AS ISIGNORED,
		0 AS IGNOREDBY,
		ISNULL(CAST(SUBSTRING(ISNULL(CAST(FORMAT(CAST(documents.DOC_REGISTERDATE AS DATE), 'yyyyMMdd', 'ar-SA') AS NVARCHAR),'-1'),0,5) AS NUMERIC),-1) AS HY,
		NULL AS sentFROMINboxName,
		1 AS IS_MIGRATED

FROM
CTS_HRDF.dbo.CTS_TRANSFERS trans 
LEFT OUTER JOIN CTS_HRDF.dbo.CTS_DOCUMENTS documents ON trans.DOC_ID = documents.DOC_ID
LEFT OUTER JOIN MOAMALAT.dbo.IO_EMPLOYEES transferFromEmps on trans.TSF_FROM = transferFromEmps.EMPLOYEEID
LEFT OUTER JOIN MOAMALAT.dbo.IO_EMPLOYEES documentEmps on documents.DOC_REGISTEREDBY = documentEmps.EMPLOYEEID
LEFT OUTER JOIN MOAMALAT.dbo.IO_DEPARTMENTS transferFromDepts on trans.TSF_FROM_STRUCTURE = transferFromDepts.DEPARTMENTID
LEFT OUTER JOIN MOAMALAT.dbo.IO_DEPARTMENTS documentDepts on documents.DOC_REGISTERBY_STRUCTURE = documentDepts.DEPARTMENTID

UNION ALL

SELECT
		trans.TSF_ID AS FORWARDINGID,
		CASE 
			WHEN trans.TSF_PURPOSE_ID = 29 THEN 3
			ELSE 2 
		END AS FORWARDINGTYPE,
		CASE 
			WHEN (trans.TSF_TO IS NOT NULL AND trans.TSF_TO_STRUCTURE IS NOT NULL)
				THEN transferToEmps.USERID
			WHEN (trans.TSF_TO IS NOT NULL AND trans.TSF_TO_STRUCTURE IS NULL)
				THEN transferToEmps.USERID
			WHEN (trans.TSF_TO IS NULL AND trans.TSF_TO_STRUCTURE IS NOT NULL)
				THEN ISNULL('#'+CAST(transferToDepts.DEPARTMENTID AS VARCHAR),'#'+CAST(documentDepts.DEPARTMENTID AS VARCHAR))
			ELSE 
				ISNULL(transferToEmps.USERID,'not_found_user')
		END AS PARTICIPANTUSERID,
		CASE 
			WHEN (trans.TSF_TO IS NOT NULL AND trans.TSF_TO_STRUCTURE IS NOT NULL)
				THEN transferToDepts.DEPARTMENTID
			WHEN (trans.TSF_TO IS NOT NULL AND trans.TSF_TO_STRUCTURE IS NULL)
				THEN transferToDepts.DEPARTMENTID
			WHEN (trans.TSF_TO IS NULL AND trans.TSF_TO_STRUCTURE IS NOT NULL)
				THEN ISNULL(transferToDepts.DEPARTMENTID,documentDepts.DEPARTMENTID)
			ELSE 
				ISNULL(transferToDepts.DEPARTMENTID,'9999')
		END AS PARTICIPANTDEPTID,
		CASE 
		WHEN (trans.TSF_TO IS NULL AND trans.TSF_TO_STRUCTURE IS NOT NULL)
			THEN 2
		WHEN  (trans.TSF_TO IS NOT NULL AND trans.TSF_TO_STRUCTURE IS NOT NULL)
			THEN 1
		ELSE 3
	END AS PARTICIPANTTYPE,
		trans.TSF_PURPOSE_AR AS INSTRUCTION,
		0 AS ISIGNORED,
		0 AS IGNOREDBY,
		ISNULL(CAST(SUBSTRING(ISNULL(CAST(FORMAT(CAST(documents.DOC_REGISTERDATE AS DATE), 'yyyyMMdd', 'ar-SA') AS NVARCHAR),'-1'),0,5) AS NUMERIC),-1) AS HY,
		NULL AS sentFROMINboxName,
		1 AS IS_MIGRATED

FROM
CTS_HRDF.dbo.CTS_TRANSFERS trans 
LEFT OUTER JOIN CTS_HRDF.dbo.CTS_DOCUMENTS documents ON trans.DOC_ID = documents.DOC_ID
LEFT OUTER JOIN MOAMALAT.dbo.IO_EMPLOYEES transferToEmps on trans.TSF_TO = transferToEmps.EMPLOYEEID
LEFT OUTER JOIN MOAMALAT.dbo.IO_EMPLOYEES documentEmps on documents.DOC_REGISTEREDBY = documentEmps.EMPLOYEEID
LEFT OUTER JOIN MOAMALAT.dbo.IO_DEPARTMENTS transferToDepts on trans.TSF_TO_STRUCTURE = transferToDepts.DEPARTMENTID
LEFT OUTER JOIN MOAMALAT.dbo.IO_DEPARTMENTS documentDepts on documents.DOC_REGISTERBY_STRUCTURE = documentDepts.DEPARTMENTID