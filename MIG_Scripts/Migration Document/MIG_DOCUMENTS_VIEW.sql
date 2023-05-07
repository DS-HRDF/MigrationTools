
-- DROP VIEW IF EXISTS MIG_DOCUMENTS_VIEW;

CREATE OR ALTER VIEW MIG_DOCUMENTS_VIEW
AS

SELECT 
ISNULL(CAST(documents.DOC_SEQUENCE AS NUMERIC),-1) AS cn,
ISNULL(CAST(SUBSTRING(ISNULL(CAST(FORMAT(CAST(documents.DOC_REGISTERDATE AS DATE), 'yyyyMMdd', 'ar-SA') AS NVARCHAR),'-1'),0,5) AS NUMERIC),-1) AS hy,
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
END AS typeid,
documents.DOC_ID  docid,
phDocs.DOCNAME AS fileName,
--documents.DOC_SUBJECT AS docTitle,
--CHARINDEX('.', phDocs.DOCNAME),
CASE 
	WHEN CHARINDEX('.', phDocs.DOCNAME) <> 0
		THEN SUBSTRING(phDocs.DOCNAME, 1,CHARINDEX('.', phDocs.DOCNAME)-1)
	WHEN CHARINDEX('.', phDocs.DOCNAME) = 0
		THEN phDocs.DOCNAME
END AS docTitle,
'RegulatoryDocuments' AS classId,
phDocs.DOCEXT mimeType,
phDocs.CREATE_DATE AS createDate,
CASE 
	WHEN docs.CREATEUSER = 585
		THEN 'HDF.SRV.SD.FNT.X1'
	ELSE emps.USERID
END AS username,
'UNDONE' AS status,
'' AS message,
phDocs.DOCPATH AS documentPath,
phDocs.FILESIZE AS documentSize,
documents.DOC_ID AS documentId

FROM     
CTS_HRDF.dbo.SYN_ESDOCS docs 
INNER JOIN CTS_HRDF.dbo.CTS_DOCUMENTS documents ON docs.RECORDKEY = 'CTS_DOCUMENTS.DOC_ID=' + CAST(documents.DOC_ID AS nvarchar(30))
INNER JOIN CTS_HRDF.dbo.SYN_ESDOCSLINKS dl ON dl.DOCKEY = docs.ESCOUNT
INNER JOIN CTS_HRDF.dbo.SYN_ESPHYSICALDOCS phDocs ON dl.DOCLINKKEY = phDocs.ESCOUNT
LEFT OUTER JOIN IO_EMPLOYEES emps on docs.CREATEUSER = emps.MigratedUserId
