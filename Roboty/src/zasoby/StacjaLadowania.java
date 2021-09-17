package zasoby;

import java.util.ArrayList;

public class StacjaLadowania {

	private static ArrayList<Stanowisko> stanowiska;
	private int obok = 0, p = 0, h = 0;

	// liczy ile jest najwiecej miejsc wolnych obok siebie
	private int ileObok() {
		int ile = 0, x = 0;

		for (int i = 1; i < stanowiska.size() - 1; i++) {
			if (stanowiska.get(i).isWolne()) {
				x++;
				if (ile < x) {
					ile = x;
				}
			}

			else {
				x = 0;
			}
		}
		return ile;
	}

	// wypisuje stan stanowisk i robotow
	public static void wypisz() {
		int poczatek = 0, koniec = 0;

		System.out.println("\n\nRoboty");
		System.out.println("\nNazwa Rozmiar Status Czas\n");
		for (int i = 0; i < Ramka.getRoboty().size(); i++) {
			System.out.println(Ramka.getRoboty().get(i).getName() + "        " + Ramka.getRoboty().get(i).getRozmiar()
					+ "    " + Ramka.getRoboty().get(i).getStatus() + "            "
					+ Ramka.getRoboty().get(i).getCzas());
		}

		System.out.println("\nStanowiska\n");

		if (!stanowiska.get(0).isWolne())
			poczatek = stanowiska.get(0).getRobot().getRozmiar() - 1;

		if (!stanowiska.get(stanowiska.size() - 1).isWolne())
			koniec = stanowiska.get(stanowiska.size() - 1).getRobot().getRozmiar() - 1;

		for (int i = 0; i < stanowiska.size() - 1 - poczatek; i++) {
			System.out.println("-");
		}

		for (int i = 0; i < poczatek; i++) {
			System.out.println(stanowiska.get(0).getRobot().getName());
		}

		for (int i = 0; i < stanowiska.size(); i++) {
			if (stanowiska.get(i).isWolne())
				System.out.println(stanowiska.get(i).getNumer() + ": -");
			else
				System.out.println(stanowiska.get(i).getNumer() + ": " + stanowiska.get(i).getRobot().getName());
		}

		for (int i = 0; i < koniec; i++) {
			System.out.println(stanowiska.get(stanowiska.size() - 1).getRobot().getName());
		}

		for (int i = 0; i < stanowiska.size() - 1 - koniec; i++) {
			System.out.println("-");
		}
	}

	// konstruktor
	public StacjaLadowania(int ile) {
		stanowiska = new ArrayList<Stanowisko>(ile);
		if (ile <= 0)
			throw new IllegalArgumentException("Za mala liczba stanowisk");

		for (int i = 0; i < ile; i++) // tworzy stanowiska i dodaje je do stacji ladowania
		{
			Stanowisko stacja = new Stanowisko(i + 1, true);
			stanowiska.add(stacja);
		}

	}

