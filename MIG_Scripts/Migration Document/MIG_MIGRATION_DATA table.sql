
CREATE TABLE [dbo].[MIG_MIGRATION_DATA](
	[cn] [numeric](18, 0) NOT NULL,
	[hy] [numeric](18, 0) NOT NULL,
	[typeid] [numeric](18, 0) NOT NULL,
	[docid] [varchar](max) NOT NULL,
	[fileName] [varchar](max) NOT NULL,
	[docTitle] [varchar](max) NULL,
	[classId] [varchar](max) NOT NULL,
	[mimeType] [varchar](max) NOT NULL,
	[createDate] [date] NULL,
	[username] [varchar](max) NULL,
	[status] [varchar](max) NULL,
	[message] [varchar](max) NULL,
	[documentPath] [varchar](max) NULL,
	[documentSize] [varchar](max) NULL,
	[migrated_docid] [varchar](max) NULL
)