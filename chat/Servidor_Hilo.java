import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Servidor_Hilo implements Runnable {

    String recibido;
    OutputStream ssalida;
	DataOutputStream xsalida;

	InputStream ientrada;
	DataInputStream xentrada;

	Socket socket;
	int numero_del_hilo;
	
	ArrayList<Servidor_Hilo> hermanos;

	public Servidor_Hilo(Socket lsocket,int numero_del_hilo,ArrayList<Servidor_Hilo> hermanos){
		try{
			this.numero_del_hilo=numero_del_hilo;
			this.hermanos=hermanos;
			socket = lsocket;			
		}
		catch (Exception excepcion) {
			System.out.println(excepcion);
		}		
	}

	public void run() {	

		try{			
			ssalida = socket.getOutputStream();
			xsalida = new DataOutputStream(ssalida);

			ientrada = socket.getInputStream();
			xentrada = new DataInputStream(ientrada);

			xsalida.writeUTF("Bienvenido al servidor \n");

			do{
				recibido = xentrada.readUTF();	
				
				int tama=hermanos.size();
				for (int i = 0; i < tama; i++) {
					hermanos.get(i).xsalida.writeUTF("Cliente: " + numero_del_hilo + ": " + recibido);
					 
				}
				
			}while(!recibido.equals("bye"));
		}
		catch (IOException excepcion) {
			System.out.println("Salio el cliente: "+numero_del_hilo);
	
		}		
		
		try{
			xsalida.close();
			xentrada.close();
			socket.close();			
		}
		catch (IOException excepcion) {
			System.out.println(excepcion);
		}			
	}
}