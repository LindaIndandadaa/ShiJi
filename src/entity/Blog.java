package entity;

public class Blog {
	private String id;
	private String name;
	private String time;
	private String person;
	private String content;
	
	public void setBlogid(String id)
	{
		this.id=id;
	}
	public void setBlogname(String name)
	{
		this.name=name;
	}
	public void setBlogtime(String time)
	{
		this.time=time;
	}
	public void setBlogperson(String person)
	{
		this.person=person;
	}
	public void setBlogcontent(String content)
	{
		this.content=content;
	}
	
	public String getBlogid()
	{
		return id;
	}
	public String getBlogname()
	{
		return name;
	}
	public String getBlogtime()
	{
		return time;
	}
	public String getBlogperson()
	{
		return person;
	}
	public String getBlogcontent()
	{
		return content;
	}
	
	public void printBlog()
	{
		System.out.println(name+" "+time+" "+person+" "+content);
	}

}
