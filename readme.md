## Parking Lot LLD

### Step 1: Clarify Requirements

#### Functional Requirements
– The system should control traffic signals at a single intersection (4 traffic lights as a unit).
- The system should manage automatic cycling through phases (NORTH → EAST → SOUTH → WEST).
- The system should handle emergency vehicle priority requests by pausing the automatic cycle.
- During emergency: All signals turn RED, emergency direction gets GREEN, then resume cycle from pause.
- The system should track vehicle count at each approach.
- The system should prevent conflicting signals from being  active simultaneously.
- The system should have configurable signal durations (RED, YELLOW, GREEN) for each direction.
- The system should allow dynamic adjustment of signal durations based on traffic conditions.

#### Non-Functional Requirements
- The system should respond to user actions within reasonable time.
- The system should have proper state transitions.
- The system should be scalable to multiple intersections.

#### Edge-Cases
- Emergency vehicle request during signal change.
- Invalid signal state transitions (handled by State Pattern).
- Cycle pause/resume during emergency.


### Step 2: Identify Core Entities

#### Entity 1: Intersection (Core Entity)
- id: int [PK]
- name: String
- trafficLights: TrafficLight[] (4 lights: NORTH, SOUTH, EAST, WEST)
- isEmergencyMode: boolean
- emergencyDirection: Direction (nullable)
- isCyclePaused: boolean


#### Entity 2: IntersectionCycle
- intersectionId: int [FK]
- currentPhase: int (0=NORTH, 1=EAST, 2=SOUTH, 3=WEST)
- isPaused: boolean
- pausedAtPhase: int
- phaseStartTime: long (timestamp)


#### Entity 3: TrafficLight
- direction: Enum (NORTH, SOUTH, EAST, WEST)
- currentState: TrafficLightState (State Pattern implementation)
- Consider the following transitions in TrafficLight:
- Valid Transitions: RED → GREEN → YELLOW → RED
- Invalid Transitions: RED → YELLOW, GREEN → RED (blocked by state pattern)

#### Entity 4: SignalTiming
- intersectionId: int [FK]
- direction: Enum (NORTH, SOUTH, EAST, WEST)
- greenDuration: int (seconds)
- isDynamic: boolean (for traffic-based adjustment)
- yellowDuration is a constant (3 seconds) for safety


#### Entity 5: VehicleCounter
- direction: Enum (NORTH, SOUTH, EAST, WEST)
- count: int
- astUpdate: long (timestamp)


#### Entity 6:EmergencyRequest
- id: int [PK]
- intersectionId: int [FK]
- direction: Enum (NORTH, SOUTH, EAST, WEST)
- duration: int (seconds)
- isActive: boolean


#### Entity 7: TrafficLightState
- State interface for traffic light state management


#### Entity 8: RedState
- Concrete state for RED traffic light


#### Entity 9: GreenState
- Concrete state for GREEN traffic light


#### Entity 10: YellowState
- Concrete state for YELLOW traffic light


#### Entity 11: OffState
- Concrete state for OFF traffic light


### Step 3: Visual Interaction Flow

#### Flow 1: Intersection Management Flows:
* Intersection Creation Flow:
```Create intersection → Initialize 4 traffic lights → Set default signal timings → Start automatic cycle```

* Intersection Status Flow:
``` Request status → Return all signal states, cycle info, and current timings ```

#### Flow 2: Automatic Cycle Management Flows:
* Normal Cycle Flow
``` Cycle through phases: North -> East -> South -> West```

``` For each phase, valid state transitions: GREEN (configurable duration) -> YELLOW (configurable duration) -> RED -> Next Phase```

* Pause/Resume Cycle Flow

``` Pause cycle → Remember current phase → Resume from same phase ```

#### Flow 3: Signal Timing Management Flows:

* Timing Configuration Flow:
```Set signal timing → Update SignalTiming for direction → Apply to next cycle```

* Dynamic Timing Adjustment Flow:
```Traffic condition detected → Calculate optimal timing → Update SignalTiming → Apply immediately or next cycle```

#### Flow 4: Emergency Management Flows:

* Emergency Request Flow:
```Emergency request → PAUSE cycle → ALL signals transition to RED (following proper state sequence) → Emergency direction GREEN → Wait duration → Resume cycle from pause```

* Emergency End Flow:
```End emergency → All signals transition to RED (following proper state sequence) → Resume cycle from paused phase```

#### Flow 5: Vehicle Counting Flows:

* Count Update Flow:
```Vehicle detected → Update count for direction → Trigger dynamic timing adjustment if enabled (in future)```

