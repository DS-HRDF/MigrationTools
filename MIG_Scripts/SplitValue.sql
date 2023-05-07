USE [MOAMALAT]
GO
/****** Object:  UserDefinedFunction [dbo].[SplitValue]    Script Date: 3/21/2023 8:56:01 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER FUNCTION [dbo].[SplitValue](@Text varchar(Max), @Delimeter varchar(2) = ' ')
RETURNS @ReturnValue TABLE (id int , value varchar(Max))
AS
BEGIN
IF @Text='' OR @Text IS NULL
RETURN

DECLARE @id int,
    @value varchar(Max),
    @cont bit,
    @strik int,
    @Delimlength int

IF @Delimeter = 'Space'
BEGIN
    SET @Delimeter = ' '
END
--initialize id with 0
SET @id = 0
SET @Text = LTrim(RTrim(@Text))
SET @Delimlength = DATALENGTH(@Delimeter)
SET @cont = 1

IF NOT ((@Delimlength = 0) or (@Delimeter = 'Empty'))
    BEGIN
        WHILE @cont = 1
        BEGIN
        --If you can find the delimiter in the text, retrieve the first element and
        --insert it with its index into the return table.
        IF CHARINDEX(@Delimeter, @Text)>0
            BEGIN
                SET @value = SUBSTRING(@Text,1, CHARINDEX(@Delimeter,@Text)-1)
                BEGIN
                    INSERT @ReturnValue (id, value)
                    VALUES (@id, @value)
                END
                --Increment the index and loop.
                SET @strik = DATALENGTH(@value) + @Delimlength
                SET @id = @id + 1
                SET @Text = LTrim(Right(@Text,DATALENGTH(@Text) - @strik))
            END
        ELSE
        BEGIN
        --If you can’t find the delimiter in the text, @Text is the last value in
        --@ReturnValue.
            SET @value = @Text
            BEGIN
                INSERT @ReturnValue (id, value)
                VALUES (@id, @value)
            END
        --Exit the WHILE loop.
            SET @cont = 0
        END
    END
END
ELSE
BEGIN
    WHILE @cont=1
    BEGIN
    --If the delimiter is an empty string, check for remaining text
    --instead of a delimiter. Insert the first character into the
    --retArray table. Trim the character from the front of the string.
    --Increment the index and loop.
        IF DATALENGTH(@Text)>1
            BEGIN
                SET @value = SUBSTRING(@Text,1,1)
                BEGIN
                INSERT @ReturnValue (id, value)
                VALUES (@id, @value)
                END
                SET @id = @id+1
                SET @Text = SUBSTRING(@Text,2,DATALENGTH(@Text)-1)
            END
        ELSE
            BEGIN
            --One character remains.
            --Insert the character, and exit the WHILE loop.
            INSERT @ReturnValue (id, value)
            VALUES (@id, @Text)
            SET @cont = 0
            END
    END
END
RETURN
END



-- select * from dbo.fun_Split('125/130/148/43/156/160/167/0', '/')
-- select * from dbo.SplitValue('125/130/148/43/156/160/167/0', '/')