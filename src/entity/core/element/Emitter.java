package entity.core.element;

import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Emitter {
	
	protected ArrayList<MapElement> elements;
	
	protected Emitter() {
		this.elements = new ArrayList<MapElement>();
	}
	
	public void emit(MapElement element) {
		this.elements.add(element);
	}
	
	public void emit(ArrayList<? extends MapElement> elements) {
		this.elements.addAll(elements);
	}
	
	public void update() {
		for (int i = 0; i < activeElementCount(); i++) {
			if(elements.get(i).shouldRemove())
				elements.remove(i);
			else
				elements.get(i).update();
		}
	}
	
	public void draw(Graphics2D g) {
		for (int i = 0; i < activeElementCount(); i++) {
			if(elements.get(i).shouldRemove())
				elements.remove(i);
			else
				elements.get(i).draw(g);
		}
	}
	
	public int activeElementCount() {
		return elements.size();
	}

}
