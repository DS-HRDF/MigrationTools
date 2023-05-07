

CREATE OR ALTER PROCEDURE [dbo].[MIG_GET_INBASKETDATA]
AS
BEGIN

SELECT [DEPARTMENTID]
      ,[DEPARTMENTNAMEAR]
      ,[DEPARTMENTNAMEEN]
      ,[QUEUENAME]
      ,[QUEUESHORTNAME]
      ,[QUEUEHIJRICREATEDATE]
      ,[DEPARTMENTQueueNAMEAR]
      ,[DEPARTMENTQueueNAMEEN]
      ,[WORKQUEUES]
      ,[applicationSpaceName]
      ,[roleName_English]
      ,[roleName_Arabic]
      ,[STATUS]
      ,[COMMENTS]
  FROM [MOAMALAT].[dbo].[MIG_INBASKETDATA]
  WHERE  DEPARTMENTID <> 9999 and DEPARTMENTID = 42
  AND STATUS = 'UNDONE'

END