package domain;

import domain.state.RedState;
import domain.state.TrafficLightState;

public class TrafficLight {
    private Direction direction;
    private TrafficLightState currentState;

    public TrafficLight(Direction direction){
        this.direction=direction;
        this.currentState= new RedState(); // We start with RED State
        System.out.println("Traffic light created for direction: " + direction + " in RED state");
    }

    public Direction getDirection() {
        return direction;
    }

    public TrafficLightState getCurrentState() { 
        return currentState; 
    }

    public String getCurrentStateName() {
        return currentState.getStateName();
    }

    public void setState(TrafficLightState newState) {
        this.currentState = newState;
    }

    public void turnRed(){
        currentState.turnRed(this);
    }

    public void turnGreen(){
        currentState.turnGreen(this);
    }

    public void turnYellow(){
        currentState.turnYellow(this);
    }

    public void turnOff(){
        currentState.turnOff(this);
    }

    public boolean canTransitionTo(TrafficLightState newState) {
        return currentState.canTransitionTo(newState);
    }

    @Override
    public String toString() {
        return "TrafficLight{" +
                "direction=" + direction +
                ", state=" + currentState.getStateName() +
                '}';
    }

    

    // If we want to transition the traffic light to a different state, we delegate the transition to 
    // that state, so it can check if that transition is possible or not
    // For instance, here when we create the traffic light, it's current state is Red
    // Now we know that that Red can only go to Green, not to yello, so we say currenstate.turngreen() whcih
    // under the hood calls the logic from RedState's turnYellow implementation which will handle this invalid state transition

    
}
