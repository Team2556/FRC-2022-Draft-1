package frc.robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SDash {
    Drive drive;
    Limelight limelight;
    Intake intake;
    Shooter shooter;
    CargoVision cargoVision;
    Climber climber;
    Auto auto;
    //OI and Drive continue to use independent SmartDashboard commands for now

    public SDash(Drive drv, Intake in, Shooter shot, Limelight lim, Climber clim, Auto aut, CargoVision cVis) {
        drive = drv;
        intake = in;
        shooter = shot;
        limelight = lim;
        climber = clim;
        auto = aut;
     //   cargoVision = cVis;
    }



    public void dashTele(){
        SmartDashboard.putBoolean("top switch", climber.topWinchSwitch.get());
        SmartDashboard.putNumber("yellowEncoder", climber.yellowEncoder.getPosition());
        SmartDashboard.putNumber("climbStepper", climber.climbStepper);
        SmartDashboard.putNumber("Distance To Goal", limelight.limeLightDistanceInches());
        SmartDashboard.putBoolean("ShouldYouShoot", shooter.shouldShoot);
    }

    public void dashTest(){
        SmartDashboard.putBoolean("translateSwitch", intake.translateSwitch.get());
    }

    public void dashAuto(){
        SmartDashboard.putNumber("autoEncoder step", auto.step);
    }


}
