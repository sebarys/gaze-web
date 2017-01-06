package com.sebarys.gazeWebsite.service.mappers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sebarys.gazeWebsite.model.dbo.Result;
import com.sebarys.gazeWebsite.model.dto.DtoResult;
import com.sebarys.gazeWebsite.model.dto.DtoStimul;
import com.sebarys.gazeWebsite.service.StimulService;

@Component
public class ResultMapper implements MapperInterface<Result, DtoResult>
{
	@Autowired
	private StimulService stimulService;

	@Override
	public Result convertToDBO(final DtoResult dtoResult)
	{
		final Result result = new Result();
		if(dtoResult.getId() != null) {
			result.setId(dtoResult.getId());
		}
		if(dtoResult.getAttachmentPath() != null) {
			result.setAttachmentPath(dtoResult.getAttachmentPath());
		}
		if(dtoResult.getCreated() != null) {
			result.setCreated(new Date(dtoResult.getCreated()));
		}
		if(dtoResult.getStimulId() != null) {
			DtoStimul stimul = stimulService.findOne(dtoResult.getStimulId());
			if(stimul != null)
				result.setStimul(stimulService.getMapper().convertToDBO(stimul));
		}
		if(dtoResult.getProfile() != null) {
			result.setProfile(dtoResult.getProfile());
		}
		result.setName(dtoResult.getName());
		return result;
	}

	@Override
	public DtoResult convertToDTO(final Result result)
	{
		final DtoResult dtoResult = new DtoResult();
		dtoResult.setId(result.getId());
		dtoResult.setName(result.getName());
		if (result.getAttachmentPath() != null) {
			dtoResult.setAttachmentPath(result.getAttachmentPath());
		}
		if (result.getCreated() != null) {
			dtoResult.setCreated(result.getCreated().getTime());
		}
		if (result.getStimul() != null) {
			dtoResult.setStimulId(result.getStimul().getId());
		}
		if (result.getProfile() != null) {
			dtoResult.setProfile(result.getProfile());
		}
		return dtoResult;
	}

	@Override
	public Iterable<Result> convertToDBO(final Iterable<DtoResult> dtoResults)
	{
		final List<Result> results = new ArrayList<>();
		dtoResults.forEach(dtoResult -> results.add(convertToDBO(dtoResult)));
		return results;
	}

	@Override
	public Iterable<DtoResult> convertToDTO(final Iterable<Result> results)
	{
		final List<DtoResult> dtoResults = new ArrayList<>();
		results.forEach(result -> dtoResults.add(convertToDTO(result)));
		return dtoResults;
	}
}
