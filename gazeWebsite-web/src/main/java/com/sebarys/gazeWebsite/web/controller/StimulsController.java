package com.sebarys.gazeWebsite.web.controller;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sebarys.gazeWebsite.model.dbo.Stimul;
import com.sebarys.gazeWebsite.model.dto.ActionResult;
import com.sebarys.gazeWebsite.model.dto.DtoStimul;
import com.sebarys.gazeWebsite.service.StimulService;

@RestController
@RequestMapping(value = "/stimuls")
public class StimulsController extends AbstractController<Stimul, DtoStimul, StimulService> {

    @Transactional
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ActionResult createStimul(final String stimulName, final MultipartFile file)
    {
        if(stimulName.isEmpty() || stimulName.length()> 100) {
            return new ActionResult(false, "Given stimul name is invalid");
        }
        if(file == null || !file.getContentType().equals("application/zip")) {
            return new ActionResult(false, "Given file is in wrong format, or null (*.zip expected)");
        }

        return service.createStimul(stimulName, file);
    }

    @RequestMapping(value = "/{stimulId}", method = RequestMethod.GET)
    public byte[] getStimul(@PathVariable final Long stimulId) throws IOException {
        if(stimulId == null){
            return null;
        }
        return service.getStimul(stimulId);
    }

    @RequestMapping(value = "/{stimulId}/details", method = RequestMethod.GET)
    public DtoStimul getStimulDetails(@PathVariable final Long stimulId)
	{
        if(stimulId == null){
            return null;
        }
		return service.getStimulDetails(stimulId);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<DtoStimul> getStimulPage(Pageable pageable, final String searchPhrase) {
        if(searchPhrase != null) {
            return service.searchStimulByName(pageable, searchPhrase);
        }
        return service.listAllByPage(pageable);
    }

    @Transactional
    @RequestMapping(value = "/{stimulId}", method = RequestMethod.DELETE)
    public ActionResult deleteStimul(@PathVariable final Long stimulId)
	{
        if(stimulId == null){
            return new ActionResult(false, "Stimul id couldn't be null");
        }
        return service.deleteStimul(stimulId);
    }

}
