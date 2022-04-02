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
        SmartDashboard.putNumber("Distance To Goal", limelight.limeLightDistanceInches());
        SmartDashboard.putBoolean("ShouldYouShoot", shooter.shouldShoot);
    }

    public void dashTest(){
        //SmartDashboard.putBoolean("translateSwitch", intake.translateSwitch.get());
        SmartDashboard.putNumber("pot", climber.pot.get());
        SmartDashboard.putNumber("avgPot", climber.analogPotentiometerAverageBounded());
        SmartDashboard.putNumber("boundedPot", climber.boundedPot);
        SmartDashboard.putBoolean("top switch", climber.topWinchSwitch.get());
        // SmartDashboard.putNumber("cargoVisionRotateValue", cargoVision.getRotationValue());


    }

    public void dashAuto(){
        SmartDashboard.putNumber("autoEncoder step", auto.step);
    }


}
