package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

public class Butterfly extends CommandBase {

    Drivetrain drivetrain = Drivetrain.getInstance();

    public Butterfly() {
//        addRequirements(drivetrain);
        setName("Butterfly Manager");
    }

    @Override
    public void execute() {

    }

//    @Override
//    public void end(boolean interrupted) {
//        drivetrain.liftDrivePistons();
//    }

    @Override
    public boolean isFinished() {
        return false;
    }
}