package com.sebarys.gazeWebsite.web.controller;

import com.sebarys.gazeWebsite.model.AttachmentType;
import com.sebarys.gazeWebsite.model.dbo.Attachment;
import com.sebarys.gazeWebsite.model.dto.DtoAttachment;
import com.sebarys.gazeWebsite.service.AttachmentService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RestController
@RequestMapping(value = "/attachments")
public class AttachmentController extends AbstractController<Attachment, DtoAttachment, AttachmentService> {

    private static final String RESOURCE_DATA_PATH = "gazeWebsite-web/src/main/resources/data/";

//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public boolean uploadFile(final Long stimulId, final MultipartFile file) {
//
//        if(!file.isEmpty()) {
//            try {
//                if(file.getContentType().equals("application/zip") || file.getContentType().equals("application/x-zip-compressed")) {
//                    service.saveAttachements(stimulId, file, file.getName());
//                } else {
//                    service.saveFile(stimulId, file);
//                }
//            } catch (IOException ex) {
//                ex.printStackTrace(); //TODO fix exception handling
//            }
//            return true;
//        }
//        return false;
//    }

//    @RequestMapping(value = "/{stimulId}", method = RequestMethod.GET, produces="application/zip")
//    public byte[] getZip(@PathVariable final String stimulId) throws IOException {
//       //pomyslec jakdochodzic do path z ktorego ma paczke robic
//        final String stimulDirectoryPath = RESOURCE_DATA_PATH + stimulId;
//        File directory = new File(stimulDirectoryPath);
//        String[] files = directory.list();
//        directory = new File(stimulDirectoryPath + File.separator + files[0]);
//        files = directory.list();
//        if (files != null && files.length > 0) {
//            byte[] buffer = new byte[1024];
//
//            return service.zipFiles(directory, files);
//
//        }
//        return null;
//    }

    @RequestMapping(value = "/{attachmentId}", method = RequestMethod.GET, produces="application/zip")
    public byte[] getZip(@PathVariable final Long attachmentId) throws IOException {

//        File directory = new File(stimulDirectoryPath);
//        String[] files = directory.list();
//        directory = new File(stimulDirectoryPath + File.separator + files[0]);
//        files = directory.list();
//        if (files != null && files.length > 0) {
//            byte[] buffer = new byte[1024];
//
//            return service.zipFiles(directory, files);
//
//        }
        return service.getFile(attachmentId);
    }

//    private Set<Long> unzipAndSave(final Long stimulId, final MultipartFile file) throws IOException {
//        byte[] buffer = new byte[1024];
//        final Set<Long> identifiers = new HashSet<Long>();
//        File folder = new File(RESOURCE_DATA_PATH + stimulId);
//        if(!folder.exists()){
//            folder.mkdir();
//        }
//
//        final ZipInputStream zis = new ZipInputStream(file.getInputStream());
//        ZipEntry entry = zis.getNextEntry();
//        while (entry != null) {
//            File dbFile = new File(RESOURCE_DATA_PATH + stimulId + File.separator + entry.getName());
//            new File(dbFile.getParent()).mkdirs();
//
//            FileOutputStream fos = new FileOutputStream(dbFile);
//
//            int len;
//            while ((len = zis.read(buffer)) > 0) {
//                fos.write(buffer, 0, len);
//            }
//
//            fos.close();
//            identifiers.add(service.save(stimulId, file.getOriginalFilename()));
//            entry = zis.getNextEntry();
//        }
//
//        zis.closeEntry();
//        zis.close();
//        return identifiers;
//    }

}
