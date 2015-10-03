package xivvic.console.action;

public class ActionMetadata
{
	private final String name;
	private final String desc;
	private final String usage;
	private final String service;
	
	public ActionMetadata(String name, String desc, String usage)
	{
		this.name  = name;
		this.desc  = desc;
		this.usage = usage;
		this.service = null;
	}

	public ActionMetadata(String name, String desc, String usage, String service)
	{
		this.name  = name;
		this.desc  = desc;
		this.usage = usage;
		this.service = service;
	}

	public String service()
	{
		return service;
	}

	public String name()
	{
		return name;
	}

	public String desc()
	{
		return desc;
	}

	public String usage()
	{
		return usage;
	}

}
