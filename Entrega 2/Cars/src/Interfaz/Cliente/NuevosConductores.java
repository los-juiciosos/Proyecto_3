package Interfaz.Cliente;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Interfaz.Principal.ErrorDisplay;
import Interfaz.Principal.MetodosAuxiliares;
import Interfaz.Principal.Principal;
import Interfaz.Principal.Verify;
import RentadoraModelo.OtrosConductores;

public class NuevosConductores extends JPanel implements MetodosAuxiliares, ActionListener {
	
	Principal principal;
	
	private JButton fotoDocumento;
	private GridBagConstraints gbc;
	private JButton confirmar;
	static final int textFieldSize = 20;
	static final int YSpace = 5;
	private ArrayList<JTextField> listaCampos = new ArrayList<JTextField>();
	private Verify verificador = new Verify();
	private JTextField foto;
	private ErrorDisplay error;
	
	public NuevosConductores(Principal principal) {
		
		this.principal = principal;
		
		setLayout(new GridBagLayout());
		
		this.gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.WEST;
		
		addCampos();
		
		fotoDocumento = new JButton("Foto Documento");
		fotoDocumento.setActionCommand("FOTO");
		fotoDocumento.addActionListener(this);
		formatButton(fotoDocumento);
		add(fotoDocumento,gbc);
		
		foto = new JTextField(textFieldSize);
        addSpace(YSpace);
        foto.setEnabled(false);
        add(foto,gbc);
		
		confirmar = new JButton("Confirmar");
		formatButton(confirmar);
		confirmar.setActionCommand("formalizarAlquiler");
		confirmar.addActionListener(this);
		add(confirmar,gbc);
	}
	
	private void addCampos() {
		
	    String[] campos = {"Nombre", "Número de licencia", "País de licencia",
	    		"Fecha de Vencimiento"};
	    
	    for (String mensaje : campos) {
	    	JTextField campo = new JTextField(textFieldSize);
	        ponerTextitoGris(campo, mensaje);
	        addSpace(YSpace);
	        add(campo,gbc);
	        listaCampos.add(campo);
		}	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String grito = e.getActionCommand();
		
		if (grito.equals("FOTO")) {
			String fotoName = verificador.chooseFile();
			if (fotoName == null) {
				fotoName = "ERROR";
			}
			foto.setText(fotoName);
		}
		else {
			
			ArrayList<String> info = new ArrayList<String>();
			for (JTextField campitos: listaCampos) {
				info.add(campitos.getText());
			}
			info.add(foto.getText());
			boolean verifyLleno = verificador.verifyLleno(info);
		
			if (verifyLleno == true) {
				OtrosConductores conductorAdicional = new OtrosConductores(info.get(0),info.get(1),info.get(2),
									info.get(3),info.get(4));
				conductorAdicional.registrarLicencia(principal.idReservaActual);
				principal.cambiarPanel(grito);
			
			}
			else {
				error = new ErrorDisplay("PORFAVOR LLENE TODOS LOS CAMPOS!!");
			}
		}
	}
	
	private void addSpace(int Yspace) {
		add(Box.createRigidArea(new Dimension(0, Yspace)), gbc);
	}

}
