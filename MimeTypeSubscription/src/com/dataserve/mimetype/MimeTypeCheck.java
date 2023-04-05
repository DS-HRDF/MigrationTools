package com.dataserve.mimetype;

import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Document;
import com.filenet.api.engine.EventActionHandler;
import com.filenet.api.events.ObjectChangeEvent;
import com.filenet.api.exception.EngineRuntimeException;
import com.filenet.api.exception.ExceptionCode;
import com.filenet.api.util.Id;
import com.filenet.apiimpl.util.ByteArraySpillStreamFactory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.tika.Tika;
import org.apache.tika.utils.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MimeTypeCheck implements EventActionHandler {


    public MimeTypeCheck() {
        // default contractor
    }

    private static final String[] DEFAULT_ALLOWED_MIMETYPES = new String[]{"application/x-tika-ooxml", "image/png", "image/jpeg", "image/pjpeg",
            "image/pngimage/tiff", "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/msword", "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.ms-powerpoint",
            "application/vnd.openxmlformats-officedocument.presentationml.presentation", "application/vnd.ms-access",
            "application/pdf"};

    @Override
    public void onEvent(ObjectChangeEvent event, Id subId) throws EngineRuntimeException {
        System.out.println("MimeTypeCheck start v4");
        try {
            System.out.println("MimeTypeCheck 1");

            List<String> defaultAllowedMimeTypesList = Arrays.asList(DEFAULT_ALLOWED_MIMETYPES);
            System.out.println("MimeTypeCheck 2");
            Document doc = (Document) event.get_SourceObject();
            System.out.println("MimeTypeCheck 3");
//            String documentTitle = doc.getProperties().getStringValue("DocumentTitle");
//            doc.getProperties().putValue("DocumentTitle",documentTitle.replaceAll("[^A-Za-z0-9]",""));
//            doc.save(RefreshMode.REFRESH);

            InputStream is = doc.accessContentStream(0);
            if(sizeExceedLimit(is)){
                System.out.println("MimeTypeCheck size fail exceed limit");
                throw new EngineRuntimeException(ExceptionCode.CONTENT_SA_EXCEEDED_MAX_SIZE);

            }
            System.out.println("MimeTypeCheck 4");
            Tika tika = new Tika();
            System.out.println("MimeTypeCheck 5");
            String fileType = tika.detect(is);
            System.out.println("MimeTypeCheck 6");
            boolean isExists = false;
            System.out.println("fileType: " + fileType);
            if (StringUtils.isEmpty(fileType)) {
                System.out.println("MimeTypeCheck 6");
                throw new EngineRuntimeException(ExceptionCode.CLASSIFY_UNKNOWN_MIME_TYPE);
            } else {
                isExists = isMimeExists(event, defaultAllowedMimeTypesList, fileType);
            }
            if (!isExists) {
                throw new EngineRuntimeException(ExceptionCode.CLASSIFY_UNKNOWN_MIME_TYPE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new EngineRuntimeException(ExceptionCode.CLASSIFY_UNKNOWN_MIME_TYPE);
        }
        System.out.println("MimeTypeCheck end");


    }

    private boolean sizeExceedLimit(InputStream inputStream) throws IOException {
        int available = inputStream.available();
        System.out.println("MimeTypeCheck available "+available);

        long fileSizeInBytes = available;
// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        long fileSizeInKB = fileSizeInBytes / 1024;
// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        long fileSizeInMB = fileSizeInKB / 1024;
        return fileSizeInMB >= 30;
    }


    private boolean isMimeExists(ObjectChangeEvent event, List<String> defaultAllowedMimeTypesList, String fileType) {
        try {
            System.out.println("MimeTypeCheck 7");

            Iterator<String> allowedMimeItr = defaultAllowedMimeTypesList.iterator();

            while (allowedMimeItr.hasNext()) {
                String mimeType = allowedMimeItr.next();
                if (!StringUtils.isEmpty(mimeType) && fileType.equalsIgnoreCase(mimeType)) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("MimeTypeCheck 8");

        return false;
    }

}
