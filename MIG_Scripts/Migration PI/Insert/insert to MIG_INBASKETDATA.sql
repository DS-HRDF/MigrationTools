USE [MOAMALAT]
GO

INSERT INTO [dbo].[MIG_INBASKETDATA]
           ([DEPARTMENTID]
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
           ,[COMMENTS])
     
	 SELECT 
	   [DEPARTMENTID]
      ,[DEPARTMENTNAME]
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
      ,'UNDONE'
	  ,''
  FROM [dbo].[MIG_IO_DEPARTMENTQUEUES_VIEW]

GO


INSERT INTO IO_DEPARTMENTQUEUES
  SELECT [DEPARTMENTID]
           ,[DEPARTMENTNAMEAR]
           ,[DEPARTMENTNAMEEN]
           ,[QUEUENAME]
           ,[QUEUESHORTNAME]
           ,[QUEUEHIJRICREATEDATE]
           ,[DEPARTMENTQUEUENAMEAR]
           ,[DEPARTMENTQUEUENAMEEN]
           ,[WORKQUEUES]
           ,[APPLICATIONSPACENAME]
           ,[ROLENAME_ENGLISH]
           ,[ROLENAME_ARABIC]
		   ,1
		   FROM [MIG_INBASKETDATA]
  WHERE DEPARTMENTID NOT IN (SELECT DEPARTMENTID FROM IO_DEPARTMENTQUEUES)

   


