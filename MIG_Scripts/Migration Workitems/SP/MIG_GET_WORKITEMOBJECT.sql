

CREATE OR ALTER PROCEDURE [dbo].[MIG_GET_WORKITEMOBJECT] 
AS 
   
   BEGIN

     SELECT  [id]
      ,[F_BoundUser]
      ,[F_Subject]
      ,[CORRESPONDENCEDATE]
      ,[FromUser]
      ,[HijricYear]
      ,[ImportanceLevel]
      ,[isRead]
      ,[ItemType]
      ,[Originator]
      ,[ReceivedOnHijriDate]
      ,[SequenceNumber]
      ,[UrgencyLevel]
      ,[departmentID]
      ,[queueName]
      ,[username]
      ,[forwardType]
      ,[participantType]
	  ,[ExternalUnit]
	  ,[status]
	  ,[message]
	  ,-1 as MONumber
	  ,[SequenceNumber] as CorrespondenceNumber
	  
  FROM [dbo].[MIG_WORKITEMS]
  where status = 'UNDONE'
  AND HijricYear = 1444

  END