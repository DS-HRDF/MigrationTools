USE [MOAMALAT]
GO

INSERT INTO [dbo].[MIG_WORKITEMS]
           ([id]
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
           ,[message])
     
	 SELECT [FORWARDINGID]
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
  FROM [dbo].[MIG_WORKITEMS_VIEW]

GO

