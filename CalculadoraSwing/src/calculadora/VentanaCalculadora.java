package calculadora;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class VentanaCalculadora extends JFrame implements KeyListener{
	
	/* contenedor general y paneles donde colocaremos los botones */
	JPanel panel, panelNumeros, panelOperaciones;
	/* display de introducción datos y resultados */
	JTextField pantalla;
	
	/* guarda el resultado de la operacion anterior o el número tecleado */
	double resultado;
	/* para guardar el operador introducido (+,-,*,/) a realizar */
	String operacion;
	/* Indica si estamos iniciando o no una operación */
	boolean nuevaOperacion = true;

	/*
	 * Constructor. Crea los botones y componentes de la calculadora
	 */
	public VentanaCalculadora()
 {
 super();//crea JFrame como contenedor
 	setSize(350, 400);//dimensiones de la ventana contenedora
 // cambio el icono de la ventana
 	Image iconoPropio = Toolkit.getDefaultToolkit().getImage(getClass().getResource("icono.png"));
 	setIconImage(iconoPropio);
 	setTitle("Calculadora");
 	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
 
 //setResizable(false);//para que no se pueda modificar las dimensiones de ventana

 // Vamos a dibujar sobre el panel
 	panel = new JPanel();
 //this.getContentPane().add(panel);//añadimos panel a la ventana principal
 	this.add(panel);
 	panel.setLayout(new BorderLayout());

 	pantalla = new JTextField("0"); 
 	pantalla.setBorder(new EmptyBorder(5, 5, 5, 5));//zona donde no escribe (paddingde CSS)
 	pantalla.setFont(new Font("Arial", Font.BOLD, 25));
 // pantalla.setHorizontalAlignment(JTextField.RIGHT);
 	pantalla.setHorizontalAlignment(SwingConstants.RIGHT);//left top right center bottom
 	pantalla.setEditable(false);//el usuario no puede cambiar el texto
 	pantalla.setBackground(Color.WHITE);//fondo del editor
 	pantalla.setBorder(BorderFactory.createLineBorder(Color.BLACK, WIDTH));

 	panel.add("North", pantalla);//lo colocamos arriba del panel

 	panelNumeros = new JPanel();
 //4 filas 3 columas de botones, espacio entre filas y columnas
 	panelNumeros.setLayout(new GridLayout(4, 3,5,5));
 	panelNumeros.setBorder(new EmptyBorder(5, 5, 5, 5));//espacio libre del panel no entre botones

 	for (int n = 9; n >= 0; n--) //creamos botones de numeros 9 a 0
 	{
 // nuevoBotonNumerico("" + n);//pasamos el entero como string
 // nuevoBotonNumerico(String.valueOf(n));
 		nuevoBotonNumerico(Integer.toString(n));
 }

 	nuevoBotonNumerico("."); //creamos botón .
//las dimensiones de los botones internos las ajusta a lo que queda(prioridad east)
 	panel.add("Center", panelNumeros);//lo colocamos centrado. No esta west

 	panelOperaciones = new JPanel();
 	panelOperaciones.setLayout(new GridLayout(6, 1,5,5));
 	panelOperaciones.setBorder(new EmptyBorder(5, 5, 5, 5));

 	nuevoBotonOperacion("+");
 	nuevoBotonOperacion("-");
 	nuevoBotonOperacion("*");
 	nuevoBotonOperacion("/");
 	nuevoBotonOperacion("=");
 	nuevoBotonOperacion("CE");
 //las dimensiones de los botones las ajusta a la letra y los coloca a la derecha
 	panel.add("East", panelOperaciones);
//	Implementar los métodos del interface KeyListener
 	pantalla.addKeyListener(this);
 	pantalla.requestFocus();
 }
	

	/*
	 * Crea un boton del teclado numérico y enlaza sus eventos con el listener
	 * correspondiente
	 */
	private void nuevoBotonNumerico(String digito) {
		JButton btn = new JButton();
		btn.setForeground(Color.BLUE);// color azul para dígitos
		btn.setText(digito);
		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e)// se suelta el raton
			{// btn lo tengo que crear cada vez al implementar la escucha
				JButton btn = (JButton) e.getSource();
				
				numeroPulsado(btn.getText());
				pantalla.requestFocus();
			}
		});
		panelNumeros.add(btn);// los va metiendo en el panel correspondiente
	}

	/*
	 * Crea un botón de operacion y lo enlaza con sus eventos.
	 */
	private void nuevoBotonOperacion(String operacion) {
		JButton btn = new JButton(operacion);
		btn.setForeground(Color.RED);// color rojo para operadores
		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				JButton btn = (JButton) e.getSource();
				operacionPulsado(btn.getText());
				pantalla.requestFocus();
			}
		});
		panelOperaciones.add(btn);
	}

	/*
	 * Gestiona las pulsaciones de teclas numéricas
	 *
	 * digito es tecla pulsada
	 */
	private void numeroPulsado(String digito) {
		if (pantalla.getText().equals("0") || nuevaOperacion) {
			pantalla.setText(digito);
		} else {
			pantalla.setText(pantalla.getText() + digito);
		}
		nuevaOperacion = false;
	}

	/*
	 * Gestiona las pulsaciones de teclas de operación
	 *
	 */
	private void operacionPulsado(String tecla) {
		if (tecla.equals("=")) {
			calcularResultado();
		} else if (tecla.equals("CE")) {
			resultado = 0;
			pantalla.setText("0");
			nuevaOperacion = true;
		} else {
			operacion = tecla;
			if ((resultado != 0) && !nuevaOperacion) {
				calcularResultado();
			} else {
				resultado = new Double(pantalla.getText());
			}
		}
		nuevaOperacion = true;
	}

	/*
	 * Calcula el resultado y lo muestra por pantalla
	 */
	private void calcularResultado() {
		if (operacion.equals("+")) {
			resultado += new Double(pantalla.getText());
		} else if (operacion.equals("-")) {
			resultado -= new Double(pantalla.getText());
		} else if (operacion.equals("/")) {
			resultado /= new Double(pantalla.getText());
		} else if (operacion.equals("*")) {
			resultado *= new Double(pantalla.getText());
		}

		pantalla.setText("" + resultado);
		operacion = "";
	}
	
	
	
					//	TECLADO
	
	//Asociremos como componente escuchador del teclado al panel
		
