package domain;

import java.util.UUID;

public class SignalTiming {
    private UUID intersectionId;
    private Direction direction;
    private int greenDuration;
    private boolean isDynamic;
    // Constant yellow duration for safety (3 seconds)
    public static final int YELLOW_DURATION = 3;

    public SignalTiming(UUID intersectionId, Direction direction, int greenDuration){
        this.intersectionId=intersectionId;
        this.direction=direction;
        this.greenDuration=greenDuration;
        isDynamic=false;
        System.out.println("Signal timing created for intersection " + intersectionId + ", direction " + direction);
    }

    public UUID getIntersectionId() {
        return intersectionId;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getGreenDuration() {
        return greenDuration;
    }

    public boolean isDynamic() {
        return isDynamic;
    }

    public void setGreenDuration(int greenDuration) {
        this.greenDuration = greenDuration;
        System.out.println("Green duration set to " + greenDuration + " seconds for " + direction);
    }

    public void setDynamic(boolean dynamic) {
        this.isDynamic = dynamic;
        System.out.println("Dynamic timing " + (dynamic ? "enabled" : "disabled") + " for " + direction);
    }

    /**
     * Calculate the red duration for this direction based on other directions' green and yellow times
     * Red duration = sum of (green + yellow) for all other directions
     */
    public int calculateRedDuration(SignalTiming[] allTimings) {
        int redDuration = 0;
        for (SignalTiming timing : allTimings) {
            if (timing != null && timing.getDirection() != this.direction) {
                redDuration += timing.getGreenDuration() + YELLOW_DURATION;
            }
        }
        return redDuration;
    }

    /**
     * Get the total cycle time for this direction (green + yellow + calculated red)
     */
    public int getTotalCycleTime(SignalTiming[] allTimings) {
        return greenDuration + YELLOW_DURATION + calculateRedDuration(allTimings);
    }

    @Override
    public String toString() {
        return "SignalTiming{" +
                "intersectionId=" + intersectionId +
                ", direction=" + direction +
                ", yellowDuration=" + YELLOW_DURATION +
                ", greenDuration=" + greenDuration +
                ", isDynamic=" + isDynamic +
                '}';
    }
} 
