package com.dataserve.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.naming.NamingException;

public class HijriCalendar extends AbstractDAO
{
  public static final int MUHARRAM = 0;
  public static final int SAFAR = 1;
  public static final int RABEE1 = 2;
  public static final int RABEE2 = 3;
  public static final int JUMAADAA1 = 4;
  public static final int JUMAADAA2 = 5;
  public static final int RAJAB = 6;
  public static final int SHABAAN = 7;
  public static final int RAMADHAAN = 8;
  public static final int SHAWWAAL = 9;
  public static final int THUL_QIDAH = 10;
  public static final int THUL_HIJJAH = 11;

  public HijriCalendar()
    throws NamingException, SQLException
  {
  }

  public String getHijriDate(String gregorianDate)
  {
    this._sqlStmt = "{ call  dbo.DS_getHijriDate(?,?)}";
    try
    {
      super.initConn();
      this._callStmt = this._conn.prepareCall(this._sqlStmt);
      this._callStmt.registerOutParameter(2, 12);
      this._callStmt.setString(1, gregorianDate);
      this._callStmt.execute();
      return this._callStmt.getString(2);
    }
    catch (Exception e)
    {
      return "";
    }
    finally
    {
      super.safeClose(this._callStmt);
      super.releaseResources();
    }
  }
	public String getHijriDate(Date date, boolean withTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String hDate = getHijriDate(sdf.format(date));
		String time = "";
		if (withTime) {
			sdf = new SimpleDateFormat("HH:mm:ss");
			time = " " + sdf.format(date);
		}
		return hDate + time/* .replace(":", "") */;

	}
	
	
	
	public long getHijriDateLong(Date date, boolean withTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String hDate = getHijriDate(sdf.format(date));
		String time = "";
		if (withTime) {
			sdf = new SimpleDateFormat("HH:mm:ss");
			time = " " + sdf.format(date);
		}
		String stringDate=hDate.replace("/", "").replace("\\","") + time.replace(":", "");
		return new Long(stringDate.replace(" ",""));
	}
	

	public boolean checkDateFormat(String date) {
		if (date == null)
			return false;
		if (date.matches("\\d{4}\\d{2}\\d{2}"))
			return true;

		return false;
	}
	public boolean checkDateFormatWithSeparator(String date) {
		if (date == null)
			return false;
		if (date.matches("\\d{4}/\\d{2}/\\d{2}"))
			return true;
		if (date.matches("\\d{2}/\\d{2}/\\d{4}"))
			return true;
		return false;
	}

  public int getCurrentHijriDateInt(){
     String currentHijriDate = getCurrentHijriDate();
     currentHijriDate = currentHijriDate.replaceAll("/", "");
     return Integer.parseInt(currentHijriDate);
  }

  public String getCurrentHijriDate()
  {
    this._sqlStmt = "{ call  dbo.DS_getCurrHijriDate(?)}";
    try
    {
      super.initConn();
      this._callStmt = this._conn.prepareCall(this._sqlStmt);
      this._callStmt.registerOutParameter(1, 12);
      this._callStmt.execute();
      return this._callStmt.getString(1);
    }
    catch (Exception e)
    {
      return "";
    }
    finally
    {
      super.safeClose(this._callStmt);
      super.releaseResources();
    }
  }

  public String getCurrentHijriDateTime()
  {
    try
    {
      String currentHihriDate = getCurrentHijriDate();
      int loopIndex = 0;
      while ((currentHihriDate.equalsIgnoreCase("")) && (loopIndex < 500))
      {
        loopIndex++;
        currentHihriDate = getCurrentHijriDate();
      }
      String[] hDate = getCurrentHijriDate().split("/");
      StringBuilder hDateTime = new StringBuilder();
      hDateTime.append(hDate[2]);
      hDateTime.append("/");
      hDateTime.append(hDate[1]);
      hDateTime.append("/");
      hDateTime.append(hDate[0]);
      hDateTime.append(" ");

      SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
      hDateTime.append(sdf.format(new Date()));
      return hDateTime.toString();
    }
    catch (Exception e)
    {
    	e.printStackTrace();
    }return "";
  }
  
