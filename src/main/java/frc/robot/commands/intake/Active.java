package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

import java.util.function.Supplier;

public class Active extends CommandBase {
    private final Intake intake = Intake.getInstance();
    private final Supplier<Double> intakeOn;
    private final double translateSpeed;


    public Active(Supplier<Double> intakeOn, double translateSpeed) {
        addRequirements(intake);
        this.intakeOn = intakeOn;
        this.translateSpeed = translateSpeed;
        setName("Intake Active - Custom");
    }

    public Active() {
        //ToDo Make this system better
        this(() -> Constants.intakeSpeed, Constants.translateSpeed);
        setName("Intake Active");
    }

    @Override
    public void execute() {
        if(Math.abs(intakeOn.get()) >  0.01)
            intake.liftIntakeSolenoid();
        else
            intake.releaseIntakeSolenoid();

        intake.setIntakeSpeed(intakeOn.get() > 0.8 ? Constants.intakeSpeed : 0);
    }

    @Override
    public void end(boolean interrupted) {
        intake.liftIntakeSolenoid();
        intake.setIntakeSpeed(0);
        intake.setTranslateSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}