package com.sebarys.gazeWebsite.web.controller;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sebarys.gazeWebsite.model.dbo.Result;
import com.sebarys.gazeWebsite.model.dto.ActionResult;
import com.sebarys.gazeWebsite.model.dto.DtoResult;
import com.sebarys.gazeWebsite.service.ResultService;

@RestController
@RequestMapping(value = "/stimuls/{stimulId}/results")
public class ResultsController extends AbstractController<Result, DtoResult, ResultService> {
	private static final Logger logger = Logger.getLogger(ResultService.class);

	@Transactional
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ActionResult createResult(@PathVariable final Long stimulId, final MultipartFile file)
	{
		if(file == null) {
			return new ActionResult(false, "File couldn't be null!");
		}
		if(!file.getContentType().equals("application/octet-stream")) {
			return new ActionResult(false, "Wrong result file type (*.gdt expected), received: " + file.getContentType());
		}
		return service.saveResult(stimulId, file);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public Page<DtoResult> getResultPage(@PathVariable final Long stimulId, final Pageable pageable,
			final String key, final String value) {
		if(key != null && value != null) {
			return service.listAllByFilteredPage(stimulId, pageable, key, value);
		}
		return service.listAllByPage(stimulId, pageable);
	}

	@RequestMapping(value = "/{resultId}", method = RequestMethod.GET)
	public byte[] getResult(@PathVariable final Long resultId)
	{
		if(resultId == null){
			return null;
		}
		return service.getResult(resultId);
	}

	@Transactional
	@RequestMapping(value = "/{resultId}", method = RequestMethod.DELETE)
	public ActionResult deleteResult(@PathVariable final Long resultId)
	{
		if(resultId == null){
			return new ActionResult(false, "Result to delete id couldn't be null");
		}
		return service.deleteResult(resultId);
	}

	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public byte[] getResultsData(@PathVariable final Long stimulId, final String key, final String value) throws Exception {
		if(key != null && value != null) {
//			return service.listAllByFilteredPage(stimulId, pageable, key, value);
		}

		return service.getResultsData(stimulId, key, value);
	}
}
