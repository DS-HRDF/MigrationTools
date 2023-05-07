package com.dataserve.migration.spga.dao;

import com.dataserve.migration.spga.objects.Attachment;
import com.dataserve.migration.spga.objects.MigrationData;
import com.dataserve.migration.spga.util.Const;
import com.dataserve.migration.spga.util.SystemConstants;
import com.dataserve.migration.spga.util.Utils;
import com.filenet.api.collection.ContentElementList;
import com.filenet.api.collection.DocumentSet;
import com.filenet.api.collection.IndependentObjectSet;
import com.filenet.api.collection.UserSet;
import com.filenet.api.constants.*;
import com.filenet.api.core.*;
import com.filenet.api.property.Properties;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.filenet.api.security.Realm;
import com.filenet.api.util.Id;
import com.filenet.api.util.UserContext;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import static com.dataserve.migration.spga.util.Utils.threadID;

/************************
 *
 * Created By Mohammad Awwad 02-June-2020
 *
 ************************/

public class CEDaoOld {

    static {
        System.setProperty("Djava.security.auth.login.config", "jaas.conf.WSI");
    }

    final static Logger logger = Logger.getLogger(CEDaoOld.class);

    private ObjectStore objectStore = null;
    private boolean connected = false;

    private int ceConnection;



    public Attachment getAttachment(String id) {
        try {
            Document document = Factory.Document.fetchInstance(getObjectStore(), new Id("{" + id + "}"), null);
            InputStream inputStream = document.accessContentStream(0);
            byte[] bytes = IOUtils.toByteArray(inputStream);
            String mimeType = document.get_MimeType();

            return new Attachment(bytes, mimeType);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public void initObjectStore() throws Exception {
        try {
            UserContext uc = UserContext.get();

            logger.info(" -----------initObjectStore--------------- ");
            String uri = "";
            uri = Utils.getConfigs("uri.old");
            String username = Utils.getConfigs("username.old");
            String password = Utils.getConfigs("password.old");

            // Get the connection
            Connection conn = Factory.Connection.getConnection(uri);

            // Build the subject using the FileNetP8WSI stanza
            uc.pushSubject(UserContext.createSubject(conn, username, password, "FileNetP8WSI"));

            // Get the default domain
            Domain domain = Factory.Domain.getInstance(conn, null);
            // Get an object store
            objectStore = Factory.ObjectStore.fetchInstance(domain, Utils.getConfigs("os.old"), null);
            setConnected(true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }


    public ObjectStore getObjectStore() {
        if (objectStore == null) {
            try {
                initObjectStore();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        return objectStore;
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


