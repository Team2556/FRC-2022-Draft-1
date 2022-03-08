package frc.robot;
// import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
// import edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.DigitalInput;



import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drive {



    //3/7/22
    //Tested drivebase and it all works except lRMotor. Pneumatics good. Commented out bc loud AF
    
    //Placeholder values for all motors
    OI oi = new OI();
    Limelight limeLight = new Limelight();

    private CANSparkMax rFMotor = new CANSparkMax(4, MotorType.kBrushless);
    private CANSparkMax rRMotor = new CANSparkMax(8, MotorType.kBrushless);
    private CANSparkMax lFMotor = new CANSparkMax(3, MotorType.kBrushless);
    private CANSparkMax lRMotor = new CANSparkMax(1, MotorType.kBrushless);
    //Checked these in rev


    DigitalInput rFLimit = new DigitalInput(0);
    DigitalInput rRLimit = new DigitalInput(1);
    DigitalInput lFLimit = new DigitalInput(3);
    DigitalInput lRLimit = new DigitalInput(2);
    //false means limit switch active

    

    

    PneumaticsControlModule PCM1 = new PneumaticsControlModule(10); //left
    PneumaticsControlModule PCM2 = new PneumaticsControlModule(11); //right
 
    Compressor compressor = new Compressor(11, PneumaticsModuleType.CTREPCM); //pcm 11
    // private DoubleSolenoid frontdrivePistons = new DoubleSolenoid(11, PneumaticsModuleType.CTREPCM, 0, 1);
    // private DoubleSolenoid reardrivePistons = new DoubleSolenoid(11, PneumaticsModuleType.CTREPCM, 3, 2);
    // private Solenoid rWSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 3);
    // private Solenoid lWSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 4);

    //drive objects
    //MecanumDrive driveMecanum = new MecanumDrive(lFMotor, lRMotor, rFMotor, rRMotor);
    MotorControllerGroup leftSide = new MotorControllerGroup(lRMotor, lFMotor);
    MotorControllerGroup rightSide = new MotorControllerGroup(rRMotor, rFMotor); 
    DifferentialDrive differentialDrive = new DifferentialDrive(leftSide, rightSide);











    






    public void testDrivebase(){
        differentialDrive.tankDrive(0.2, 0.2);
    }



    public void drivebaseInit(){
        rFMotor.setInverted(true);
        rRMotor.setInverted(true);
        SmartDashboard.putString("Right motors reversed", "Yes");
    }

    // public void ethanisDumb(){
    //     lRMotor.set(oi.shit());
    // } //I hate ethan lindsay >:(
   




    //limit switches for drivebase tested and work. logic works.
    
    public void dualDrivebase(){
        boolean dropped = oi.dropped(); 
        SmartDashboard.putBoolean("dropped", dropped);
        
        // double mForward = oi.mForward(); 
        // double mStrafe = oi.mStrafe(); 
        // double mRotate = oi.mRotate();

        double tLeft = -oi.tLeft();
        double tRight = -oi.tRight();
        // double tLeft = 0;
        // double tRight = 0;
        //Values taken from the OI to be fed into this program. 

        // if (frontdrivePistons.get() == Value.kForward){ //Puts piston data to the smart dashboard
        //     SmartDashboard.putBoolean("Front Drive Pistons Down", true);
        // }
        // else if (frontdrivePistons.get() == Value.kReverse){
        //     SmartDashboard.putBoolean("Front Drive Pistons Down", false);
        // }

        
        // if (dropped){ //Takes in boolean and switches solenoid output based on it. 
        //     frontdrivePistons.set(Value.kForward);
        //     reardrivePistons.set(Value.kForward);
        //     }
        // else {
        //     frontdrivePistons.set(Value.kReverse);
        //     reardrivePistons.set(Value.kReverse);
        //     }
        

        SmartDashboard.putBoolean("dropped", dropped); 
        
    
        if (dropped){ //Takes in boolean and switches drive output based on it. 
            differentialDrive.tankDrive(tLeft, tRight);
            SmartDashboard.putString("Ethan is a fucking dumbass", "not mecanum and dropped");
        }
        else if (dropped == false && lFLimit.get() == false && lRLimit.get() == false && rFLimit.get() == false && rRLimit.get() == false){
            // If solenoids don't drop the motors and all the limits are switched. False means switch is clicked
            //driveMecanum.driveCartesian(mForward, mStrafe, mRotate);
            SmartDashboard.putString("Ethan is a fucking dumbass", "Mecanum go");
        }
        else{
            differentialDrive.tankDrive(0, 0);
            SmartDashboard.putString("Ethan is a fucking dumbass", "Not mecanum");
        }

        
    
    }





    public void limelightDrive(){
        //changes the way the robot turns based on if the robot is in mecanum or arcade mode
        if(oi.limeLightTurn)         {
            if(oi.dropped){
                differentialDrive.arcadeDrive(0, limeLight.PIDC());
            }
            else if(oi.dropped == false){
                //driveMecanum.driveCartesian(0, 0, limeLight.PIDC(), 0);
                SmartDashboard.putString("No mecanum until we proof check everything", "Dylan");
            }
        }
        
    }




















    public void triDrivebase(){
        //This if statement decides if the limelight turn button is clicked and changes how its driven accordingly.
        if(oi.limeLightTurn()){
            limelightDrive();
        }
        else{
            dualDrivebase();
        }
    }





























    public void backleftmotortest(){
        lRMotor.set(0.1);
        rRMotor.set(0.1);
    }

    public void limitSwitchTest(){
        SmartDashboard.putBoolean("LF Switch", lFLimit.get());
        SmartDashboard.putBoolean("LR Switch", lRLimit.get());
        SmartDashboard.putBoolean("RF Switch", rFLimit.get());
        SmartDashboard.putBoolean("RR Switch", rRLimit.get());

    }


}



















