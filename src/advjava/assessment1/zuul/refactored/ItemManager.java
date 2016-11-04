package advjava.assessment1.zuul.refactored;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
	
	private final List<Item> items;
	
	public ItemManager(){
		items = new ArrayList<>();
	}
	
	public boolean hasItem(String name){
		return items.stream()
				.findAny()
				.isPresent();
	}
	
	public Item getItem(String name){
		return items.stream()
				.filter(i->i.getName().equals(name))
				.findFirst()
				.orElse(null);
	}
	
	public boolean addItem(Item item){
		if(items.contains(item))
			return false;
		else
			items.add(item);
		return true;
	}
	
	public void clearItems(){
		items.clear();
	}
	
	public boolean removeRoom(String name){
		if(hasItem(name)){
			items.removeIf(i->i.getName().equals(name));
			return true;
		}else
			return false;
			
	}

}
