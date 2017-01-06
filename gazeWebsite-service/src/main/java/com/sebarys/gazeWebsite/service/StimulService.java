package com.sebarys.gazeWebsite.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sebarys.gazeWebsite.model.dbo.Stimul;
import com.sebarys.gazeWebsite.model.dto.ActionResult;
import com.sebarys.gazeWebsite.model.dto.DtoAttachment;
import com.sebarys.gazeWebsite.model.dto.DtoStimul;
import com.sebarys.gazeWebsite.repo.StimulRepo;
import com.sebarys.gazeWebsite.service.mappers.StimulMapper;

@Service
public class StimulService extends AbstractService<Stimul, DtoStimul, StimulRepo, StimulMapper> {

    //FIXME zmienic na logger albo usunac wpisy
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    ResultService resultService;

    private static final String RESOURCE_DATA_PATH = "gazeWebsite-web/src/main/resources/data";

    public ActionResult createStimul(final String stimulName, final MultipartFile file)
    {
        DtoStimul dtoStimul = new DtoStimul();
        dtoStimul.setName(stimulName);
        dtoStimul.setCreated(new Date().getTime());
        final Stimul newStimul = save(dtoStimul);
        final Set<DtoAttachment> attachments = attachmentService.saveAttachements(newStimul.getId(), file, stimulName);
        dtoStimul = getMapper().convertToDTO(newStimul);
        dtoStimul.setAttachments(attachments);
        dtoStimul.setAttachmentsPath(RESOURCE_DATA_PATH + File.separator + dtoStimul.getId()
                + File.separator + dtoStimul.getName());
        save(dtoStimul);
        return new ActionResult(true, "Stimul created successfully with ID: " + dtoStimul.getId());
    }

    public DtoStimul getStimulDetails(final Long stimulId) {
        return findOne(stimulId);
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

    public ActionResult deleteStimul(final Long stimulId) {
        final DtoStimul stimulToDelete = findOne(stimulId);
        if(stimulToDelete == null) {
            return new ActionResult(false, "Coundn't find given stimul");
        }
        stimulToDelete.getResults()
                .forEach(dtoResult -> resultService.deleteResult(dtoResult.getId()));
        File rootFolder = new File(stimulToDelete.getAttachmentsPath());

        deleteFile(rootFolder);

        delete(stimulToDelete);
        return new ActionResult(true, "Stimul deleted successfully");
    }

    private static void deleteFile(final File file)
    {
        if(file.isDirectory()){
            //directory is empty, then deleteFile it
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
                    File fileToDelete = new File(file, temp);
                    //recursive deleteFile
                    deleteFile(fileToDelete);
                }
                //check the directory again, if empty then deleteFile it
                if(file.list().length==0){
                    file.delete();
                    System.out.println("Directory is deleted : "
                            + file.getAbsolutePath());
                }
            }
        }
        else {
            //if file, then deleteFile it
            file.delete();
            System.out.println("File is deleted : " + file.getAbsolutePath());
        }
    }

    public Page<DtoStimul> listAllByPage(final Pageable pageable) {
        final Page<Stimul> stimulPage = repo.findAll(pageable);
        final Page<DtoStimul> dtoStimuls = stimulPage.map(stimul -> getMapper().convertToDTO(stimul));
        dtoStimuls.getContent()
                .forEach(dtoStimul -> dtoStimul.setAttachments(null));
        return dtoStimuls;
    }

    public Page<DtoStimul> searchStimulByName(final Pageable pageable, final String stimulName) {
        final Page<Stimul> stimulPage = repo.findByNameLike("%" + stimulName + "%", pageable);
        final Page<DtoStimul> dtoStimuls = stimulPage.map(stimul -> getMapper().convertToDTO(stimul));
        dtoStimuls.getContent()
                .forEach(dtoStimul -> dtoStimul.setAttachments(null));
        return dtoStimuls;
    }
}
