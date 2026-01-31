package frc.robot.commands;

import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.elevator.Elevator.ElevatorState;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ElevatorSetState extends InstantCommand {
    public Elevator elevator;
    public ElevatorState elevatorState;
    // WRITE THE COMMAND
    // Don't forget addRequirements(...) in your constructor
    public ElevatorSetState(ElevatorState elevatorState) {
        elevator = Elevator.getInstance();
        elevator.setState(elevatorState);

        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        elevator.setState(elevatorState);
    }
}