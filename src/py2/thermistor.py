"""
The MIT License

Copyright 2019 giuliobosco.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
"""

# Domotics thermistor module
# -
# @author giuliobosco
# @version 1.0.1 (2019-04-12 - 2019-04-12)

from math import log


class Thermistor:
    # thermistor datasheet values
    RT0 = 10000
    # thermistor datasheet values
    B = 3977
    # temperature T0 from thermistor datasheet
    T0 = 25 + 273.15

    def __init__(self, vcc=5, r=10000):
        """
        Create Thermistor with volt and resistance
        :param vcc: Maximum volt.
        :param r: Resistance
        """
        # set maximum volt
        self.vcc = vcc
        # set resistance
        self.r = r

    def get_kelvin(self, voltage):
        """
        Get kelvin from in voltage
        :param voltage: In voltage
        :return: Kelvin degreess.
        """
        # execute transformation from voltage to kelvin
        voltage = (5.00 / 1023.00) * float(voltage)
        voltage_reverse = self.vcc - voltage
        resistance = voltage / (voltage_reverse / self.r)
        ln = log(resistance / self.RT0)
        return 1 / ((ln / self.B) + (1 / self.T0))

    def get_celsius(self, voltage):
        """
        Get celsius from voltage.
        :param voltage: In voltage.
        :return: Celsius degreess.
        """
        # trasform from kelvin to degreess and return
        return self.get_kelvin(voltage) - 273.15


def get_celsius(voltage, vcc=5, r=10000):
    """
    Get celsius from voltage.
    :param voltage: In voltage.
    :param vcc: Maximum voltage.
    :param r: Resistence.
    :return: Celisus degreess.
    """
    # initialize thermistor
    therm = Thermistor(vcc, r)
    # trasfrorm from voltage to celisius degrees and return
    return therm.get_celsius(voltage)


def get_kelvin(voltage, vcc=5, r=10000):
    """
    Get kelvin from voltage.
    :param voltage: In voltage.
    :param vcc: Maximum voltage.
    :param r: Resistence.
    :return: Kelvin degreess.
    """
    # initialize thermistor
    therm = Thermistor(vcc, r)
    # transform from voltage to kelvin and return
    return therm.get_kelvin(voltage)
