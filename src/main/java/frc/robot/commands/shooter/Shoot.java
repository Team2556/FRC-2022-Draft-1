package frc.robot.commands.shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.commands.intake.Active;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

import java.util.function.Supplier;

public class Shoot extends CommandBase {

    Shooter shooter = Shooter.getInstance();
    Intake intake = Intake.getInstance();
    Supplier<Double> trigger;
    Supplier<Boolean> hoodUp, hoodDown;

    public Shoot(Supplier<Double> trigger, Supplier<Boolean> hoodUp, Supplier<Boolean> hoodDown) {
        this.trigger = trigger;
        this.hoodUp = hoodUp;
        this.hoodDown = hoodDown;

        setName("Shooting State");
        addRequirements(shooter);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        boolean shouldShoot;
        if(!shooter.isHoodReset())
            shooter.hoodResetBySwitch();
        else {
            if (!intake.getTranslateSwitch()) {
                shooter.setHoodSpeed(hoodUp.get() ? 0.1 : (hoodDown.get() ? -0.1 : 0.0));
            } else
                shooter.setHoodSpeed(0.0);
        }

        if(trigger.get() > 0.8) {
            SmartDashboard.putNumber("Shooter Equation", shooter.getShooterEquation());
            shouldShoot = shooter.bangShoot(shooter.getShooterEquation() + 700);
//            shooter.hoodRunToPos(shooter.getHoodEquation());

            intake.setTranslateSpeed(shouldShoot ? -1.0 : !intake.getTranslateSwitch() ? 0.0 : Constants.translateSpeed);
        } else {
            if(intake.getTranslateSwitch()) {
                intake.setTranslateSpeed(Constants.translateSpeed);
                shooter.setShooterSpeed(ControlMode.PercentOutput, 0.0);
            } else {
                intake.setTranslateSpeed(0.0);
                shooter.setShooterSpeed(ControlMode.PercentOutput, -0.3);
            }
        }
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
