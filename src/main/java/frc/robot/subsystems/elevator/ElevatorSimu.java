package frc.robot.subsystems.elevator;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

import frc.robot.constants.Settings;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorSimu extends Elevator {
    private final ElevatorSim sim;
    private final PIDController controller;

    private TrapezoidProfile profile;
    private TrapezoidProfile.State setpoint;
    private TrapezoidProfile.State goal;
    private ElevatorVisualizer visualizer;
    protected ElevatorSimu() {

        visualizer = new ElevatorVisualizer();
        sim = new ElevatorSim(
            DCMotor.getKrakenX60(1),
            Settings.Elevator.Encoders.GEARING,
            Settings.Elevator.MASS_KG,
            Settings.Elevator.Encoders.DRUM_RADIUS_METERS,
            Settings.Elevator.MIN_HEIGHT_METERS,
            Settings.Elevator.MAX_HEIGHT_METERS,
            true,
            0.0
        );

        double maxAngularVelRadiansPerSecond = Units.degreesToRadians(400.0);
        double maxAngularAccelRadiansPerSecondSquared = Units.degreesToRadians(400.0);

        TrapezoidProfile.Constraints constraints = new TrapezoidProfile.Constraints(maxAngularVelRadiansPerSecond, maxAngularAccelRadiansPerSecondSquared);

        controller = new PIDController(
            // TUNE THESE PID VALUES IN SETTINGS
            Settings.Elevator.PID.kP,
            Settings.Elevator.PID.kI,
            Settings.Elevator.PID.kD
        );
        
        profile = new TrapezoidProfile(constraints);
        goal = new TrapezoidProfile.State();
        setpoint = new TrapezoidProfile.State();
    }
    
    @Override
    public void periodic() {
        super.periodic();

        goal = new TrapezoidProfile.State(getState().getTargetHeight(), 0.0);
        setpoint = profile.calculate(0.020, setpoint, goal);
        
        // did u forget something??? GO CHECK ARMSIM U DONUT

        double voltage = controller.calculate(sim.getPositionMeters(), setpoint.position);
        sim.setInputVoltage(voltage);
        SmartDashboard.putNumber("Elevator/voltage", voltage);

        // LOG THE CURRENT HEIGHT OF THE ELEVATOR
        SmartDashboard.putNumber("Elevator/CurrentHeight", sim.getPositionMeters());
        // UPDATE THE ELEVATOR VISUALIZER

        visualizer.update(sim.getPositionMeters());
        
    }
}