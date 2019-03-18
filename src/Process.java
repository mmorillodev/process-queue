
public class Process {
	private String name;
	private int priority;
	
	public Process(String name, int priority){
		this.name = name;
		this.priority = priority;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
