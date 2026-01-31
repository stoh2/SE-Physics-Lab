package frc.robot.subsystems.arm;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.constants.Constants;
import frc.robot.constants.Settings;

public class ArmSim extends Arm {
    private final SingleJointedArmSim sim;
    private final PIDController controller;

    private TrapezoidProfile profile;
    private TrapezoidProfile.State setpoint;
    private TrapezoidProfile.State goal;
    private ArmVisualizer visualizer;

    protected ArmSim() {
        super();
        sim = new SingleJointedArmSim(
            DCMotor.getKrakenX60(1),
            Constants.Arm.GEAR_RATIO,
            Constants.Arm.MOMENT_OF_INERTIA,
            Constants.Arm.ARM_LENGTH,
            Settings.Arm.MIN_ANGLE.getRadians(),
            Settings.Arm.MAX_ANGLE.getRadians(),
            true,
            Settings.Arm.MIN_ANGLE.getRadians()
        );

        double maxAngularVelRadiansPerSecond = Units.degreesToRadians(400.0);
        double maxAngularAccelRadiansPerSecondSquared = Units.degreesToRadians(400.0);
        visualizer = ArmVisualizer.getInstance();

        TrapezoidProfile.Constraints constraints = new TrapezoidProfile.Constraints(maxAngularVelRadiansPerSecond, maxAngularAccelRadiansPerSecondSquared);

        controller = new PIDController(
            // TUNE THESE VALUES
            Settings.Arm.PID.kP,
            Settings.Arm.PID.kI,
            Settings.Arm.PID.kD
        );
        profile = new TrapezoidProfile(constraints);
        goal = new TrapezoidProfile.State();
        setpoint = new TrapezoidProfile.State(); 
    }

    @Override
    public void periodic() {
        super.periodic();

        goal = new TrapezoidProfile.State(getState().getTargetAngle().getRadians(), 0.0);
        setpoint = profile.calculate(0.020, setpoint, goal);

        // USE THE PID CONTROLLER TO CALCULATE THE VOLTAGE TO REACH THE TARGET ANGLE
        double voltage = controller.calculate(sim.getAngleRads() * (180/Math.PI),getState().getTargetAngle().getDegrees());
        sim.setInputVoltage(voltage);
        sim.update(0.02);
        
        SmartDashboard.putNumber("Arm/targetAngle", getState().getTargetAngle().getDegrees());
        SmartDashboard.putNumber("Arm/currentAngle", sim.getAngleRads() * (180 / Math.PI));
        SmartDashboard.putNumber("Arm/voltage", voltage);

        // UPDATE THE ANGLE OF THE ARM VISUALIZER
        visualizer.updateArmAngle(new Rotation2d(sim.getAngleRads()));
    }

}