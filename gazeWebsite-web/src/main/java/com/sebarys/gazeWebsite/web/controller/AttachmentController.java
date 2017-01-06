package com.sebarys.gazeWebsite.web.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sebarys.gazeWebsite.model.dbo.Attachment;
import com.sebarys.gazeWebsite.model.dto.DtoAttachment;
import com.sebarys.gazeWebsite.service.AttachmentService;

@RestController
@RequestMapping(value = "/attachments")
public class AttachmentController extends AbstractController<Attachment, DtoAttachment, AttachmentService> {

    private static final String RESOURCE_DATA_PATH = "gazeWebsite-web/src/main/resources/data/";

    @RequestMapping(value = "/{attachmentId}", method = RequestMethod.GET, produces="application/zip")
    public byte[] getZip(@PathVariable final Long attachmentId) throws IOException {

        return service.getFile(attachmentId);
    }

}
