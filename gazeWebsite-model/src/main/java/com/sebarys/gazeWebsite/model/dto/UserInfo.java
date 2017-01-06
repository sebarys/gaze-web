package com.sebarys.gazeWebsite.model.dto;

public class UserInfo {

	private String username;

	private boolean isUser;

	private boolean isAdmin;

	public UserInfo(final String username, final boolean isUser, final boolean isAdmin)
	{
		this.username = username;
		this.isUser = isUser;
		this.isAdmin = isAdmin;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(final String username)
	{
		this.username = username;
	}

	public boolean isUser()
	{
		return isUser;
	}

	public void setUser(final boolean user)
	{
		isUser = user;
	}

	public boolean isAdmin()
	{
		return isAdmin;
	}

	public void setAdmin(final boolean admin)
	{
		isAdmin = admin;
	}
}
