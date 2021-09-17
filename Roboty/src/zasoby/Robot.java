package zasoby;

public class Robot extends Thread {

	private int rozmiar;
	private String status; // czeka, dziala, laduje sie
	private int czas;
	protected StacjaLadowania sl;
	static protected boolean koniec;

	public Robot(String nazwa, StacjaLadowania sl) {
		super(nazwa);
		this.sl = sl;
	}

	public static boolean isKoniec() {
		return koniec;
	}

	public void setKoniec(boolean koniec) {
		Robot.koniec = koniec;
	}

	public int getRozmiar() {
		return rozmiar;
	}

	public void setRozmiar(int rozmiar) {
		this.rozmiar = rozmiar;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCzas() {
		return czas;
	}

	public void setCzas(int czas) {
		this.czas = czas;
	}

	public void run() {
		while (!koniec) {
			// jezdzi po hali
			try {
				sleep(czas + (int) (Math.random() * 100));
			} catch (InterruptedException e) {
				System.err.println("Przerwano watek");
			}

			// laduje sie
			sl.zajmij(this);

			try {
				sleep(czas * 3 / 4 + (int) (Math.random() * 10));
			} catch (InterruptedException e) {
				System.err.println("Przerwano watek");
			}

			// zwalnia stanowisko ladowania
			sl.zwolnij(this);
		}
	}

}