* Count Query Flow:
```Request count → Return vehicle count for direction```

#### Flow 6: State Transition Flows:

* Valid State Transition Flow:
```TrafficLight.turnGreen() → currentState.turnGreen(this) → setState(new GreenState())```

* Invalid State Transition Flow:
```TrafficLight.turnYellow() → currentState.turnYellow(this) → throws InvalidStateTransitionException```

* Emergency State Transition Flow:
```Emergency transition → Check current state → Follow proper sequence (GREEN → YELLOW → RED) → Handle each state appropriately → Log transition sequence```


### Step 4: Define Class Structures and Relationships

#### Layers of Architecture

Client Layer → Controller Layer → Service Layer → Repository Layer → Domain Layer

#### Controllers:
- C1:  IntersectionController (Main Controller)
    - void createIntersection(int id, String name)
    - Intersection getIntersection(int intersectionId)
    - void startCycle(int intersectionId)
    - void displayStatus(int intersectionId)


- C2: EmergencyController (Emergency Management)
    - void requestEmergency(int intersectionId, Enum direction, int duration)
    - void endEmergency(int intersectionId)


- C3: TrafficController
    - void updateVehicleCount(Enum direction, int count)
    - int getVehicleCount(Enum direction)


- C4: TimingController (Timing Management)
    - void setSignalTiming(int intersectionId, Enum direction, int greenDuration)
    - void enableDynamicTiming(int intersectionId, Enum direction, boolean enable)
    - SignalTiming getSignalTiming(int intersectionId, Enum direction)


#### Services:
- S1: IntersectionService (Core Service)
    - void createIntersection(int id, String name)
    - Intersection getIntersection(int intersectionId)
    - void startAutomaticCycle(int intersectionId)
    - void pauseCycle(int intersectionId)
    - void resumeCycle(int intersectionId)
    - IntersectionCycle getCycle(int intersectionId)
    - void setAllSignalsToRed(int intersectionId)
    - void emergencySetAllSignalsToRed(int intersectionId)
    - void setSignalToGreen(int intersectionId, Direction direction)
    - void setSignalToYellow(int intersectionId, Direction direction)
    - void setSignalToRed(int intersectionId, Direction direction)
    - void setSignalToOff(int intersectionId, Direction direction)

- S2: EmergencyService (Core Emergency Service)
    - void requestEmergency(int intersectionId, Enum direction, int duration)
    - void endEmergency(int intersectionId)
    - EmergencyRequest getActiveEmergency(int intersectionId)


- S3: TrafficService
    - void updateVehicleCount(Enum direction, int count)
    - int getVehicleCount(Enum direction)


- S4: TimingService (Timing Management)
    - void setSignalTiming(int intersectionId, Enum direction, int greenDuration)
    - void enableDynamicTiming(int intersectionId, Enum direction, boolean enable)
    - SignalTiming getSignalTiming(int intersectionId, Enum direction)
    - void adjustTimingBasedOnTraffic(int intersectionId, Enum direction)
    - int calculateOptimalGreenDuration(int vehicleCount)


#### Repository:
- R1: IntersectionRepository
    - void save(Intersection intersection)
    - Intersection findById(int intersectionId)
    - void updateCycle(int intersectionId, IntersectionCycle cycle)
    - void updateEmergencyMode(int intersectionId, boolean emergencyMode, Enum direction)


- R2: EmergencyRepository
    - void save(EmergencyRequest request)
    - EmergencyRequest getActiveEmergency(int intersectionId)
    - void updateStatus(int requestId, boolean isActive)


- R3: TrafficRepository
    - void updateCount(Enum direction, int count)
    - int getCount(Enum direction)


- R4: TimingRepository (Timing Data Access)
    - void saveSignalTiming(SignalTiming timing)
    - SignalTiming getSignalTiming(int intersectionId, Enum direction)
    - void updateSignalTiming(int intersectionId, Enum direction, int greenDuration)


#### Domain:
- C1:
- C2:

#### Interfaces and Adapters:
- C1:
- C2:

### Step 5: Implement Core Use Cases

#### Use Case 1:
#### Use Case 2:
#### Use Case 3:


### Step 6: Apply OOP Principles & Design Patterns

#### Design Pattern Used:
- Adapter Pattern: Abstraction of payment gateways
- Repository Pattern: Isolation of database operations
- Service Layer Pattern: Centralization of business logic

#### OOP Principles Applied:
-
-
-

### Step 7: Handle Edge Case

- Edge Case 1 : Strategy
- Edge Case 2 : Strategy
- Edge Case 3 : Strategy


### Step 8: Package Structure

### Step 9: Class Diagram


