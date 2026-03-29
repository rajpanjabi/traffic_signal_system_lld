package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Intersection {
    // Core Entity
    private UUID id;
    private String name;
    private Map<Direction, TrafficLight> trafficLights;
    private boolean isEmergencyMode;
    private Direction emergencyDirection;
    private boolean isCyclePaused;

    public Intersection(String name ){
        this.id=UUID.randomUUID();
        this.name=name;
        this.trafficLights=new HashMap<>();
        this.isEmergencyMode=false;
        this.isCyclePaused=false;
        for (Direction direction : Direction.values()) {
            this.trafficLights.put(direction, new TrafficLight(direction));
        }
        System.out.println("Intersection created: " + name + " (ID: " + id + ")");

    }

    public boolean isCyclePaused() {
        return isCyclePaused;
    }
    public boolean isEmergencyMode() {
        return isEmergencyMode;
    }
    public Direction getEmergencyDirection() {
        return emergencyDirection;
    }
    public void setIsCyclePaused(boolean isCyclePaused) {
        this.isCyclePaused = isCyclePaused;
    }
    public String getName() {
        return name;
    }
    public UUID getId() {
        return id;
    }
    public TrafficLight getTrafficLight(Direction direction) {
        return trafficLights.get(direction);
    }
    public void setEmergencyMode(boolean emergencyMode) {
        this.isEmergencyMode = emergencyMode;
        System.out.println("Emergency mode " + (emergencyMode ? "enabled" : "disabled") + " for intersection " + id);
    }
    public void setEmergencyDirection(Direction emergencyDirection) {
        this.emergencyDirection = emergencyDirection;
        System.out.println("Emergency direction set to: " + emergencyDirection + " for intersection " + id);
    }
    public void setCyclePaused(boolean cyclePaused) {
        this.isCyclePaused = cyclePaused;
        System.out.println("Cycle " + (cyclePaused ? "paused" : "resumed") + " for intersection " + id);
    }

   


    
}
