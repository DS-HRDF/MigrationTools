
CREATE TABLE [dbo].[MIG_WORKITEMS](
	[id] [numeric](18, 0) NOT NULL,
	[F_BoundUser] [varchar](100) NULL,
	[F_Subject] [varchar](max) NULL,
	[CORRESPONDENCEDATE] [numeric](18, 0) NULL,
	[FromUser] [varchar](500) NULL,
	[HijricYear] [numeric](18, 0) NULL,
	[ImportanceLevel] [numeric](18, 0) NULL,
	[isRead] [int] NULL,
	[ItemType] [int] NULL,
	[Originator] [varchar](100) NULL,
	[ReceivedOnHijriDate] [varchar](50) NULL,
	[SequenceNumber] [numeric](18, 0) NULL,
	[UrgencyLevel] [int] NULL,
	[departmentID] [int] NULL,
	[queueName] [varchar](50) NULL,
	[username] [varchar](50) NULL,
	[forwardType] [int] NULL,
	[participantType] [int] NULL,
	[ExternalUnit] [varchar](max) NULL,
	[status] [varchar](50) NULL,
	[message] [varchar](max) NULL
)