package serial.acc.java;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Serial Thread, opens communication with thread and keep connections.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2020-02-10 - 2020-02-14)
 */
public class SerialThread extends Thread {

    // ------------------------------------------------------------------------------------ Costants

    /**
     * Arduino serial port name.
     */
    private static final String COM_NAME = "Arduino";

    /**
     * Serial read timeout.
     */
    private static final int TIMEOUT = 100000000;

    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Serial port.
     */
    private SerialPort serialPort;

    /**
     * List of command to send.
     */
    private List<SerialCommand> commands;

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create serial thread, initialize commands list.
     */
    public SerialThread() {
        super();

        this.commands = new ArrayList<>();
    }

    // -------------------------------------------------------------------------------- Help Methods

    /**
     * Search arduino port and set to serialport attribute.
     *
     * @throws IOException I/O error.
     */
    private void searchArduino() throws IOException {
        for (SerialPort serialPort : SerialPort.getCommPorts()) {
            if (serialPort.getDescriptivePortName().contains(COM_NAME)) {
                this.serialPort = serialPort;
            }
        }

        if (this.serialPort == null) {
            throw new IOException("Arduino not found");
        }
    }

    /**
     * Open Serial Port.
     *
     * @throws IOException I/O.
     */
    private void openSerialPort() throws IOException {
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, TIMEOUT, TIMEOUT);

        if (!serialPort.openPort()) {
            throw new IOException("Error while opening communication with arduino");
        }
    }

    /**
     * Check Serial communication.
     *
     * @throws IOException I/O Error.
     */
    private void checkSerialCommunication() throws IOException {
        InputStream in = this.serialPort.getInputStream();
        OutputStream out = this.serialPort.getOutputStream();

        int read;

        while ((read = in.read()) != SerialCommunication.INIT) {
        }

        boolean isOk = false;

        while (!isOk) {

            SerialEchoCommand serialEchoCommand = new SerialEchoCommand(new byte[]{20});

            serialEchoCommand.write(out);
            SerialCommunication serialResponse = new SerialCommunication();
            serialResponse.readUntilEnd(in);

            isOk = serialResponse.isSame(SerialResponse.OK) &&
                    Arrays.equals(serialEchoCommand.getMessage(), serialResponse.getMessage());
        }

    }

    /**
     * Read command and write response.
     *
     * @throws IOException I/O Error.
     */
    private void readCommandWriteResponse() throws IOException {
        SerialCommunication serialCommunication = new SerialCommunication();
        serialCommunication.readUntilEnd(this.serialPort.getInputStream());

        SerialResponse serialResponse = SerialCommand.loadCommand(serialCommunication);

        if (serialResponse != null) {
            serialResponse.write(this.serialPort.getOutputStream());
        }
    }

    /**
     * Write command and read response.
     *
     * @throws IOException I/O Error.
     */
    private void writeCommandReadResponse() throws IOException {
        if (this.commands.size() > 0) {
            writeOnSerial(this.commands.get(0));

            SerialCommunication serialCommunication = new SerialCommunication();
            serialCommunication.readUntilEnd(this.serialPort.getInputStream());

            if (serialCommunication.isSame(SerialResponse.OK)) {
                removeCommand(this.commands.get(0));
            }
        } else {
            writeOnSerial(SerialCommand.NULL);
        }
    }

    /**
     * Write on Serial.
     *
     * @param serialCommunication Serial communication to write.
     * @throws IOException I/O Error.
     */
    private void writeOnSerial(SerialCommunication serialCommunication) throws IOException {
        serialCommunication.write(this.serialPort.getOutputStream());
    }

    /**
     * Remove command from commands list.
     *
     * @param command Command to remove.
     */
    private void removeCommand(SerialCommand command) {
        this.commands.remove(command);
    }

    /**
     * Add command from commands list.
     *
     * @param command Command to add.
     */
    public void addCommand(SerialCommand command) {
        this.commands.add(command);
    }

    // ----------------------------------------------------------------------------- General Methods

    /**
     * Execution of the thread.
     */
    @Override
    public void run() {

        try {
            this.searchArduino();
            this.openSerialPort();
            this.checkSerialCommunication();

            while (!interrupted()) {
                readCommandWriteResponse();
                writeCommandReadResponse();
            }
        } catch (IOException ioe) {

        }
    }
}
