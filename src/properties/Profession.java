package properties;

// Proveffion of a humanoid. Warrior, mage, thief, etc
public class Profession {
	public String title;

	public Profession(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
}
