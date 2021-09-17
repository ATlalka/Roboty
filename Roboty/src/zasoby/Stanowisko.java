package zasoby;

public class Stanowisko {
	private int numer;
	private boolean wolne = true;
	private Robot robot;

	public Robot getRobot() {
		return robot;
	}

	public void setRobot(Robot robot) {
		this.robot = robot;
	}

	public Stanowisko(int nr, boolean stan) {
		this.numer = nr;
		this.wolne = stan;
	}

	public int getNumer() {
		return numer;
	}

	public void setNumer(int numer) {
		this.numer = numer;
	}

	public boolean isWolne() {
		return wolne;
	}

	public void setWolne(boolean wolne) {
		this.wolne = wolne;
	}

}
