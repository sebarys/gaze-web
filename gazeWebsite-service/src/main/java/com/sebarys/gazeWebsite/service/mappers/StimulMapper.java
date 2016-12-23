package com.sebarys.gazeWebsite.service.mappers;

import com.sebarys.gazeWebsite.model.dbo.Attachment;
import com.sebarys.gazeWebsite.model.dbo.Stimul;
import com.sebarys.gazeWebsite.model.dto.DtoStimul;
import com.sebarys.gazeWebsite.repo.AttachmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class StimulMapper implements MapperInterface<Stimul, DtoStimul> {

    @Autowired
    AttachmentRepo attachmentRepo;

    @Override
    public Stimul convertToDBO(final DtoStimul dtoStimul) {
        final Stimul stimul = new Stimul();
        if(dtoStimul.getId() != null)
            stimul.setId(dtoStimul.getId());
        stimul.setName(dtoStimul.getName());
        if(dtoStimul.getAttachmentsPath() != null)
            stimul.setAttachmentsPath(dtoStimul.getAttachmentsPath());
        if(dtoStimul.getCreated() != null)
            stimul.setCreated(new Date(dtoStimul.getCreated()));
        if(dtoStimul.getAttachmentIds() != null) {
            final Set<Attachment> attachments = new HashSet<>();
            for(Long attachmentId : dtoStimul.getAttachmentIds()) {
                attachments.add(attachmentRepo.findOne(attachmentId));
            }
            stimul.setAttachments(attachments);
        }
        return stimul;
    }

    @Override
    public DtoStimul convertToDTO(final Stimul stimul) {
        final DtoStimul dtoStimul = new DtoStimul();
        dtoStimul.setId(stimul.getId());
        dtoStimul.setName(stimul.getName());
        if(stimul.getAttachmentsPath() != null)
            dtoStimul.setAttachmentsPath(stimul.getAttachmentsPath());
        if(stimul.getCreated() != null)
            dtoStimul.setCreated(stimul.getCreated().getTime());
        if(stimul.getAttachments() != null) {
            final Set<Long> attachmentIds = new HashSet<>();
            for(Attachment attachment : stimul.getAttachments()) {
                attachmentIds.add(attachment.getId());
            }
            dtoStimul.setAttachmentIds(attachmentIds);
        }
        return dtoStimul;
    }

    @Override
    public Iterable<Stimul> convertToDBO(final Iterable<DtoStimul> dtos) {
        final List<Stimul> stimuls = new ArrayList<>();
        dtos.forEach(dtoStimul -> stimuls.add(convertToDBO(dtoStimul)));
        return stimuls;
    }

    @Override
    public Iterable<DtoStimul> convertToDTO(final Iterable<Stimul> dbos) {
        final List<DtoStimul> dtoStimuls = new ArrayList<>();
        dbos.forEach(stimul -> dtoStimuls.add(convertToDTO(stimul)));
        return dtoStimuls;
    }
}
