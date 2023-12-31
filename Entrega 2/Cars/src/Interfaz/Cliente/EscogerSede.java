package Interfaz.Cliente;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Interfaz.Principal.MetodosAuxiliares;
import Interfaz.Principal.Principal;
import RentadoraModelo.Cliente;

public class EscogerSede extends JPanel implements MetodosAuxiliares, ActionListener {
	
	Principal principal;
	public  Cliente clienteActual;
	private GridBagConstraints gbc;
	private Catalogo catalogo;
	private MenuCliente menuCliente;
	private FormalizarAlquiler formalizarAlquiler;
	private HacerReserva hacerReserva;
	private EntregarVehiculo entregarVehiculo;
	private CambiarReserva cambiarReserva;
	private NuevosConductores nuevosConductores;
	private JButton logOut;
	
	static final int textFieldSize = 20;
	
	static final int YSpace = 10;
	
	public EscogerSede(Principal principal) {
		
		this.principal = principal;
		this.gbc = new GridBagConstraints();
		
		this.menuCliente = new MenuCliente(principal);
		this.formalizarAlquiler = new FormalizarAlquiler(principal);
		this.hacerReserva = new HacerReserva(principal);
		this.entregarVehiculo = new EntregarVehiculo(principal);
		this.cambiarReserva = new CambiarReserva(principal);
		this.nuevosConductores = new NuevosConductores(principal);
		
		principal.addPanel(menuCliente, "menuCliente" );
		principal.addPanel(formalizarAlquiler, "formalizarAlquiler" );
		principal.addPanel(hacerReserva, "hacerReserva" );
		principal.addPanel(entregarVehiculo, "entregarVehiculo" );
		principal.addPanel(cambiarReserva, "cambiarReserva" );
		principal.addPanel(nuevosConductores, "nuevosConductores" );
		
		setLayout(new GridBagLayout());
		this.gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		//setBackground(Principal.globalTheme);
		
		setSize(500, 500);
		
		setBorder(new EmptyBorder(40, 40, 40, 40)); //PARA PONER MARGENES
		
		addTitulo();
		addBotones();
		addCatalogos();
		
	}
	
	private void addTitulo() {
		JLabel instruccion = new JLabel("Escoge una Sede");
		titleText(instruccion);
		gbc.gridwidth = 2;
		add(instruccion,gbc);
		gbc.gridy++;
		gbc.gridwidth = 1;
		addSpace(YSpace);
		
	}

	private void addBotones() {
		
        gbc.anchor = GridBagConstraints.CENTER;
		
		
		for (String sede : principal.cargaArchivos.cargarSedes()) {
			
			JButton button = new JButton(sede);
			
			formatButton(button, pastelPurple);
			
			button.setActionCommand(sede);
			
			button.addActionListener(this);
			
			add(button, gbc);
			gbc.gridy++;
			addSpace(YSpace);
		}
		
		addSpace(YSpace*3);
		gbc.gridwidth = 2;
		logOut = new JButton("LOG OUT");
		formatGradientButton(logOut, pastelPurple, pastelPink);
		logOut.setActionCommand("login");
		logOut.addActionListener(this);
		gbc.anchor = GridBagConstraints.WEST;
		add(logOut, gbc);
		gbc.gridy++;

	}
	
	private void addCatalogos() {

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 2;
        gbc.gridy = 2;
        
		for (String sede : principal.cargaArchivos.cargarSedes()) {
					
			JButton button = new JButton("Catalogo");
			
			formatButton(button, pastelPink);
			button.setActionCommand(sede+"!");
			button.addActionListener(this);
			
			add(button, gbc);
			gbc.gridy++; 
			addSpace(YSpace);
		
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String grito = e.getActionCommand();
		
		if (grito.charAt(grito.length() - 1) == '!') {
			catalogo = new Catalogo(principal, grito.replace("!", ""));
			
		} else if (grito.equals("login")) {
			principal.cambiarPanel(grito); 
			
		} else {
			principal.setSedeActual(grito.replace("!", ""));
			principal.sedePresente = principal.cargaArchivos.cargarSedeIndiv(grito.replace("!", ""));
			principal.cambiarPanel("menuCliente");
			
		} 
	}
	private void addSpace(int Yspace) {
		add(Box.createRigidArea(new Dimension(0, Yspace)), gbc);
		gbc.gridy++;
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        // Define the start and end points for the gradient
        Point2D start = new Point2D.Float(0, 0);
        Point2D end = new Point2D.Float(0, height);

        // Define the colors for the gradient
        Color color1 = cutePurple;
        Color color2 = Color.PINK;

        // Create a gradient paint
        GradientPaint gradientPaint = new GradientPaint(start, color1, end, color2);

        // Set the paint for the graphics context
        g2d.setPaint(gradientPaint);

        // Fill the background with the gradient paint
        g2d.fillRect(0, 0, width, height);
    }
	
}
