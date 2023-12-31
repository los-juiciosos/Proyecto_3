package Interfaz.Empleados;

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
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Interfaz.Principal.ErrorDisplay;
import Interfaz.Principal.MetodosAuxiliares;
import Interfaz.Principal.Notificacion;
import Interfaz.Principal.Principal;
import Interfaz.Principal.Verify;
import RentadoraModelo.EmpleadoMostrador;

public class ReservaEspecial extends JPanel implements MetodosAuxiliares, ActionListener {
	
	Principal principal;
	
	private GridBagConstraints gbc;
	
	private JButton confirmar;
	
	private JButton volver;
	
	static final int textFieldSize = 20;
	
	static final int YSpace = 5;
	private ErrorDisplay error;
	private Notificacion notify;
	private Verify verificador;
	private ArrayList<JTextField> listaCampos;
	private ArrayList<String> campos;
	private JComboBox<String> sedes;
	
	
	public ReservaEspecial(Principal principal) {
		
		this.verificador = new Verify();
		this.principal = principal;
		this.campos = new ArrayList<String>();
		this.listaCampos = new ArrayList<JTextField>();
		
		setLayout(new GridBagLayout());
		
		this.gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel instruccion = new JLabel("Rellene los siguientes campos");
		subTitleText(instruccion);
		
		confirmar = new JButton("Confirmar");
		confirmar.setActionCommand("empleadoMostrador");
		confirmar.addActionListener(this);
		formatGradientButton(confirmar, cuteYellow, Color.PINK); 
		
		volver = new JButton("VOLVER");
		volver.setActionCommand("VOLVER");
		volver.addActionListener(this);
		formatGradientButton(volver, Color.PINK, cutePurple);        
		
		add(instruccion,gbc);
		
        addCampos();
        
        addSpace(YSpace);
        JLabel labelSede = new JLabel("Sede de entrega: ");
        subTitleText(labelSede);
        
        ArrayList<String> todasSedes = principal.cargaArchivos.cargarSedes();
		sedes = new JComboBox<>();
		for (String sede: todasSedes) {			
        sedes.addItem(sede);
		}
		
		addSpace(YSpace);
		add(labelSede, gbc);
		add(sedes,gbc);
        
		addSpace(YSpace*4);
        add(confirmar,gbc);
        addSpace(YSpace*2);
        add(volver,gbc);
	}

	private void addCampos() {
		  
        campos.add("Día recogida");
        campos.add("Hora recogida (hh:mm)");
        campos.add("Día entrega (dd/mm/YYYY)");
        campos.add("Hora Entrega");
        campos.add("Placa");
		
		for (String mensaje : campos) {
        	JTextField campo = new JTextField(textFieldSize);
            ponerTextitoGris(campo, mensaje);
            addSpace(YSpace);
            listaCampos.add(campo);
            add(campo,gbc);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String grito = e.getActionCommand();
		
		ArrayList<String> info = new ArrayList<String>();
		for (JTextField campito: listaCampos) {
			if (campos.contains(campito.getText()) == true) {
				info.add("");
			}
			else {
				info.add(campito.getText());
			}
		}
		
		boolean verifiLleno = verificador.verifyLleno(info);
		
		if (grito.equals("VOLVER")) {
			principal.cambiarPanel("empleadoMostrador");
		}
		
		else if (verifiLleno == false) {
			error = new ErrorDisplay("PORFAVOR LLENE TODOS LOS CAMPOS");
		}
		else {
			EmpleadoMostrador empleado = (EmpleadoMostrador) principal.usuarioActual;
			Properties pVehiculo = principal.cargaArchivos.cargarVehiculos();
			boolean verifySede = verificador.verifyCarroInSede(pVehiculo, info.get(4), empleado.getSede());
			if (verifySede == true) {
			String estado = empleado.iniciarReservaEspecial(info.get(0), info.get(1), info.get(2), 
					info.get(3), (String) sedes.getSelectedItem(), info.get(4));
			
			if (estado == null) {
				error = new ErrorDisplay("CARRO NO DENTRO DE MI JURISDICCION");
			}
			else {
				notify = new Notificacion("RESERVA ESPECIAL REALIZADA CON EXITO");
			}
			}
			else {
				error = new ErrorDisplay("CARRO NO DENTRO DE MI JURISDICCION");
			}
		}
		principal.cambiarPanel(grito);
		
	}
	
	private void addSpace(int Yspace) {
		add(Box.createRigidArea(new Dimension(0, Yspace)), gbc);
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
//        Color color1 = new Color(255, 165, 0);
        Color color1 = cuteYellow;
        Color color2 = Color.PINK;

        // Create a gradient paint
        GradientPaint gradientPaint = new GradientPaint(start, color1, end, color2);

        // Set the paint for the graphics context
        g2d.setPaint(gradientPaint);

        // Fill the background with the gradient paint
        g2d.fillRect(0, 0, width, height);
    }
}
