package com.sebarys.gazeWebsite.model.dto;

public class ActionResult
{

	private boolean isSuccess;

	private String info;

	public ActionResult(final boolean isSuccess, final String info)
	{
		this.isSuccess = isSuccess;
		this.info = info;
	}

	public boolean isSuccess()
	{
		return isSuccess;
	}

	public void setSuccess(final boolean success)
	{
		isSuccess = success;
	}

	public String getInfo()
	{
		return info;
	}

	public void setInfo(final String info)
	{
		this.info = info;
	}
}
