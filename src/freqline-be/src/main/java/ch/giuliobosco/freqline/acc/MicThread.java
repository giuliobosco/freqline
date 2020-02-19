/*
 * The MIT License
 *
 * Copyright 2020 giuliobosco.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package ch.giuliobosco.freqline.acc;

/**
 * Mic thread, wait until timer then turn off generator.
 *
 * @author giuliobosco (giuliobva@gmail.com)
 * @version 1.0 (2020-02-19 - 2020-02-19)
 */
public class MicThread extends Thread {
    // ---------------------------------------------------------------------------------- Attributes

    /**
     * Serial thread, ACC connection  to arduino.
     */
    private SerialThread serialThread;

    /**
     * Timer to turn off.
     */
    private long timer;

    // -------------------------------------------------------------------------------- Constructors

    /**
     * Create serial thread.
     *
     * @param serialThread Serial thread.
     * @param timer        Timer to turn off.
     */
    public MicThread(SerialThread serialThread, long timer) {
        this.serialThread = serialThread;
        this.timer = timer;
    }

    // ----------------------------------------------------------------------------- General Methods

    /**
     * Wait until timer then turn off generator.
     */
    @Override
    public void run() {
        this.timer = System.currentTimeMillis() + timer;

        try {
            while (System.currentTimeMillis() > this.timer && !interrupted()) {
                Thread.sleep(900);
            }
        } catch (InterruptedException ignored) {

        }

        this.serialThread.addCommand(SerialCommand.GENERATOR_OFF);
    }

}
