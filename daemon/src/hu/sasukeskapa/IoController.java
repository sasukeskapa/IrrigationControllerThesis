package hu.sasukeskapa;

import com.pi4j.io.gpio.*;

import java.time.LocalTime;
import java.util.ArrayList;

public class IoController {
    private GpioController gpioController;
    private ArrayList<GpioPinDigitalOutput> outPins;
    private boolean initialized = false;

    public IoController() {
        init();
    }

    public boolean init() {
        if (!initialized) {
            gpioController = GpioFactory.getInstance();
            outPins = new ArrayList<>(28);  //RPi has 28 controllable GPIO pins
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_29, PinState.HIGH)); //i=0 (1st item)
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_25, PinState.HIGH));
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_28, PinState.HIGH));
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_24, PinState.HIGH));
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_27, PinState.HIGH)); //i=4 (5th item)
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_23, PinState.HIGH));
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_22, PinState.HIGH));
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_26, PinState.HIGH));
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_21, PinState.HIGH));
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_30, PinState.HIGH)); //i=9 (10th item)
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_31, PinState.HIGH));
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_11, PinState.HIGH));
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_14, PinState.HIGH));
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_10, PinState.HIGH));
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_13, PinState.HIGH)); //i=14 (15th item)
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_06, PinState.HIGH));
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_12, PinState.HIGH));
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_05, PinState.HIGH));
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.HIGH));
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_04, PinState.HIGH)); //i=19 (20th item)
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.HIGH));
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.HIGH));
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.HIGH));
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_16, PinState.HIGH));
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_07, PinState.HIGH)); //i=24 (25th item)
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_15, PinState.HIGH));
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_09, PinState.HIGH));
            outPins.add(gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_08, PinState.HIGH)); //i=27 (28th item)
            initialized = true;
        }
        return initialized;
    }

    public boolean isInitialized() {
        return initialized;
    }

    void onPin(int i) {
        if (!initialized)
            init();
        outPins.get(i).low();
    }

    void offPin(int i) {
        if (!initialized)
            init();
        outPins.get(i).high();
    }

    void offAllPins() {
        if (!initialized)
            init();
        for (GpioPinDigitalOutput out : outPins) {
            out.high();
        }
    }

    void shutdownAllPins() {
        if (initialized) {
            offAllPins();
            for (GpioPinDigitalOutput out : outPins) {
                out.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
            }
            gpioController.shutdown();
            for (GpioPinDigitalOutput out : outPins) {
                gpioController.unprovisionPin(out);
            }
            initialized = false;
            System.out.println(LocalTime.now() + " - all GPIO pins were shutdown properly.");
        }
    }
}
