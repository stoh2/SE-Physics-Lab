package frc.robot.subsystems.elevator;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Settings;

public class Elevator extends SubsystemBase {
    private static final Elevator instance; 

    static {
        instance = new ElevatorSimu();
    }

    public static Elevator getInstance() {
        return instance;
    }

    public static enum ElevatorState {
        // WRITE STATES FOR L1, L2, AND L3 AS WELL USING CONSTANTS FROM SETTINGS.JAVA
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        STOW(Settings.Elevator.STOW_HEIGHT_METERS),
        L1(Settings.Elevator.L1_HEIGHT_METERS),
        L2(Settings.Elevator.L2_HEIGHT_METERS),
        L3(Settings.Elevator.L3_HEIGHT_METERS);
        
        private double targetHeight;

        // WRITE CONSTRUCTOR

        private ElevatorState(double targetHeight) {
            this.targetHeight = targetHeight;
        }

        public double getTargetHeight() {
            return this.targetHeight;
        }
    }

    private ElevatorState state;

    protected Elevator() {
       this.state = ElevatorState.STOW;
    }

    // WRITE GETTER AND SETTER METHODS FOR THE STATE

    public ElevatorState getState() {
        return state;
    }

    public void setState(ElevatorState state) {
        this.state = state;
    }
    @Override
    public void periodic() {
        // LOG THE TARGET HEIGHT AND STATE OF THE ELEVATOR
        SmartDashboard.putString("Elevator/State", getState().name());
        SmartDashboard.putNumber("Elevator/Target Height", state.getTargetHeight());
    }
}