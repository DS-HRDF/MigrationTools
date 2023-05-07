
-- DROP VIEW IF EXISTS MIG_IO_OUTGOING_DESTINATIONS_VIEW;

CREATE VIEW MIG_IO_OUTGOING_DESTINATIONS_VIEW
AS

SELECT --a.Items,
a.CORRESPONDENCENUMBER,
a.HIJRICYEAR,
--a.CorrespondenceType,
a.CORRESPONDENCEDESTINATIONID,
1 AS RECEIVEMODEID,
1 AS LETTERTYPEID,
'' AS [COMMENTS],
1 AS [UNITTYPEID],
'' AS [ATTACHMENTS],
0 AS [ISMINISTER],
NULL AS [RECORDNUMBER],
0 AS [DELIVERED],
'' AS [SearchableComments],
1 AS [IS_MIGRATED],
a.doc_id AS OLD_DOC_ID
,a._c AS IS_NUMERIC  --- 0:true  1:false
FROM(
SELECT 
--doc_recipients.Items,
ISNULL(CAST(documents.DOC_SEQUENCE AS NUMERIC),-1) AS CORRESPONDENCENUMBER,
ISNULL(CAST(doc_recipients.Items AS NUMERIC),-1) AS CORRESPONDENCEDESTINATIONID,
ISNUMERIC(DOC_RECIPIENT) as _c,
documents.DOC_ID AS doc_id,
ISNULL(CAST(SUBSTRING(ISNULL(CAST(FORMAT(CAST(documents.DOC_REGISTERDATE AS DATE), 'yyyyMMdd', 'ar-SA') AS NVARCHAR),'-1'),0,5) AS NUMERIC),-1) AS HIJRICYEAR,
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
END AS CorrespondenceType

FROM CTS_HRDF.dbo.CTS_DOCUMENTS documents
CROSS APPLY dbo.fun_Split(DOC_RECIPIENT, '/') AS doc_recipients
WHERE 
DOC_RECIPIENT IS NOT NULL
AND documents.DOC_CATEGORY = 5
--DOC_STATUS_ID <> 46
--AND (ISNUMERIC(DOC_RECIPIENT) <> 1)    ----   <> 1 means multi destinations
--group by documents.DOC_SEQUENCE 
--and DOC_ID = 142662
  --order by documents.DOC_ID desc
) AS a
--ORDER BY a.DOC_ID DESC
  --where a.CorrespondenceType not in (1,2,3,44,55)

  -- 119,233
  -- 3,907  <> 1
  -- 115,326 = 1