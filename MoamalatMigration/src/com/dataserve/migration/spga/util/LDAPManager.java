package com.dataserve.migration.spga.util;


import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;
import java.util.Map;

public class LDAPManager {



	public LdapContext getLdapContext() {
        LdapContext context = null;
        try {
            String adminUser="p8adminprd";
            String adminPassword="j7HLtdtNDHR3Hg66ExtZxhTSM";


            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.SECURITY_AUTHENTICATION, "Simple");
            env.put(Context.SECURITY_PRINCIPAL, "p8adminprd");
            env.put(Context.SECURITY_CREDENTIALS, "j7HLtdtNDHR3Hg66ExtZxhTSM");
            env.put(Context.PROVIDER_URL, "ldap://MCI.GOV:389");
            env.put(Context.REFERRAL, "follow");
            context = new InitialLdapContext(env, null);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return context;
    }
	
	public boolean isUserExistsInLDAP(String accountName) {
        try {
            SearchControls constrains = new SearchControls();
            constrains.setSearchScope(SearchControls.SUBTREE_SCOPE);
            constrains.setReturningAttributes(new String[]{"thumbnailPhoto"});

            NamingEnumeration<SearchResult> answer = getLdapContext().search("DC=MCI,DC=GOV",
                    "(&(objectClass=user)(objectCategory=person)(sAMAccountName="
                    + accountName + "))", constrains);
            
            while (answer.hasMore()) {
                Attributes attr = answer.next().getAttributes();
               return true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }



    public void isUserMemberOf(String accountName) {
        try {
            System.out.println("... isUserMemberOf "+accountName);
            SearchControls constrains = new SearchControls();
            constrains.setSearchScope(SearchControls.SUBTREE_SCOPE);
            constrains.setReturningAttributes(new String[]{"memberOf"});

            NamingEnumeration<SearchResult> answer = getLdapContext().search("DC=MCI,DC=GOV",
                    "(&(objectClass=user)(objectCategory=person)(sAMAccountName="
                            + accountName + "))", constrains);

            while (answer.hasMore()) {
                SearchResult next = answer.next();
                Attribute memberOf = next.getAttributes().get("memberOf");

                System.out.println("memberOf.get() = "+memberOf.get());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        new LDAPManager().isUserMemberOf("mawwad");
    }
}
