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


### Step 2: Identify Core Entities

#### Entity 1:
#### Entity 2:
#### Entity 3:
#### Entity 4:


### Step 3: Visual Interaction Flow

#### Flow 1:
#### Flow 2:
#### Flow 3:


### Step 4: Define Class Structures and Relationships

#### Layers of Architecture

Client Layer → Controller Layer → Service Layer → Repository Layer → Domain Layer

#### Controllers:
- C1:
- C2:

#### Services:
- C1:
- C2:

#### Repository:
- C1:
- C2:

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


