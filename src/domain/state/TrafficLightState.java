package domain.state;

import domain.TrafficLight;

public interface TrafficLightState {
    // this is the contract that each state implements
    void turnGreen(TrafficLight trafficlight);
    void turnYellow(TrafficLight trafficlight);
    void turnRed(TrafficLight trafficlight);
    void turnOff(TrafficLight trafficlight);
    String getStateName();
    boolean canTransitionTo(TrafficLightState newState);
    
}
