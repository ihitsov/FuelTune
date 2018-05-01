import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JSlider;
import com.fazecast.jSerialComm.*;

public class serialRead {
 
        public static void main(String[] args) {
                // create a window with a slider
                JFrame window = new JFrame();
                JSlider slider = new JSlider();
                slider.setMaximum(50);
                window.add(slider);
                window.pack();
                window.setVisible(true);
               
                // determine which serial port to use
                SerialPort ports[] = SerialPort.getCommPorts();

                System.out.println("Select a port:");
                int i = 1;
                for(SerialPort port : ports) {
                        System.out.println(i++ + ". " + port.getSystemPortName());
                }
                Scanner s = new Scanner(System.in);
                int chosenPort = s.nextInt();
                // open and configure the port
                SerialPort port = ports[chosenPort - 1];
                port.setBaudRate(230400);
                if(port.openPort()) {
                        System.out.println("Successfully opened the port.");
                } else {
                        System.out.println("Unable to open the port.");
                        return;
                }
                port.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);
//                port.setComPortTimeouts(chosenPort, 10, 10);
                // enter into an infinite loop that reads from the port and updates the GUI
                Scanner data = new Scanner(port.getInputStream());
                BufferedWriter out = null;
				try {
					out = new BufferedWriter(new FileWriter("ComSniffedFile.csv"));
				} catch (IOException e1) {}

                while(true) {
//                        double number = 0;
                        try{System.out.println(data.nextLine());
                        
                        out.write(data.nextLine());
                        out.newLine();
                        
                        
                        
//                        	number = Double.parseDouble(data.nextLine());
                        }
                        catch(Exception e){}
//                        slider.setValue((int) number);
                }
//                data.close();
//                try {
//                    s.close();
//                	out.flush();
//					out.close();
//				} catch (IOException e) {}
        }

}