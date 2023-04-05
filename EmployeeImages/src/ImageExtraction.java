import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * This program demonstrates how to establish database connection to Microsoft
 * SQL Server.
 * 
 * @author www.codejava.net
 *
 */
public class ImageExtraction {
	// public static String
	// PATH="E:\\IBM\\WebSphere\\AppServer\\profiles\\AppSrv01\\installedApps\\RY0FNICNDEV1Node01Cell\\navigator.ear\\navigator.war\\usersImages\\";
	public static String PATH = "D:\\usersImages\\\\";

	public static void main(String[] args) {
		String connectionUrl = "jdbc:sqlserver://D1SHLDBSQDWV1.MCI.GOV:50855;databaseName=Moamalat1";

		ResultSet resultSet = null;
		String user = "p8admindev";
		String pass = "P@$$w0rd@@PS6";
		String providerURL = "ldap://MCI.GOV:389" ;
		String domain = "DC=MCI,DC=GOV";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection(connectionUrl, "Moamalat1", "$$Sp22@qwer");

			Statement statement = connection.createStatement();

			// Create and execute a SELECT SQL statement.
			String selectSql = "SELECT USERID FROM IO_EMPLOYEES WHERE ISACTIVE=1";
			resultSet = statement.executeQuery(selectSql);

			LdapContext context = getLdapContext(user, pass, providerURL);
			// Print results from select statement
			System.out.println("Employees size " + resultSet.getFetchSize());
			while (resultSet.next()) {
				// System.out.println(resultSet.getString(1));
				String accountName = resultSet.getString(1);
				getUserImage(accountName, context, domain);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static LdapContext getLdapContext(String username, String password, String providerUrl) {
		LdapContext context = null;
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.SECURITY_AUTHENTICATION, "Simple");
			env.put(Context.SECURITY_PRINCIPAL, username);
			env.put(Context.SECURITY_CREDENTIALS, password);
			env.put(Context.PROVIDER_URL, providerUrl);
			env.put(Context.REFERRAL, "follow");
			context = new InitialLdapContext(env, null);
			System.out.println("Connection Successful.");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return context;
	}

	public static void getUserImage(String accountName, LdapContext ctx, String searchin) {
		try {
			SearchControls constrains = getSearchControls();
			NamingEnumeration<SearchResult> answer = ctx.search(searchin,
					"(&(objectClass=user)(objectCategory=person)(sAMAccountName=" + accountName + "))", constrains);
			boolean hasImage = false;
			while (answer.hasMore()) {
				Attributes attr = answer.next().getAttributes();
				NamingEnumeration<? extends Attribute> t = attr.getAll();
				while (t.hasMoreElements()) {
					Attribute attribute = t.nextElement();
					if (attribute.getID().equals("thumbnailPhoto")) {
						hasImage = true;
						byte[] imagedata = (byte[]) attribute.get();
						saveImage(imagedata, accountName);
						break;
					}
					{
						System.out.println(accountName + " has no image");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveImage(byte[] image, String userId) {
		try {
			System.out.println("adding image for " + userId);
			ByteArrayInputStream bis = new ByteArrayInputStream(image);
			BufferedImage bImage2 = ImageIO.read(bis);

			ImageIO.write(bImage2, "jpg", new File(PATH + userId.toLowerCase() + ".jpg"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static SearchControls getSearchControls() {
		SearchControls cons = new SearchControls();
		cons.setSearchScope(SearchControls.SUBTREE_SCOPE);
		String[] attrIDs = { "distinguishedName", "thumbnailPhoto" };
		cons.setReturningAttributes(attrIDs);
		return cons;
	}
}
