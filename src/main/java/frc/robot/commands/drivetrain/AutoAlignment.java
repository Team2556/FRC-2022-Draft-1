package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class AutoAlignment extends CommandBase {
    private final Drivetrain drivetrain = Drivetrain.getInstance();

    public AutoAlignment() {
        setName("Limelight Alignment");
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        Limelight.enableLEDs();
    }

    @Override
    public void execute() {
//        drivetrain.driveTeleop(0.0,0.0, Limelight.getCalculatedTurn());
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.driveTeleop(0.0,0.0, 0.0);
        Limelight.disableLEDs();
    }
}