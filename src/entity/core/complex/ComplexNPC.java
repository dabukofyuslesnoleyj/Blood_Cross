package entity.core.complex;

import entity.core.complex.loot.Lootable;
import game_state.core.PlayState;
import tile_map.TileMap;

public abstract class ComplexNPC extends ComplexUnit implements Lootable{

	public ComplexNPC(TileMap tm, double endurance, double strength,
			double dexterity, double intelligence, double vitality,
			double agility, PlayState state) {
		super(tm, endurance, strength, dexterity, intelligence, vitality, agility, state);
		// TODO Auto-generated constructor stub
	}
	
	public abstract void NPC_Pattern();
	
	@Override
	public void update(){
		super.update();
		this.NPC_Pattern();
		this.animation.update();
	}

}
