package frc.robot.subsystems.arm;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Settings;

public class Arm extends SubsystemBase {

    // WRITE SINGLETON HERE!!!
    // What is a singleton you may ask? 
    // It is the part where you create the static instance of your class...
    // Make sure to instantiate to ArmSim()
    private static final Arm instance;

    static {
        instance = new ArmSim();
    }

    public enum ArmState {
        ONE(Rotation2d.kZero),
        TWO(Settings.Arm.MIN_ANGLE),
        THREE(Settings.Arm.MAX_ANGLE),
        FOUR(new Rotation2d((Settings.Arm.MIN_ANGLE.getRadians() + Settings.Arm.MAX_ANGLE.getRadians())/2));
        // WRITE 3 MORE ARM STATES: TWO THREE FOUR

        private Rotation2d targetAngle;

        private ArmState(Rotation2d targetAngle) {
            this.targetAngle = Rotation2d.fromDegrees(MathUtil.clamp(targetAngle.getDegrees(), Settings.Arm.MIN_ANGLE.getDegrees(), Settings.Arm.MAX_ANGLE.getDegrees()));
        }

        public Rotation2d getTargetAngle() {
            return this.targetAngle;
        }
    }

    private ArmState state;

    protected Arm() {
        this.state = ArmState.ONE;
    }

    public ArmState getState() {
        return this.state;
    }

    public void setState(ArmState state) {
        this.state = state;
    }

    public void periodic() {
        SmartDashboard.putString("Arm/state", getState().name());
    }

    public static Arm getInstance() {
        return instance;
    }
    
}