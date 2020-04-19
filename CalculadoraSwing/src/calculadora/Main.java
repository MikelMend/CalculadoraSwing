package calculadora;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// VentanaCalculadora = new VentanaCalculadora();
		VentanaCalculadora calculadora = new VentanaCalculadora();
		calculadora.setLocation(100, 100);
		calculadora.setVisible(true);

	}

}

