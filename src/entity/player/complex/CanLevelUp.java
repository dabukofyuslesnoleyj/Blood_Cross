package entity.player.complex;

public interface CanLevelUp {

	public void updateExperience();
	public void increaseExperience(double addedExperience);
	public void decreaseExperience(double removedExperience);
	public void levelUp();
	public void levelDown();
	public double getExperience();
	public int getLevel();
	public void setLevelLimit(int levelLimit);
	public void setExpThresholdPerLevel(double threshold);
	
}
