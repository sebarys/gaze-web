package com.sebarys.gazeWebsite.service;

import com.sebarys.gazeWebsite.model.AttachmentType;
import com.sebarys.gazeWebsite.model.dbo.Attachment;
import com.sebarys.gazeWebsite.model.dto.DtoAttachment;
import com.sebarys.gazeWebsite.repo.AttachmentRepo;
import com.sebarys.gazeWebsite.service.mappers.AttachmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Service
public class AttachmentService extends AbstractService<Attachment, DtoAttachment, AttachmentRepo, AttachmentMapper> {

    private static final String RESOURCE_DATA_PATH = "gazeWebsite-web/src/main/resources/data/";

    public long save(final Long stimulId, final String name, final AttachmentType attachmentType) {
        DtoAttachment dtoAttachment = new DtoAttachment();
        dtoAttachment.setStimulId(stimulId);
        dtoAttachment.setName(name);
        dtoAttachment.setAttachmentType(attachmentType.toString());
        final Attachment attachement = repo.save(mapper.convertToDBO(dtoAttachment));
        return attachement.getId();

    }

    public void saveFile(final Long stimulId, final MultipartFile file) throws IOException {
        File dbFile = new File(RESOURCE_DATA_PATH + stimulId);
        if(!dbFile.exists()) {
            dbFile.mkdirs();
        }
        dbFile = new File(RESOURCE_DATA_PATH + stimulId + File.separator + file.getOriginalFilename());
        BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(dbFile));

        FileCopyUtils.copy(file.getInputStream(), stream);
        stream.close();
        save(stimulId, file.getOriginalFilename(), AttachmentType.PHOTO);
    }

    public Set<Long> saveAttachements(final Long stimulId, final MultipartFile file, final String stimulName){
        final Set<Long> identifiers = new HashSet<Long>();
        byte[] buffer = new byte[1024];

        try{

            //create output directory if not exists
            File folder = new File(RESOURCE_DATA_PATH + stimulId + File.separator + stimulName);
            if(!folder.exists()){
                folder.mkdir();
            }

            //get the zip file content
            ZipInputStream zis =
                    new ZipInputStream(file.getInputStream());
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while(ze!=null){

                String fileName = ze.getName();
                File newFile = new File(RESOURCE_DATA_PATH + stimulId + File.separator + stimulName + File.separator + fileName);

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                identifiers.add(save(stimulId, fileName, AttachmentType.PHOTO));
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }

        return identifiers;
    }

    public byte[] zipFiles(File directory, String[] files) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        byte bytes[] = new byte[2048];

        for (String fileName : files) {
            FileInputStream fis = new FileInputStream(directory.getPath() + "/" + fileName);
            BufferedInputStream bis = new BufferedInputStream(fis);

            zos.putNextEntry(new ZipEntry(fileName));

            int bytesRead;
            while ((bytesRead = bis.read(bytes)) != -1) {
                zos.write(bytes, 0, bytesRead);
            }
            zos.closeEntry();
            bis.close();
            fis.close();
        }
        if (zos != null) {
            zos.finish();
            zos.flush();
            zos.close();
        }
        baos.close();

        return baos.toByteArray();
    }
}
