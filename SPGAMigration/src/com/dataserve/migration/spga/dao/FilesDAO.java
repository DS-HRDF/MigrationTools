package com.dataserve.migration.spga.dao;

import com.dataserve.migration.spga.controller.MofController;
import com.dataserve.migration.spga.util.Utils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/************************
 *
 * Created By Mohammad Awwad 02-June-2020
 *
 ************************/
public class FilesDAO {
    final static Logger logger = Logger.getLogger(FilesDAO.class);


    public static String SaveFile(byte[] bytes, String fileName, String mimeType) throws Exception {
        String folderPath = Utils.getConfigs("mof.folder");

        Path path = Paths.get(folderPath + fileName + "." + extension(mimeType));

        Files.write(path, bytes);
        return fileName + "." + extension(mimeType);
    }

    public static byte[] getFileBytes(String fileName) throws Exception {
        String folderPath = Utils.getConfigs("mof.folder");

        File file = new File(folderPath + fileName);
        byte[] bytes = Files.readAllBytes(file.toPath());
        return bytes;
    }

    public static String extension(String mimeType) throws Exception {
        if (mimeType.equals("application/pdf")) {
            return "pdf";
        } else if (mimeType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
            return "docx";
        } else if (mimeType.equals("image/png")) {
            return "png";
        } else if (mimeType.equals("image/jpeg")) {
            return "jpg";
        }else if (mimeType.equals("text/webeditor")) {
            return "html";
        }else if (mimeType.equals("image/tiff")) {
            return "tiff";
        }else if (mimeType.equals("application/msword")) {
            return "doc";
        }else if (mimeType.equals("application/msaccess")) {
            return "mdb";
        }
        throw new Exception("MimeType " + mimeType + " Extension not found");
    }

//    public static String mimeType(String extension) throws Exception {
//        if (extension.equals("pdf")) {
//            return "application/pdf";
//        } else if (extension.equals("docx")) {
//            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
//        } else if (extension.equals("png")) {
//            return "image/png";
//        } else if (extension.equals("jpg")) {
//            return "image/jpeg";
//        }else if (extension.equals("html")) {
//            return "text/webeditor";
//        }
//        throw new Exception("MimeType " + extension + " Extension not found");
//    }
}
