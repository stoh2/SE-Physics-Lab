package frc.robot.commands;

import frc.robot.subsystems.arm.Arm;
import frc.robot.subsystems.arm.Arm.ArmState;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ArmSetState extends InstantCommand {
    public Arm arm;
    public ArmState armState;
    // WRITE THIS COMMAND!!!
    // Don't forget addRequirements(...) in your constructor
    public ArmSetState(ArmState armState) {
        arm = Arm.getInstance();
        arm.setState(armState);
        addRequirements(arm);
    }
    @Override
    public void initialize() {
        arm.setState(armState);
    }
    
}