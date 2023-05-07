UPDATE stmt1 
SET stmt1.SEARCHALLFILEDS =  CONCAT(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(SUBSTRING(stmt2.CORRESPONDENCESUBJECT, 1, 899), 'أ', 'ا'), 'إ', 'ا'), 'ة', 'ه'), 'ي', 'ى'), 'ئ', 'ا'), 'ت', 'ه'), ' ',  stmt2.CORRESPONDENCENUMBER)
FROM IO_FULL_SEARCH_CORRESPONDENCE stmt1
INNER JOIN  IO_FULL_SEARCH_CORRESPONDENCE stmt2 
ON stmt1.CORRESPONDENCENUMBER = stmt2.CORRESPONDENCENUMBER 
AND stmt1.FULLSEARCH_ID = stmt2.FULLSEARCH_ID
  
--where stmt1.CORRESPONDENCENUMBER = 15920