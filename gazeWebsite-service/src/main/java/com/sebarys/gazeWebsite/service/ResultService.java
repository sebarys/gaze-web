package com.sebarys.gazeWebsite.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sebarys.gazeWebsite.model.dbo.Result;
import com.sebarys.gazeWebsite.model.dbo.Stimul;
import com.sebarys.gazeWebsite.model.dto.ActionResult;
import com.sebarys.gazeWebsite.model.dto.DtoResult;
import com.sebarys.gazeWebsite.model.dto.DtoStimul;
import com.sebarys.gazeWebsite.repo.ResultRepo;
import com.sebarys.gazeWebsite.service.mappers.ResultMapper;

import pl.kasprowski.tet.data.Results;

@Service
public class ResultService extends AbstractService<Result, DtoResult, ResultRepo, ResultMapper> {


	private static final Logger logger = Logger.getLogger(ResultService.class);
	private static final String RESOURCE_DATA_PATH = "gazeWebsite-web/src/main/resources/data";
	private static final String PROFILE_READINGS = "PROFILE";

	@Autowired
	StimulService stimulService;

	public ActionResult saveResult(final Long stimulId, final MultipartFile file) {
		DtoResult dtoResult = new DtoResult();
		dtoResult.setName(file.getOriginalFilename());
		dtoResult.setStimulId(stimulId);
		dtoResult.setCreated(new Date().getTime());
		final Result newResult = save(dtoResult);
		dtoResult = getMapper().convertToDTO(newResult);

		try
		{
			dtoResult.setAttachmentPath(saveResultFile(newResult.getStimul().getId(), newResult.getId(), file));
		}
		catch (IOException e)
		{
			return new ActionResult(false, "Error during saving file" + e.getMessage());
		}
		Results resultDeserlization = Results.deserialize(dtoResult.getAttachmentPath());
		final Map profileData = (Map<String,String>) resultDeserlization
				.getReadings(PROFILE_READINGS)
				.get(0).getData();
		dtoResult.setProfile(profileData);
		final DtoStimul stimulDto = stimulService.findOne(stimulId);
		if(stimulDto.getProfile().isEmpty()) {
			stimulDto.setProfile(profileData);
			stimulService.save(stimulDto);
		}
		save(dtoResult);
		return new ActionResult(true, "Result saved successfuly with ID: " + dtoResult.getId());
	}

	public Page<DtoResult> listAllByPage(final Long stimulId, final Pageable pageable) {
		final DtoStimul dtoStimul = stimulService.findOne(stimulId);
		final Stimul stimul = stimulService.getMapper().convertToDBO(dtoStimul);
		final Page<Result> resultsPage = repo.findByStimul(stimul, pageable);
		return resultsPage.map(result -> getMapper().convertToDTO(result));
	}

	public Page<DtoResult> listAllByFilteredPage(final Long stimulId, final Pageable pageable,
			final String key, final String value) {
		final Page<Result> filteredResultsPage = repo.findByProfileKey(stimulId, key, value, pageable);
		return filteredResultsPage.map(result -> getMapper().convertToDTO(result));
	}

	public byte[] getResult(final Long resultId) {
		final DtoResult resultDto = findOne(resultId);
		final File resultFile = new File(resultDto.getAttachmentPath());
		byte[] fileBytes = new byte[(int) resultFile.length()];

		try
		{
			FileInputStream fileInputStream = new FileInputStream(resultFile);
			fileInputStream.read(fileBytes);
			fileInputStream.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		return fileBytes;
	}

	public ActionResult deleteResult(final Long resultId) {
		final DtoResult resultToDelete = findOne(resultId);
		if(resultToDelete == null) {
			return new ActionResult(false, "Couldn't find result with given id");
		}
		File resultFile = new File(resultToDelete.getAttachmentPath());

		deleteFile(resultFile);
		delete(resultToDelete);
		return new ActionResult(true, "Result removed successfully");
	}

	private String saveResultFile(final long stimulId, final long resultId, final MultipartFile file) throws IOException
	{
		final String path = RESOURCE_DATA_PATH + File.separator + stimulId + File.separator +
				"results" + File.separator + resultId + File.separator + file.getOriginalFilename();
		final File resultFile = new File(path);
		new File(resultFile.getParent()).mkdirs();

		InputStream inputStream = file.getInputStream();
		FileOutputStream outputStream = new FileOutputStream(resultFile);

		int read = 0;
		byte[] bytes = new byte[1024];

		while ((read = inputStream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, read);
		}
		inputStream.close();
		outputStream.close();

		return path;
	}

	private static void deleteFile(final File file)
	{
		if(file.isDirectory()){
			//directory is empty, then deleteFile it
			if(file.list().length==0){

				file.delete();
				logger.info("Directory is deleted : " + file.getAbsolutePath());
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
					logger.info("Directory is deleted : " + file.getAbsolutePath());
				}
			}
		}
		else {
			//if file, then deleteFile it
			file.delete();
			logger.info("File is deleted : " + file.getAbsolutePath());
		}
	}
}
