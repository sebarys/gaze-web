package com.sebarys.gazeWebsite.web.controller;

import com.sebarys.gazeWebsite.model.dbo.Stimul;
import com.sebarys.gazeWebsite.model.dto.DtoStimul;
import com.sebarys.gazeWebsite.service.AttachmentService;
import com.sebarys.gazeWebsite.service.StimulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

@RestController
@RequestMapping(value = "/stimuls")
public class StimulsController extends AbstractController<Stimul, DtoStimul, StimulService> {

    @Transactional
    @RequestMapping(value = "", method = RequestMethod.POST)
    public boolean createStimul(final String stimulName, final MultipartFile file)
    {
        if(stimulName.isEmpty() || stimulName.length()> 100)
            return false;
        return service.createStimul(stimulName, file);
    }

    @RequestMapping(value = "/{stimulId}", method = RequestMethod.GET)
    public byte[] getStimul(@PathVariable final Long stimulId) throws IOException {
        if(stimulId == null){
            return null;
        }
        return service.getStimul(stimulId);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<DtoStimul> getStimulPage(Pageable pageable) {

        return service.listAllByPage(pageable);
    }

    @Transactional
    @RequestMapping(value = "/{stimulId}", method = RequestMethod.DELETE)
    public boolean deleteStimul(@PathVariable final Long stimulId) throws IOException {
        if(stimulId == null){
            return false;
        }
        return service.deleteStimul(stimulId);
    }

}
