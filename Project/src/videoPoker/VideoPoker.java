package videoPoker;

public class VideoPoker {

	int[][] rewardTable;
	
	public VideoPoker(int[][] rewardTable){
		this.rewardTable = rewardTable;
	}
	
	public int reward(int score, int bet){
		return rewardTable[score][bet];
	}
}
