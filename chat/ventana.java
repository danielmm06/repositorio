import java.awt.BorderLayout;   
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ventana extends JFrame  implements Runnable{
	JTextArea caja=new JTextArea();
	JTextField info=new JTextField(20);
	JButton boton=new JButton();
	String infoserver="desconectado";
	JLabel texto=new JLabel("Informacion =");
	
	JScrollPane scroll = new JScrollPane();
	
	String recibido;
    OutputStream ssalida;
	DataOutputStream xsalida;
	boolean salida=true;

	InputStream ientrada;
	DataInputStream xentrada;

	Socket cliente;
	Thread hilocaja;
	
	
	public ventana() {
		
		setSize(400,350);
		setLocation(300,250);
		
		servidor();
		
		setVisible(true);	
		
		scroll.setViewportView(caja);
		try {	
			
			cliente = new Socket("127.0.0.1", 3000);  
			ssalida = cliente.getOutputStream();
			xsalida = new DataOutputStream(ssalida);

			ientrada = cliente.getInputStream();
			xentrada = new DataInputStream(ientrada);
 
			recibido = xentrada.readUTF();
	        caja.setText(caja.getText()+recibido);
		}
		catch (Exception e) {
			System.out.println("Algo salio mal al recibir");
			System.err.println("Error: " + e);
		}
		
		hilocaja=new Thread(this);
		hilocaja.start();
		
	}


	public void servidor() { 
		try {
			
		} catch (Exception e) {
			
		}  
		
		this.setLayout(new GridLayout(1, 1, 1, 1));
		JPanel Pservidor=new JPanel();
		Pservidor.setLayout(new BorderLayout());
				
		JPanel Pcliente=new JPanel();
		Pcliente.setLayout(new BorderLayout());
		JPanel pcentro=new JPanel();
		pcentro.setLayout(new FlowLayout());
		this.info.setText("Ingresar mensaje");
		this.boton.setText("Enviar");
		pcentro.add(this.info);
		pcentro.add(this.boton);
		boton.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent evento){
					 try {
						xsalida.writeUTF(info.getText()+"\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
							
					}	
				}
			);
		
		
		Pcliente.add(new JLabel("Seleccion servidor"), BorderLayout.NORTH);
		Pcliente.add(pcentro, BorderLayout.CENTER);
		Pcliente.add(texto, BorderLayout.SOUTH);
		
		Pservidor.add(new JLabel("Seleccion servidor"), BorderLayout.NORTH);
		Pservidor.add(scroll, BorderLayout.CENTER);
		Pservidor.add(Pcliente, BorderLayout.SOUTH);
		this.add(Pservidor);
		
			}
	public void cliente() {
		this.setLayout(new GridLayout(1, 1, 1, 1));
				
	}
	
public void cerrarsesion(){
		
		try {
			xsalida.close();
			xentrada.close();
		    cliente.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

public void run() {
	Thread ct= Thread.currentThread();
	if(ct==hilocaja){
		try {
			
			do{
			recibido = xentrada.readUTF();
			caja.setText(caja.getText()+recibido);
	    
	        
	}while(true);
		} catch (Exception e) {
			caja.setText(caja.getText()+"Error al recibir la informacion \n ");
		}
	
	}

}		

	public static void main(String[] args) {
		ventana obj= new ventana();
	}
	
}
