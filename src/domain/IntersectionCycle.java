package domain;
import java.util.UUID;

public class IntersectionCycle {
    private UUID intersectionId;
    private int currentPhase;
    private boolean isPaused;
    private int pausedAtPhase;
    private long phaseStartTime;
    private long pauseStartTime; // NEW: Track when pause started
    private long totalPauseTime; // NEW: Track total pause duration


    // 0 -> N, 1 -> E, 2 -> S, 3 -> W,
    public IntersectionCycle(UUID intersectionId){
        this.intersectionId=intersectionId;
        this.currentPhase=0;
        this.isPaused=false;
        this.pausedAtPhase = 0;
        this.phaseStartTime=System.currentTimeMillis();
        this.pauseStartTime = 0;
        this.totalPauseTime = 0;
        System.out.println("Intersection cycle created for intersection: " + intersectionId);
    }

    public UUID getIntersectionId() {
        return intersectionId;
    }

    public int getCurrentPhase() {
        return currentPhase;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public int getPausedAtPhase() {
        return pausedAtPhase;
    }

    public long getPhaseStartTime() {
        return phaseStartTime;
    }

    // get phase elapsed time
    public long getPhaseElapsedTime(){
        if (isPaused){
            // it it was paused then only time elapsed is from the moment phase started to the time it was paused
            return pauseStartTime-phaseStartTime;
        }
        // else
        return System.currentTimeMillis() - phaseStartTime -totalPauseTime;
    }

    // get total pause time
    public long getTotalPauseTime(){
        if (isPaused){
            return totalPauseTime +System.currentTimeMillis()-pauseStartTime;
        }
        return totalPauseTime;
    }

    // get Phase remaining time
    // we give the duration of how long this phase should be and then deduct elapsed time
    public long getPhaseRemainingTime(int duration){
        long remainingtime= (duration*1000)- getPhaseElapsedTime();
        return Math.max(0,remainingtime);
    }

    // Setters
    // set current phase
    public void setCurrentPhase(int phase){
        this.currentPhase=phase;
        this.phaseStartTime=System.currentTimeMillis();
        this.totalPauseTime = 0; // Reset pause time for new phase
        System.out.println("Phase changed to: " + currentPhase + " for intersection " + intersectionId);

    }

    public void setPaused(boolean pause){
        this.isPaused=pause;
        if (isPaused){
            this.pausedAtPhase=currentPhase;
            this.pauseStartTime=System.currentTimeMillis();
            System.out.println("Cycle paused at phase: " + this.currentPhase + 
                             " (elapsed: " + getPhaseElapsedTime()/1000 + "s) for intersection " + intersectionId);
        }
        else{
            // this is the resume case, when isPaused is set to false, then we resume the cycle
            this.totalPauseTime+=System.currentTimeMillis()-pauseStartTime;
            System.out.println("Cycle resumed from phase: " + this.pausedAtPhase + 
                             " (remaining: " + getPhaseRemainingTime(30)/1000 + "s) for intersection " + intersectionId);
        }
    }

    public void setPausedAtPhase(int pausedAtPhase) {
        this.pausedAtPhase = pausedAtPhase;
        System.out.println("Paused phase set to: " + pausedAtPhase + " for intersection " + intersectionId);
    }


    // move to next phase
    public void nextPhase(int phase){
        this.currentPhase=(currentPhase+1)%4;
        this.phaseStartTime=System.currentTimeMillis();
        this.pauseStartTime=0;
        System.out.println("Moved to next phase: "+ currentPhase+" for intersection: "+intersectionId);
    }

    public boolean isPhaseCompleted(int duration){
        return getPhaseElapsedTime() >= (duration*1000);
    }

    @Override
    public String toString() {
        return "IntersectionCycle{" +
                "intersectionId=" + intersectionId +
                ", currentPhase=" + currentPhase +
                ", isPaused=" + isPaused +
                ", pausedAtPhase=" + pausedAtPhase +
                ", phaseElapsed=" + getPhaseElapsedTime()/1000 + "s" +
                ", totalPauseTime=" + getTotalPauseTime()/1000 + "s" +
                '}';
    }
} 
