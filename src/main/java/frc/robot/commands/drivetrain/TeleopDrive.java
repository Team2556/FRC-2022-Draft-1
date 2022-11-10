package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

import java.util.function.Supplier;

public class TeleopDrive extends CommandBase {

    private final Drivetrain drivetrain = Drivetrain.getInstance();

    private final Supplier<Double> forward, strafe, turn;
    private final Supplier<Boolean> align;

    // Mecanum drive, with butterfly capabilities.
    public TeleopDrive(Supplier<Double> forward, Supplier<Double> strafe, Supplier<Double> turn) {
        this.forward = forward;
        this.strafe = strafe;
        this.turn = turn;
        this.align = () -> false;

        addRequirements(drivetrain);
        setName("TeleOp Drive");
    }

    @Override
    public void initialize() {
        Limelight.enableLEDs();
    }

    @Override
    public void execute() {
        if(RobotContainer.getInstance().getDropped())
            drivetrain.dropDrivePistons();
        else
            drivetrain.liftDrivePistons();

        drivetrain.driveTeleop(-forward.get(),
                RobotContainer.getInstance().getDropped() ? 0 : strafe.get(),
                align.get() ? Limelight.getLimelight().getCalculatedTurn() : turn.get());
        drivetrain.driveTeleop(-forward.get(), strafe.get(), turn.get());
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.driveTeleop(0.0,0.0,0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}