	// ZAJMOWANIE
	public synchronized void zajmij(Robot r) {

		obok = ileObok();

		if (stanowiska.size() == 2) {
			while (!stanowiska.get(0).isWolne() && !stanowiska.get(1).isWolne())
				try {
					r.setStatus("Czeka");
					wait();
					wypisz();
				} catch (InterruptedException e) {
					System.err.println("Przerwano watek");
				}

			if (stanowiska.get(0).isWolne()) {
				stanowiska.get(0).setRobot(r);
				stanowiska.get(0).setWolne(false);
				r.setStatus("Laduje sie");
			}

			else {
				stanowiska.get(1).setRobot(r);
				stanowiska.get(1).setWolne(false);
				r.setStatus("Laduje sie");
			}

			wypisz();
		}

		else if (stanowiska.size() == 1) {
			while (!stanowiska.get(0).isWolne())
				try {
					r.setStatus("Czeka");
					wait();
					wypisz();
				} catch (InterruptedException e) {
					System.err.println("Przerwano watek");
				}

			if (stanowiska.get(0).isWolne()) {
				stanowiska.get(0).setRobot(r);
				stanowiska.get(0).setWolne(false);
				r.setStatus("Laduje sie");
				wypisz();
			}
		}

		else {
			if (r.getRozmiar() >= stanowiska.size() / 2 + 1) {
				while (!stanowiska.get(0).isWolne() && !stanowiska.get(stanowiska.size() - 1).isWolne())
					try {
						r.setStatus("Czeka");
						wait();
						wypisz();
					} catch (InterruptedException e) {
						System.err.println("Przerwano watek");
					}

				if (stanowiska.get(0).isWolne()) {
					stanowiska.get(0).setRobot(r);
					stanowiska.get(0).setWolne(false);
					r.setStatus("Laduje sie");
				}

				else {
					stanowiska.get(stanowiska.size() - 1).setRobot(r);
					stanowiska.get(stanowiska.size() - 1).setWolne(false);
					r.setStatus("Laduje sie");
				}

				wypisz();

			}

			else if (r.getRozmiar() == 1) {
				while (obok == 0)
					try {
						r.setStatus("Czeka");
						wait();
						wypisz();
					} catch (InterruptedException e) {
						System.err.println("Przerwano watek");
					}

				if (obok > 0) {
					for (int i = 1; i < stanowiska.size() - 1; i++) {
						if (stanowiska.get(i).isWolne()) {
							stanowiska.get(i).setRobot(r);
							stanowiska.get(i).setWolne(false);
							r.setStatus("Laduje sie");
							obok = ileObok();
							wypisz();
							break;
						}
					}
				}
			}

			else {
				while (obok < r.getRozmiar())
					try {
						r.setStatus("Czeka");
						wait();
						wypisz();
					} catch (InterruptedException e) {
						System.err.println("Przerwano watek");
					}

				if (obok >= r.getRozmiar()) {

					p = 0; // zmienna pomocnicza
					h = 0; // indeks miejsca

					for (int i = 1; i < stanowiska.size() - 1; i++) {
						if (stanowiska.get(i).isWolne()) {
							p++;
							if (p == r.getRozmiar()) {
								h = i;
								break;
							}
						}

						else
							p = 0;
					}

					p = 0;

					while (p < r.getRozmiar()) {
						stanowiska.get(h - p).setRobot(r);
						stanowiska.get(h - p).setWolne(false);
						p++;
					}

					p = 0;
					h = 0;

					r.setStatus("Laduje sie");
					wypisz();
					obok = ileObok();
				}
			}
		}

	}

	// ZWALNIANIE
	public synchronized void zwolnij(Robot r) {

		if (stanowiska.size() == 1) {
			stanowiska.get(0).setRobot(null);
			stanowiska.get(0).setWolne(true);
			r.setStatus("Dziala");
			notify();
		}

		else if (stanowiska.size() == 2) {
			if (stanowiska.get(0).getRobot() == r) {
				stanowiska.get(0).setRobot(null);
				stanowiska.get(0).setWolne(true);
				r.setStatus("Dziala");
			}

			else {
				stanowiska.get(1).setRobot(null);
				stanowiska.get(1).setWolne(true);
				r.setStatus("Dziala");
			}

			notify();
		}

		else {

			obok = ileObok();

			if (r.getRozmiar() >= stanowiska.size() / 2 + 1) {

				if (stanowiska.get(0).getRobot() == r) {
					stanowiska.get(0).setRobot(null);
					stanowiska.get(0).setWolne(true);
					r.setStatus("Dziala");
				}

				else {
					stanowiska.get(stanowiska.size() - 1).setRobot(null);
					stanowiska.get(stanowiska.size() - 1).setWolne(true);
					r.setStatus("Dziala");
				}

				notify();
			}

			else if (r.getRozmiar() == 1) {
				for (int i = 1; i < stanowiska.size() - 1; i++) {
					if (stanowiska.get(i).getRobot() == r) {
						stanowiska.get(i).setWolne(true);
						stanowiska.get(i).setRobot(null);
						r.setStatus("Dziala");
						break;
					}
				}

				if (obok == 0) {
					obok = 1;
					notify();
				}
			}

			else {

				p = r.getRozmiar() - 1;

				for (int i = 1; i < stanowiska.size() - 1; i++) {
					if (stanowiska.get(i).getRobot() == r) {
						do {
							stanowiska.get(i + p).setWolne(true);
							stanowiska.get(i + p).setRobot(null);
							p--;
						} while (p >= 0);
						r.setStatus("Dziala");
						break;
					}
				}

				if (r.getRozmiar() > obok) {
					obok = r.getRozmiar();
					notify();
				}
				p = 0;
			}
		}
		wypisz();
	}

}
