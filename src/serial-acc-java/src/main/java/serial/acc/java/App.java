/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package serial.acc.java;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;

public class App {
    public static final byte INIT = 42;

    public static void main(String[] args) {
        SerialPort serialPort =  SerialPort.getCommPort("/dev/tty.usbmodem14101");
        //serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 100000000, 10000000);
        System.out.println(serialPort.openPort());
        InputStream input = serialPort.getInputStream();

        int read;
        try {
            while ((read = input.read()) != 0) {
                byte c = (byte)(0x000000FF & read);

                if (c == INIT) {
                    System.out.println("Return");
                }
                System.out.print((char)c);

            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println(ioe.getMessage());
        }
    }
}
