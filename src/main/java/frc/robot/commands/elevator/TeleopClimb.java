package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;

import java.util.function.Supplier;

public class TeleopClimb extends CommandBase {
    private final Elevator elevator = Elevator.getInstance();

    private final Supplier<Boolean> up, down, hookGrab, hookRelease;

    public TeleopClimb(Supplier<Boolean> up, Supplier<Boolean> down, Supplier<Boolean> hookGrab, Supplier<Boolean> hookRelease) {
        this.up = up;
        this.down = down;
        this.hookGrab = hookGrab;
        this.hookRelease = hookRelease;

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

        if(hookRelease.get())
            elevator.clampPistonRelease();
        else if(hookGrab.get())
            elevator.clampPistonHold();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
