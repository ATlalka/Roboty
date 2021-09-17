package zasoby;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class Ramka extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtRoboty;
	private JTextField txtStanowiska;
	private StacjaLadowania stacja;
	private static ArrayList<Robot> roboty;
	private char nazwa = 65;
	Random los = new Random();
	private JTextField txtPodajLiczbeRobotw;
	private JTextField txtPodajLiczbeStanowisk;

	public static ArrayList<Robot> getRoboty() {
		return roboty;
	}

	public void utworz(int r, int s) {
		stacja = new StacjaLadowania(s);
		roboty = new ArrayList<Robot>(r);

		if (r <= 0)
			throw new IllegalArgumentException("Zla liczba robotow");
		if (r > 26)
			throw new IllegalArgumentException("Za duzo robotow!");

		for (int i = 0; i < r; i++) // tworzy roboty
		{
			Robot robot = new Robot(Character.toString(nazwa), stacja);
			robot.setCzas((los.nextInt(10) + 1) * 1000);
			robot.setRozmiar(los.nextInt(s) + 1);
			robot.setStatus("Dziala");
			nazwa++;
			roboty.add(robot);
		}
	}

	public void uruchomRoboty() {
		for (Robot r : roboty) {
			r.setKoniec(false);
			r.start();
		}
		StacjaLadowania.wypisz();
	}

	public void zatrzymajRoboty() {
		for (Robot r : roboty) {
			r.setKoniec(true);
		}
	}

	/**
	 * Create the frame.
	 */
	public Ramka() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 478, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtRoboty = new JTextField();
		txtRoboty.setBounds(26, 94, 127, 19);
		contentPane.add(txtRoboty);
		txtRoboty.setColumns(10);

		txtStanowiska = new JTextField();
		txtStanowiska.setBounds(180, 94, 127, 19);
		contentPane.add(txtStanowiska);
		txtStanowiska.setColumns(10);

		JButton btnNewButton = new JButton("START");
		btnNewButton.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				utworz(Integer.parseInt(txtRoboty.getText()), Integer.parseInt(txtStanowiska.getText()));
				uruchomRoboty();
				txtStanowiska.setEditable(false);
				txtRoboty.setEditable(false);
			}
		});
		btnNewButton.setBounds(26, 149, 127, 34);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("STOP");
		btnNewButton_1.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				zatrzymajRoboty();
				System.out.println("\n\nZatrzymano roboty. Roboty dokrecaja swoje petle run.\n\n");
			}
		});
		btnNewButton_1.setBounds(183, 149, 124, 34);
		contentPane.add(btnNewButton_1);

		txtPodajLiczbeRobotw = new JTextField();
		txtPodajLiczbeRobotw.setEditable(false);
		txtPodajLiczbeRobotw.setText("Podaj liczbe robotow");
		txtPodajLiczbeRobotw.setBounds(26, 51, 127, 19);
		contentPane.add(txtPodajLiczbeRobotw);
		txtPodajLiczbeRobotw.setColumns(10);

		txtPodajLiczbeStanowisk = new JTextField();
		txtPodajLiczbeStanowisk.setEditable(false);
		txtPodajLiczbeStanowisk.setText("Podaj liczbe stanowisk");
		txtPodajLiczbeStanowisk.setBounds(183, 51, 124, 19);
		contentPane.add(txtPodajLiczbeStanowisk);
		txtPodajLiczbeStanowisk.setColumns(10);
	}
}
