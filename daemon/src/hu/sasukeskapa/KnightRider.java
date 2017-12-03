package hu.sasukeskapa;

import com.pi4j.io.gpio.*;

import java.util.ArrayList;

public class KnightRider implements Runnable {
    public void run() {
        knightRider();
    }

    void knightRider() {
        // create gpio controller instance
        final GpioController gpio = GpioFactory.getInstance();

        // RaspiPinNumberingScheme a;

        // provision gpio pins #04 as an output pin and make sure is is set to LOW at startup
        GpioPinDigitalOutput myGPIO0 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.HIGH);      // PIN NUMBER
        GpioPinDigitalOutput myGPIO1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.HIGH);      // PIN NUMBER
        GpioPinDigitalOutput myGPIO2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.HIGH);      // PIN NUMBER
        GpioPinDigitalOutput myGPIO3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.HIGH);      // PIN NUMBER

        // use pulse method to set the pin to the HIGH state for
        // an explicit length of time in milliseconds
        //myGPIO0.pulse(1000);

        ArrayList<GpioPinDigitalOutput> outPins = new ArrayList<>();
        outPins.add(myGPIO0);
        outPins.add(myGPIO1);
        outPins.add(myGPIO2);
        outPins.add(myGPIO3);

        // reset relays
        for (GpioPinDigitalOutput out : outPins) {
            out.high();
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                // reset relays
                for (GpioPinDigitalOutput out : outPins) {
                    out.high();
                }
                // knight rider
                if (j < 4) {
                    // knight rider right
                    outPins.get(j).low();
                } else {
                    // knight rider left
                    outPins.get(outPins.size() - (j - (outPins.size() - 2))).low();
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // shutdown relays
        for (GpioPinDigitalOutput out : outPins) {
            out.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        }
        gpio.shutdown();
        for (GpioPinDigitalOutput out : outPins) {
            gpio.unprovisionPin(out);
        }

        System.out.println("Yay :)");
    }
}
