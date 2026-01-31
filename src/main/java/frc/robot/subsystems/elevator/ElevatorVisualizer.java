package frc.robot.subsystems.elevator;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.robot.subsystems.elevator.Elevator.ElevatorState;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorVisualizer {

    private static final ElevatorVisualizer instance;

    static {
        instance = new ElevatorVisualizer();
    }

    private final Mechanism2d canvas;
    private final MechanismRoot2d vertical;
    private final MechanismRoot2d elevator;

    ElevatorVisualizer() {
        canvas = new Mechanism2d(25, 25);
        vertical = canvas.getRoot("Vertical", 10, 5);
        elevator = canvas.getRoot("Elevator", 8, 5);
        vertical.append(new MechanismLigament2d(
            "Vertical",
            10, 
            90,
            3, 
            new Color8Bit(Color.kOrange)
            )
        );
        elevator.append(new MechanismLigament2d(
            "Elevator",
            4, 
            0,
            3, 
            new Color8Bit(Color.kOrange)
            )
        );
    }   
    
    public void update(double elevatorHeight) {
        // UPDATE THE POSITION OF THE ELEVATOR MECHANISM ROOT
        elevator.setPosition(8,elevatorHeight); // error to make you look at ts 

        SmartDashboard.putData("Visualizers/Elevator", canvas);
    }

}