package com.sebarys.gazeWebsite.service.mappers;

import com.sebarys.gazeWebsite.model.AttachmentType;
import com.sebarys.gazeWebsite.model.dbo.Attachment;
import com.sebarys.gazeWebsite.model.dbo.Stimul;
import com.sebarys.gazeWebsite.model.dto.DtoAttachment;
import com.sebarys.gazeWebsite.repo.StimulRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AttachmentMapper implements MapperInterface<Attachment, DtoAttachment> {

    @Autowired
    StimulRepo stimulRepo;

    @Override
    public Attachment convertToDBO(final DtoAttachment dtoAttachment) {
        final Attachment attachment = new Attachment();
        //attachment.setId(dtoAttachment.getId());
        attachment.setName(dtoAttachment.getName());
        attachment.setAttachmentType(AttachmentType.PHOTO);
        if(dtoAttachment.getStimulId() != null) {
            Stimul stimul = stimulRepo.findOne(dtoAttachment.getStimulId());
            if(stimul != null) {
                attachment.setStimul(stimul);
            }
        }
        return attachment;
    }

    @Override
    public DtoAttachment convertToDTO(final Attachment attachment) {
        final DtoAttachment dtoAttachment = new DtoAttachment();
        dtoAttachment.setId(attachment.getId());
        dtoAttachment.setName(attachment.getName());
        if(attachment.getStimul() != null) {
            dtoAttachment.setStimulId(attachment.getStimul().getId());
        }
        return dtoAttachment;
    }

    @Override
    public Iterable<Attachment> convertToDBO(final Iterable<DtoAttachment> dtos) {
        final List<Attachment> attachments = new ArrayList<>();
        dtos.forEach(dtoAttachment -> attachments.add(convertToDBO(dtoAttachment)));
        return attachments;
    }

    @Override
    public Iterable<DtoAttachment> convertToDTO(final Iterable<Attachment> dbos) {
        final List<DtoAttachment> dtoAttachments = new ArrayList<>();
        dbos.forEach(attachment -> dtoAttachments.add(convertToDTO(attachment)));
        return dtoAttachments;
    }
}
