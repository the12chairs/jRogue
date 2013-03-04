package primitives;

import java.io.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import lowlevel.FileReader;

public class Quest {
	
	private String title;
	private String description;
	private long exp;
	private long gold;
	private String autorName;
	// Не уверен насчет этих атрибутов
	private boolean isSuccess;
	private boolean isFailed;
	
	
	public static void main(String args[]) throws FileNotFoundException{

	}
	
	public Quest(String questFile){
		
		// Прочитаем все значения из файла-описания квеста
		String fileContent = null;
		
		try {
			fileContent = FileReader.readFile(questFile);
			
		} catch (IOException e) { // Хьюстон, у нас проблема
			e.printStackTrace();
		}
		
		// Парсим засранца
		JSONParser parser = new JSONParser();
		
		Object obj = null;
		try {
			obj = parser.parse(fileContent); // В obj лежит распарсенный квест
		} catch (ParseException e) {
			// Нираспарсилось((((999
			e.printStackTrace();
		}
		// Выдернем из него все значения
		JSONObject jsonObj = (JSONObject) obj;
		
		
		this.title = (String) jsonObj.get("title");
		this.description = (String) jsonObj.get("description");
		this.exp = (long) jsonObj.get("exp");
		this.gold = (long) jsonObj.get("gold");
		this.autorName = (String) jsonObj.get("autor");
		
		// Не невыполненный, не выполненный одновременно. Короче говоря, активный
		this.isFailed = false;
		this.isSuccess = false;
	}
	
	// Некоторый необходимый минимум получения и установки статуса квеста
	public boolean isSuccess(){
		return this.isSuccess;
	}
	
	public boolean isFailed(){
		return this.isFailed;
	}
	
	public void success(){
		this.isSuccess = true;
		this.isFailed = false;
	}
	
	
	public void fail(){
		this.isSuccess = false;
		this.isFailed = true;
	}
	
	public boolean getStatus(){
		if(this.isSuccess()){
			return true;
		}
		else{
			return false;
		}
	}
	// Геттеры-сеттеры, вся шаверма
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public long getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public String getAutorName() {
		return autorName;
	}

	public void setAutorName(String autorName) {
		this.autorName = autorName;
	}
	
}


