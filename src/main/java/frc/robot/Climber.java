package frc.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
//import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber {
    Drive drive = new Drive();
    OI oi = new OI();

    private DoubleSolenoid yellowPistons = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0,1);
    private Solenoid clampPiston = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    private TalonSRX winchMotor = new TalonSRX(5);



    public void climbDraft(){

       int winchspeedF = 0; //enter the speed which moves winch OUT
        /*yellow up 1
        yellow down 2

            clampon    3
            [winchout    4
            yellowup    5
            clampoff    6
            winchin     7]
            yellowdown  8
            clampoff    9
            repeat 4-7 once more
    */

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 1
    yellowPistons.set(Value.kForward);
    // Thread.sleep(1000);
    }

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 2
    yellowPistons.set(Value.kReverse);
    // Thread.sleep(1000);
    }

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 3
    clampPiston.set(true);
    }

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 4
    winchMotor.set(ControlMode.PercentOutput, winchspeedF); //placeholder motor values and possible placeholder motor
    }

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 5
    yellowPistons.set(Value.kForward);
    }

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 6
    clampPiston.set(false);
    }

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 7
    winchMotor.set(ControlMode.PercentOutput,-winchspeedF);
    }

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 8
    yellowPistons.set(Value.kReverse);
    } 

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 9
    clampPiston.set(false);
    }





    // the repeat
    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 4
    winchMotor.set(ControlMode.PercentOutput, winchspeedF); //placeholder motor values and possible placeholder motor
    }

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 5
    yellowPistons.set(Value.kForward);
    }

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 6
    clampPiston.set(false);
    }

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 7
    winchMotor.set(ControlMode.PercentOutput,-winchspeedF);
    }

}















    public void winchMotor(){
        winchMotor.set(ControlMode.PercentOutput, oi.winchSpeed());
    }

    public void winchPistons(){
        boolean winchUp = oi.winchUp();
        if (yellowPistons.get() == Value.kForward){
            SmartDashboard.putBoolean("Winch Pistons Down", true);
        }
        else if (yellowPistons.get() == Value.kReverse){
            SmartDashboard.putBoolean("Winch Pistons Down", false);
        }

        
        if (winchUp){
            yellowPistons.set(Value.kForward);
            }
        else {
            yellowPistons.set(Value.kReverse);
            }
        

        SmartDashboard.putBoolean("winchUp", winchUp);
    }

}
