USE [MOAMALAT]
GO
/****** Object:  UserDefinedFunction [dbo].[fun_Split]    Script Date: 3/21/2023 8:52:27 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER function [dbo].[fun_Split]
(
@String nvarchar(4000),
@Delimiter char(1)
) Returns @Results Table (Items int)
As
Begin
Declare @Index int
Declare @Slice nvarchar(4000)
Select @Index = 1
If @String Is NULL Return
While @Index != 0
Begin
Select @Index = CharIndex(@Delimiter, @String)
If (@Index != 0)
Select @Slice = left(@String, @Index - 1)
else
Select @Slice = @String
Insert into @Results(Items) Values (@Slice)
Select @String = right(@String, Len(@String) - @Index)
If Len(@String) = 0 break
End
Return
End




-- select * from dbo.fun_Split('125/130/148/43/156/160/167/0', '/')
-- select * from dbo.SplitValue('125/130/148/43/156/160/167/0', '/')