//		Implementar los métodos del interface KeyListener
		public void keyTyped(KeyEvent e) {
			
	 }

		public void keyPressed(KeyEvent evt) {
			
	 String numeroteclado="0";
	 pantalla.setFocusable(true);
	 String numero = KeyEvent.getKeyText(evt.getKeyCode());
	 System.out.println(numero);
	 numeroteclado=numero.substring(0, 1);
	 
	 switch (evt.getKeyCode()) {
	 
	 case KeyEvent.VK_ENTER:
		 operacionPulsado("=");
	 break;
	 
	 case KeyEvent.VK_0:
		 pantalla.setFocusable(true);
	 	numeroPulsado(numeroteclado);
	 break;
	 
	 case KeyEvent.VK_1:
		 pantalla.setFocusable(true);
	 	numeroPulsado(numeroteclado);
	 break;
	 
	 case KeyEvent.VK_2:
		 pantalla.setFocusable(true);
	 	numeroPulsado(numeroteclado);
	 break;
	 
	 case KeyEvent.VK_3:
		 pantalla.setFocusable(true);
	 	numeroPulsado(numeroteclado);
	 break;
	 
	 case KeyEvent.VK_4:
		 pantalla.setFocusable(true);
	 	numeroPulsado(numeroteclado);
	 break;
	 
	 case KeyEvent.VK_5:
		 pantalla.setFocusable(true);
	 	numeroPulsado(numeroteclado);
	 break; 
	 
	 case KeyEvent.VK_6:
		 pantalla.setFocusable(true);
	 	numeroPulsado(numeroteclado);
	 break;
	 
	 case KeyEvent.VK_7:
		 pantalla.setFocusable(true);
	 	numeroPulsado(numeroteclado);
	 break;
	 
	 case KeyEvent.VK_8:
		 pantalla.setFocusable(true);
	 	numeroPulsado(numeroteclado);
	 break;
	 
	 case KeyEvent.VK_9:
		 pantalla.setFocusable(true);
	 	numeroPulsado(numeroteclado);
	 break;
	 
	 case KeyEvent.VK_NUMPAD0:
		 pantalla.setFocusable(true);
	 	numeroPulsado("0");
	 break;
	 
	 case KeyEvent.VK_NUMPAD1:
		 pantalla.setFocusable(true);
	 	numeroPulsado("1");
	 break;
	 
	 case KeyEvent.VK_NUMPAD2:
		 pantalla.setFocusable(true);
	 	numeroPulsado("2");
	 break;
	 
	 case KeyEvent.VK_NUMPAD3:
		 pantalla.setFocusable(true);
	 	numeroPulsado("3");
	 break;
	 
	 case KeyEvent.VK_NUMPAD4:
		 pantalla.setFocusable(true);
	 	numeroPulsado("4");
	 break;
	 
	 case KeyEvent.VK_NUMPAD5:
		 pantalla.setFocusable(true);
	 	numeroPulsado("5");
	 break;
	 
	 case KeyEvent.VK_NUMPAD6:
		 pantalla.setFocusable(true);
	 	numeroPulsado("6");
	 break;
	 
	 case KeyEvent.VK_NUMPAD7:
		 pantalla.setFocusable(true);
	 	numeroPulsado("7");
	 break;
	 
	 case KeyEvent.VK_NUMPAD8:
		 pantalla.setFocusable(true); 
	 	numeroPulsado("8");
	 break;
	 
	 case KeyEvent.VK_NUMPAD9:
		 pantalla.setFocusable(true);
	 	numeroPulsado("9");
	 break;
	 
	 case KeyEvent.VK_EQUALS:
		 pantalla.setFocusable(true);
	 	operacionPulsado(numeroteclado);
	 break;
	 
	 case KeyEvent.VK_BACK_SPACE:
		 pantalla.setText("0");
		 resultado=0;
	 break;
	 	default:
	 break;
	 }
	 if (numeroteclado.equalsIgnoreCase("+")){
	 pantalla.setFocusable(true);
	 operacionPulsado(numeroteclado);
	 	}else if (numeroteclado.equalsIgnoreCase("-")){
	 		pantalla.setFocusable(true);
	 		operacionPulsado(numeroteclado);
	 	}else if (numeroteclado.equalsIgnoreCase("*")){
	 		pantalla.setFocusable(true);
	 		operacionPulsado(numeroteclado);
	 	}else if (numeroteclado.equalsIgnoreCase("/")){
	 		pantalla.setFocusable(true);
	 		operacionPulsado(numeroteclado);
	 	}
	 	pantalla.requestFocus();
	 }

		public void keyReleased(KeyEvent e) {
			
	 }
		
}


