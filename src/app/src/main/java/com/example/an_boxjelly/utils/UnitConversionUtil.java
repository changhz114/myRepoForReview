package com.example.an_boxjelly.utils;

public class UnitConversionUtil {
    /**
     * Convert power from horsepower to watts.
     * Conversion rule: 1 horsepower = 0.746 watts
     *
     * @param horsepower the power in horsepower
     * @return the power in watts
     */
        public static float convertHorsepowerToWatts(float horsepower) {
            return horsepower * 0.746f;
        }

    /**
     * Convert temperature from Fahrenheit to Celsius.
     * Conversion rule: (Fahrenheit - 32) / 1.8 = Celsius
     *
     * @param fahrenheit the temperature in Fahrenheit
     * @return the temperature in Celsius
     */

        public static float convertFahrenheitToCelsius(float fahrenheit) {
            return (fahrenheit - 32) / 1.8f;
        }

    /**
     * Convert distance from feet to meters.
     * Conversion rule: 1 foot = 0.3048 meters
     *
     * @param feet the distance in feet
     * @return the distance in meters
     */

        public static float convertFeetToMeters(float feet) {
            return feet * 0.3048f;
        }

    /**
     * Convert thermal conductivity from British thermal units per foot per hour to watts per meter per kelvin.
     * Conversion rule: 1 BTU/ft/hr = 1.730735 W/mK
     *
     * @param btuPerFtPerHour the thermal conductivity in BTU/ft/hr
     * @return the thermal conductivity in W/mK
     */

        public static float convertBtuPerFtPerHourToWattPerMeterKelvin(float btuPerFtPerHour) {
            return btuPerFtPerHour * 1.730735f;
        }
    }

