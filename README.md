# Horse Race Simulator

## About the Project
The Horse Race Simulator consists of two parts:
- **Part 1**: A textual version with the `Horse` class and a modified `Race` class.
- **Part 2**: A graphical version that includes the `Horse` and `Race` classes, along with additional classes for GUI functionality.

This simulation allows users to:
- Customize various characteristics of a horse, which affect its confidence (a primary factor for winning, but higher confidence increases the chance of falling).
- View detailed statistics for each horse, including wins, time taken, and more.
- Place bets on horses and track betting outcomes.

## Setup

### Operating Systems
- Windows
- macOS

### System Requirements
- **Java Development Kit (JDK)**: Build version 16
- **Java Runtime Environment (JRE)**: Build version 16

### Dependencies
This app has no external dependencies and only relies on the JDK and JRE.

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/Darkfury12/HorseRaceSimulator.git
cd HorseRaceSimulator
```
### 2. Compile the .java files
Rember to go to the root folder first eg part1 or part2
```bash
javac .java
```
### 3. Run the Program
```bash
java HorseRaceSimulator
```
## Usage Instructions
![RaceTrack pannel ](part2/Trackpanel.png)
### Customize Horses:
![Betiting Window ](part2/CustomiseHorse.png)
Open the application and click the "Customise Horses" button.
Modify horse attributes such as breed, coat color, saddle, and symbol.
Save your changes to apply the customizations.


### Place Bets:
![Betiting Window ](part2/betting.png)
Click the "Place Bet" button to open the betting window.
Select a horse and place your bet using the available balance.


### Start the Race:
![Start Race ](part2/buttons.png)
Click the "Start Race" button to begin the race.
Watch the horses compete on the race track.

### View Statistics:
![Statistics window](part2/Stats.png)
After the race, click the "View Statistics" button to open the statistics window.
Analyze horse performance metrics such as average speed, win percentage, and historical race data.

### Adjust Track Settings:
![Track Settinfs ](part2/TrackCustomisation.png)
Use the "Track Shape" dropdown to select the shape of the race track (e.g., oval, figure-eight, zigzag).
Use the "Weather" dropdown to set the weather conditions (e.g., dry, muddy, icy).

### Exit the Application:
Close the application by clicking the "X" button in the top-right corner of the window.