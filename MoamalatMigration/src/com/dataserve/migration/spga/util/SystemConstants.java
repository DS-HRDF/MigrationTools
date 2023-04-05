package com.dataserve.migration.spga.util;

public interface SystemConstants {

	String CONFIG_NAME_SITE_NAME = "siteName";
	String CONFIG_NAME_ADMIN_USER = "adminUserName";
	String CONFIG_NAME_ADMIN_PASSWORD = "adminPassword";
	String CONFIG_NAME_DBTYPE_ORACLE = "dbTypeOracle";
	String CONFIG_NAME_DBTYPE_SQL_SRV = "dbTypeSQL";
	String CONFIG_NAME_DATASOURCE = "dataSource";
	String CONFIG_NAME_DATABASE_PREFIX = "dbPrefix";
	String CONFIG_NAME_LDAP_URL = "ldapurl";
	String CONFIG_NAME_LDAP_DOMAIN = "ldapDomain";
	String CONFIG_NAME_LOG_PATH = "logPath";
	String CONFIG_NAME_IS_MONUMBER_EXIST_YES = "isMONumberExistYes";
	String CONFIG_NAME_IS_MONUMBER_EXIST_NO = "isMONumberExistNo";
	String CONFIG_NAME_SITE_AR_NAME = "SiteArabicName";
	String CONFIG_NAME_REPORT_LOGO_PATH = "ReportLogoPath";
	/*********** Email Configs */
	String CONFIG_EMAILSERVERURL = "emailserverurl";
	String CONFIG_EMAILDOMAIN = "emailDomain";
	String CONFIG_EMAILAPPLICATIONURL = "emailApplicationUrl";
	String CONFIG_EMAILPROTOCOL = "emailProtocol";
	String CONFIG_EMAILHOSTSMTP = "emailHostSMTP";
	String CONFIG_EMAILSENDEREMAIL = "emailSenderEmail";
	String CONFIG_EMAILSENDERUSER = "emailSenderUser";
	String CONFIG_EMAILSENDERPASSWORD = "emailSenderPassword";
	String CONFIG_EMAILSENDERDISPLAYNAME = "emailSenderDisplayName";

	String CONFIG_EMAIL_HTML_TEMPLATE = "emailHTMLTemplate";
	String CONFIG_EMAIL_HTML_TEMPLATE_QUICKTASK = "emailHTMLTemplateQuickTask";
	String CONFIG_EMAIL_HTML_TEMPLATE_PUBLICQUEUE = "emailHTMLTemplatePublicQueue";
	String CONFIG_EMAIL_HTML_TEMPLATE_REMINDER = "emailHTMLTemplateReminder";

	String DB_TYPE_ORACLE = "oracle";
	String DB_TYPE_SQL_SERVER = "sqlSrv";
	String DB_DEFAULT_DATASOURCE = "FNMOAMALATDS";
	String LOG_DEFAULT_PATH = "/opt/IBM/moamalatLogs/";
	String SITE_NAME_SHURA = "Shura";

	String CE_DOCUEMNT_CLASS_DELIVERY_REPORT = "DeliveryReport";
	String CE_DOCUMENT_CLASS_CORRESPONDENCE = "CorrespondenceInfo";
	String CE_PROPERTY_CORRESPONDENCE_TYPE = "CorrespondenceType";
	String CE_PROPERTY_HIJRIC_YEAR = "HijricYear";
	String CE_PROPERTY_CORRESPONDENCE_NUMBER = "CorrespondenceNumber";

	// int PERMISSION_CATEGORY_USER = 1;
	// int PERMISSION_CATEGORY_DEPARTMENT = 2;

	// 1 Departments Permissions
	// 2 Send Permissions
	// 3 Create Permissions
	// 4 Search Permissions
	// 5 Edit Permissions
	// 6 General Permissions

	// @mhmdawwad
	int PERMISSION_CATEGORY_DEPARTMENT = 1;
	int PERMISSION_CATEGORY_SEND = 2;
	int PERMISSION_CATEGORY_CREATE = 3;
	int PERMISSION_CATEGORY_SEARCH = 4;
	int PERMISSION_CATEGORY_EDIT = 5;
	int PERMISSION_CATEGORY_GENERAL = 6;

	String WF_NAME_INTERNAL = "Moamalat_ICN_Internal";
	String WF_NAME_INCOMMING = "Moamalat_ICN_Incoming";
	String WF_NAME_OUTGOING = "Moamalat_ICN_Outgoing";
	String WF_NAME_CIRCULAR = "Moamalat_ICN_Circulars";
	String WF_NAME_DECISION = "Moamalat_ICN_Decisions";

	// sagia
	String WF_NAME_OUTGOING_SHL = "Moamalat_ICN_OutgoingSHL";
	String WF_NAME_QuickTask = "MOAMALAT_ICN_QUICKTASK";
	// sagia

	String WF_OLD_NAME_INTERNAL = "SC_Internal";
	String WF_OLD_NAME_INCOMMING = "SC_Incoming";
	String WF_OLD_NAME_OUTGOING = "SC_Outgoing";

	String WF_NAME_TECHNICAL_CORRESPONDENCE = "Moamalat_ICN_Technical";
	String LOG_APP_NAME_MOAMALAT = "moamalat";
	String GENERIC_PUBLIC_INBOX = "RolesPublicInbox";

}
