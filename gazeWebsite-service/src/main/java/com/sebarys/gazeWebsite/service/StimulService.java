package com.sebarys.gazeWebsite.service;

import com.sebarys.gazeWebsite.model.dbo.Stimul;
import com.sebarys.gazeWebsite.model.dto.DtoStimul;
import com.sebarys.gazeWebsite.repo.StimulRepo;
import com.sebarys.gazeWebsite.service.mappers.StimulMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

@Service
public class StimulService extends AbstractService<Stimul, DtoStimul, StimulRepo, StimulMapper> {

    //FIXME zmienic na logger albo usunac wpisy
    @Autowired
    AttachmentService attachmentService;

    private static final String RESOURCE_DATA_PATH = "gazeWebsite-web/src/main/resources/data";

    public boolean createStimul(final String stimulName, final MultipartFile file)
    {
        if(stimulName.isEmpty() || stimulName.length()> 100)
            return false;
        DtoStimul dtoStimul = new DtoStimul();
        dtoStimul.setName(stimulName);
        dtoStimul.setCreated(new Date().getTime());
        final Stimul newStimul = save(dtoStimul);
        final Set<Long> attachmentsIds = attachmentService.saveAttachements(newStimul.getId(), file, stimulName);
        dtoStimul = getMapper().convertToDTO(newStimul);
        dtoStimul.setAttachmentIds(attachmentsIds);
        dtoStimul.setAttachmentsPath(RESOURCE_DATA_PATH + File.separator + dtoStimul.getId()
                + File.separator + dtoStimul.getName());
        save(dtoStimul);
        return true;
    }

    public byte[] getStimul(final Long stimulId) throws IOException {
        final DtoStimul stimul = findOne(stimulId);
        final String stimulDirectoryPath = stimul.getAttachmentsPath();
        File directory = new File(stimulDirectoryPath);
        String[] files = directory.list();
        directory = new File(stimulDirectoryPath + File.separator + files[0]);
        files = directory.list();
        if (files != null && files.length > 0) {
            byte[] buffer = new byte[1024];
            return attachmentService.zipFiles(directory, files);
        }
        return null;
    }

    public boolean deleteStimul(final Long stimulId) {
        final DtoStimul stimulToDelete = findOne(stimulId);
        if(stimulToDelete == null) {
            return false;
        }
        File rootFolder = new File(stimulToDelete.getAttachmentsPath());
        if(rootFolder == null) {
            return false;
        }
        try {
            delete(rootFolder);
        } catch (IOException e) {
            return false;
        }
        delete(stimulToDelete);
        return true;
    }

    private static void delete(File file) throws IOException{
        if(file.isDirectory()){
            //directory is empty, then delete it
            if(file.list().length==0){

                file.delete();
                System.out.println("Directory is deleted : "
                        + file.getAbsolutePath());
            }
            else {
                //list all the directory contents
                String files[] = file.list();
                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);
                    //recursive delete
                    delete(fileDelete);
                }
                //check the directory again, if empty then delete it
                if(file.list().length==0){
                    file.delete();
                    System.out.println("Directory is deleted : "
                            + file.getAbsolutePath());
                }
            }
        }
        else {
            //if file, then delete it
            file.delete();
            System.out.println("File is deleted : " + file.getAbsolutePath());
        }
    }

    public Page<DtoStimul> listAllByPage(Pageable pageable) {
        Page<Stimul> stimulPage = repo.findAll(pageable);
        return stimulPage.map(stimul -> getMapper().convertToDTO(stimul));
    }
}
