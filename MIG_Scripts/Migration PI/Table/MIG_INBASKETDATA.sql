
CREATE TABLE 
[dbo].[MIG_INBASKETDATA](
	[DEPARTMENTID] [numeric](18, 0) NOT NULL,
	[DEPARTMENTNAMEAR] [varchar](500) NOT NULL,
	[DEPARTMENTNAMEEN] [varchar](500) NOT NULL,
	[QUEUENAME] [varchar](250) NOT NULL,
	[QUEUESHORTNAME] [varchar](250) NOT NULL,
	[QUEUEHIJRICREATEDATE] [numeric](18, 0) NULL,
	[DEPARTMENTQueueNAMEAR] [varchar](500) NULL,
	[DEPARTMENTQueueNAMEEN] [varchar](500) NULL,
	[WORKQUEUES] [varchar](250) NULL,
	[applicationSpaceName] [varchar](250) NULL,
	[roleName_English] [varchar](250) NULL,
	[roleName_Arabic] [varchar](250) NULL,
	[STATUS] [varchar](50) NULL,
	[COMMENTS] [varchar](max) NULL
)