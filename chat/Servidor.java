import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Servidor {
	
    public static void main(String[] args) throws IOException {    
			ArrayList<Servidor_Hilo> clientes=new ArrayList<>();
			int numcliente=0;
		try {
			ServerSocket servidor = new ServerSocket(3000);			
			do
			{
							
				System.out.println("Esperando al cliente");							
				Socket socketConectado = servidor.accept();
                numcliente++;
                Servidor_Hilo hilo_cliente= new Servidor_Hilo(socketConectado,numcliente,clientes);
                clientes.add(hilo_cliente);
                Runnable nuevoSocket=hilo_cliente;
              
				Thread hiloSocket = new Thread(nuevoSocket);
				
				hiloSocket.start();
				
				System.out.println("Cliente recibido");								
			}while(true);
			
		}
		catch (IOException excepcion) {			
			System.out.println(excepcion);
		}

    }
}