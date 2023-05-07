package com.dataserve.migration.spga.dao;

import com.dataserve.migration.spga.objects.Attachment;
import com.filenet.api.core.*;
//import jcifs.smb.NtlmPasswordAuthentication;
//import jcifs.smb.SmbFile;
//import jcifs.smb.SmbFileInputStream;
//import jcifs.smb.SmbFileOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.*;

/************************
 *
 * Created By Mohammad Awwad 02-June-2020
 *
 ************************/

public class AttachmentsDAO {

//	static {
//		System.setProperty("Djava.security.auth.login.config", "jaas.conf.WSI");
//		System.setProperty("jcifs.netbios.wins", "192.168.0.26");
//	}

	final static Logger logger = Logger.getLogger(AttachmentsDAO.class);

	private ObjectStore objectStore = null;
	private boolean connected = false;

	private int ceConnection;

	public static String getExtensionByApacheCommonLib(String filename) {
		return FilenameUtils.getExtension(filename);
	}

	public static String getMimeType(File file) throws Exception {
		if (getExtensionByApacheCommonLib(file.getName()).equalsIgnoreCase("pdf")) {
			return "application/pdf";
		} else if (getExtensionByApacheCommonLib(file.getName()).equalsIgnoreCase("docx"))
			return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		else if (getExtensionByApacheCommonLib(file.getName()).equalsIgnoreCase("tif")) {
			return "application/x-font-truetype";
		} else if (getExtensionByApacheCommonLib(file.getName()).equalsIgnoreCase("jpg")) {
			return "image/jpeg";
		} else if (getExtensionByApacheCommonLib(file.getName()).equalsIgnoreCase("png")) {
			return "image/png";
		} else if (getExtensionByApacheCommonLib(file.getName()).equalsIgnoreCase("html")) {
			return "text/webeditor";
		} else if (getExtensionByApacheCommonLib(file.getName()).equalsIgnoreCase("tiff")) {
			return "image/tiff";
		} else if (getExtensionByApacheCommonLib(file.getName()).equalsIgnoreCase("txt")) {
			return "text/plain";
		} else if (getExtensionByApacheCommonLib(file.getName()).equalsIgnoreCase("doc")) {
			return "application/msword";
		} else if (getExtensionByApacheCommonLib(file.getName()).equalsIgnoreCase("xls")) {
			return "application/vnd.ms-excel";
		} else if (getExtensionByApacheCommonLib(file.getName()).equalsIgnoreCase("xlsx")) {
			return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		} else {
			return "Not Found";
		}
	}

	public Attachment getAttachment(String attachmentPath, String fileName, String attachmentSize) {
		byte[] bytes = null;
		String mimeType = "";
		InputStream input = null;
		String[] extensions = { "png", "jpg", "pdf" };
		try {
			attachmentPath = attachmentPath.replace("/", "\\");

			//String url1 = "\\\\192.168.0.26\\e$\\CDSI\\" + attachmentPath;// + "\\" + fileName;
			String url = "\\\\172.24.100.61\\e$\\Migration\\" + attachmentPath + "\\" + fileName;;
			File file = new File(url);

//			File[] listOfFiles = fileToGet.listFiles();

//			for (File file : listOfFiles) {
//				if (file.isFile()) {
					long attSiza = Long.parseLong(attachmentSize);
//					if (file.length() == attSiza) {
						if (!getMimeType(file).equalsIgnoreCase("old")
								|| getMimeType(file).equalsIgnoreCase("Not Found")) {
							try {
								input = new FileInputStream(file);
								mimeType = getMimeType(file);
								bytes = IOUtils.toByteArray(input);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
//					} else {
//						continue;
//					}
//				}
//			}
			return new Attachment(bytes, mimeType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public void setObjectStore(ObjectStore objectStore) {
		this.objectStore = objectStore;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public void setCeConnection(int ceConnection) {
		this.ceConnection = ceConnection;
	}

	public int getCeConnection() {
		return ceConnection;
	}
}
