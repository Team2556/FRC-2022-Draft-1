package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;

import java.util.function.Supplier;

public class TeleopClimb extends CommandBase {
    private final Elevator elevator = Elevator.getInstance();
    private final Intake intake = Intake.getInstance();

    private final Supplier<Boolean> up, down, hookGrab, hookRelease, yellowArmToggle;

    public TeleopClimb(Supplier<Boolean> up, Supplier<Boolean> down, Supplier<Boolean> hookGrab, Supplier<Boolean> hookRelease, Supplier<Boolean> yellowArmToggle) {
        this.up = up;
        this.down = down;
        this.hookGrab = hookGrab;
        this.hookRelease = hookRelease;
        this.yellowArmToggle = yellowArmToggle;

        addRequirements(elevator);
        setName("TeleOp Climb");
    }

    @Override
    public void execute() {
        if(up.get())
            elevator.setYellowMotorSpeed(-0.25);
        else if(down.get())
            elevator.setYellowMotorSpeed(0.25);
        else
            elevator.setYellowMotorSpeed(0.0);

        if(hookRelease.get() && intake.getTranslateSwitch())
            elevator.clampPistonRelease();
        else if(hookGrab.get() && intake.getTranslateSwitch())
            elevator.clampPistonHold();

        if(yellowArmToggle.get())
            elevator.winchPistonsUp();
        else
            elevator.winchPistonsDown();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