  public String getCurrentHijriDateTime(String currentHihriDate,int addedSeconds)
  {
    try
    {
      //String currentHihriDate = getCurrentHijriDate();
      int loopIndex = 0;
      while ((currentHihriDate.equalsIgnoreCase("")) && (loopIndex < 500))
      {
        loopIndex++;
        currentHihriDate = getCurrentHijriDate();
      }
      String[] hDate = getCurrentHijriDate().split("/");
      StringBuilder hDateTime = new StringBuilder();
      hDateTime.append(hDate[2]);
      hDateTime.append("/");
      hDateTime.append(hDate[1]);
      hDateTime.append("/");
      hDateTime.append(hDate[0]);
      hDateTime.append(" ");

      Calendar calender=Calendar.getInstance();
      calender.add(Calendar.SECOND,addedSeconds);
      
      SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
      hDateTime.append(sdf.format(calender.getTime()));
      return hDateTime.toString();
    }
    catch (Exception e)
    {
    	e.printStackTrace();
    }return "";
  }
  // msrahin 
  public long getCurrentHijriDateTimeAsLong()
  {
    try
    {
      String currentHihriDate = getCurrentHijriDate();
      int loopIndex = 0;
      while ((currentHihriDate.equalsIgnoreCase("")) && (loopIndex < 500))
      {
        loopIndex++;
        currentHihriDate = getCurrentHijriDate();
      }
      String[] hDate = getCurrentHijriDate().split("/");
      StringBuilder hDateTime = new StringBuilder();
      hDateTime.append(hDate[0]);
     // hDateTime.append("/");
      hDateTime.append(hDate[1]);
    //  hDateTime.append("/");
      hDateTime.append(hDate[2]);
    //  hDateTime.append(" ");

      SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
      String time = sdf.format(new Date()).replace(":", "").trim();
      hDateTime.append(time);
      return Long.parseLong(hDateTime.toString());
    }
    catch (Exception e)
    {
    	e.printStackTrace();
      //AppLogger1.Log("xxx : System Error getCurrentHijriDateTime()   ");
      //AppLogger1.Log(e);
    }return 0l;
  }
  
  
  public long getCurrentHijriDateTimeAsLong(String currentHihriDate,int addedSeconds)
  {
    try
    {
      //String currentHihriDate = getCurrentHijriDate();
      int loopIndex = 0;
      while ((currentHihriDate.equalsIgnoreCase("")) && (loopIndex < 500))
      {
        loopIndex++;
        currentHihriDate = getCurrentHijriDate();
        //AppLogger1.Log("failed to get current Hijri date #" + loopIndex);
      }
      String[] hDate = getCurrentHijriDate().split("/");
      StringBuilder hDateTime = new StringBuilder();
      hDateTime.append(hDate[0]);
     // hDateTime.append("/");
      hDateTime.append(hDate[1]);
    //  hDateTime.append("/");
      hDateTime.append(hDate[2]);
    //  hDateTime.append(" ");
      Calendar calender=Calendar.getInstance();
      calender.add(Calendar.SECOND,addedSeconds);
      
      SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
      String time = sdf.format(calender.getTime()).replace(":", "").trim();
      hDateTime.append(time);
      return Long.parseLong(hDateTime.toString());
    }
    catch (Exception e)
    {
    	e.printStackTrace();
    }return 0l;
  }
  

  public String getGregorianDate(String hijriDate)
  { 
    this._sqlStmt = "{call dbo.DS_getGregDate(?, ?)}";
    try
    {
      super.initConn();
      this._callStmt = this._conn.prepareCall(this._sqlStmt);
      this._callStmt.registerOutParameter(2, 12);
      this._callStmt.setString(1, hijriDate.trim());
      this._callStmt.execute();
      return this._callStmt.getString(2);
    }
    catch (Exception e)
    {
      return "";
    }
    finally
    {
      super.safeClose(this._callStmt);
      super.releaseResources();
    }
  }

  public String getCurrentGregorianDate()
  {
    this._sqlStmt = "{ call  dbo.DS_getCurrGregDate(?)}";
    try
    {
      super.initConn();
      this._callStmt = this._conn.prepareCall(this._sqlStmt);
      this._callStmt.registerOutParameter(1, 12);
      this._callStmt.execute();
      return this._callStmt.getString(1);
    }
    catch (Exception e)
    {
      return "";
    }
    finally
    {
      super.safeClose(this._callStmt);
      super.releaseResources();
    }
  }

  public boolean validHijriDate(String hijriDate)
  {
    String gregDate = getGregorianDate(hijriDate).trim();
    return (!gregDate.equals("")) && (!gregDate.equals("//"));
  }
}