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
        SmartDashboard.putNumber("climbStepper", climber.climbStepperV2);
        SmartDashboard.putNumber("Distance To Goal", limelight.limeLightDistanceInches());
        SmartDashboard.putNumber("avg pot", climber.analogPotentiometerAverageBounded());
    }

    public void dashTest(){
        //SmartDashboard.putNumber("encoer value", drive.lFEncoder.getPosition());
        SmartDashboard.putNumber("limelightDistanceInInches", limelight.limeLightDistanceInches());
    }

    public void dashAuto(){
       // SmartDashboard.putNumber("autoEncoder step", auto.step);
    }


}
