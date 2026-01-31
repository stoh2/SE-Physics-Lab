package frc.robot.constants;

import edu.wpi.first.math.geometry.Rotation2d;

public class Settings {
    public interface Arm {
        Rotation2d MIN_ANGLE = Rotation2d.fromDegrees(-90);
        Rotation2d MAX_ANGLE = Rotation2d.fromDegrees(235);

        // PID TUNE!!!!!!

        public interface PID {
            double kP = 0.346;
            double kI = 0.0;
            double kD = 0.1;
        }
    } 

    public interface Elevator {
        public interface Encoders {
            double GEARING = 3.0;
            double DRUM_RADIUS_METERS = 0.5;
        }

        // PID TUNE!!!!!!

        public interface PID {
            double kP = 33.66;
            double kI = 0.0;
            double kD = 0.75;
        }

        double MASS_KG = 2.0;
        double MIN_HEIGHT_METERS = 5.0;
        double MAX_HEIGHT_METERS = 10.0;
    
        double STOW_HEIGHT_METERS = 5.0;
        double L1_HEIGHT_METERS = 6.0;
        double L2_HEIGHT_METERS = 8.0;
        double L3_HEIGHT_METERS = 10.0;
    }
}
