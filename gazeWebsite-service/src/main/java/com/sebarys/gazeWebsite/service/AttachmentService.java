package com.sebarys.gazeWebsite.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sebarys.gazeWebsite.model.dbo.Attachment;
import com.sebarys.gazeWebsite.model.dto.DtoAttachment;
import com.sebarys.gazeWebsite.model.dto.DtoStimul;
import com.sebarys.gazeWebsite.repo.AttachmentRepo;
import com.sebarys.gazeWebsite.service.mappers.AttachmentMapper;

@Service
public class AttachmentService extends AbstractService<Attachment, DtoAttachment, AttachmentRepo, AttachmentMapper> {

    private static final String RESOURCE_DATA_PATH = "gazeWebsite-web/src/main/resources/data/";

    @Autowired
    private StimulService stimulService;

    public long save(final Long stimulId, final String name) {
        DtoAttachment dtoAttachment = new DtoAttachment();
        dtoAttachment.setStimulId(stimulId);
        dtoAttachment.setName(name);
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
        save(stimulId, file.getOriginalFilename());
    }

    public Set<DtoAttachment> saveAttachements(final Long stimulId, final MultipartFile file, final String stimulName){

        final Set<DtoAttachment> attachments = new HashSet<>();
        byte[] buffer = new byte[1024];

        try{

            //create output directory if not exists
            File folder = new File(RESOURCE_DATA_PATH + stimulId + File.separator + stimulName);
            if(!folder.exists()){
                folder.mkdir();
            }

            final ZipInputStream zis =
                    new ZipInputStream(file.getInputStream());

            ZipEntry ze = zis.getNextEntry();

            while(ze!=null){

                final DtoAttachment attachment = new DtoAttachment();
                final String fileName = ze.getName();
                attachment.setName(fileName);
                final File newFile = new File(RESOURCE_DATA_PATH + stimulId + File.separator + stimulName + File.separator + fileName);

                //create all non existing folders else will throw exception
                new File(newFile.getParent()).mkdirs();

                final FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                attachment.setStimulId(stimulId);
                attachment.setId(save(stimulId, fileName));
                attachments.add(attachment);
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }

        return attachments;
    }

    public byte[] zipFiles(File directory, String[] files) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ZipOutputStream zos = new ZipOutputStream(baos);
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

    public byte[] getFile(final Long attachmentId) throws IOException {
        DtoAttachment attachment = findOne(attachmentId);
        DtoStimul stimul = stimulService.findOne(attachment.getStimulId());
        final File newFile = new File(stimul.getAttachmentsPath() + File.separator + attachment.getName());
        return IOUtils.toByteArray(new FileInputStream(newFile));
    }

}
