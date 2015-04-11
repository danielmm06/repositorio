import java.io.*;  
import java.net.*;
import javax.swing.JTextArea;

public class Cliente implements Runnable{
	String recibido;
    OutputStream ssalida;
	DataOutputStream xsalida;
	boolean salida=true;

	InputStream ientrada;
	DataInputStream xentrada;

	Socket cliente;
	Thread hilocaja;
	JTextArea caja;
	public Cliente(JTextArea caja){
		this.caja=caja;
		hilocaja=new Thread(this);
		hilocaja.start();
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
			System.err.println("Error: " + e);
		}
	}

	public void mensaje(String mmensaje){
		
		try {	
			xsalida.writeUTF(mmensaje+"\n");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
		
	public void cerrar_sesion(){
		
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
		        System.out.println("EL cliente a recibido: "+recibido);
			
		}while(true);
		
       } catch (Exception e) {
				
			}
		}
	
	}
	
 
	
}