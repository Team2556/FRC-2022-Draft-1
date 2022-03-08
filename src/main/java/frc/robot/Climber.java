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
    //Drive drive = new Drive();
    OI oi = new OI();
    // as of 3/8/22, yellow pistons work.
    private DoubleSolenoid yellowLeft = new DoubleSolenoid(10, PneumaticsModuleType.CTREPCM, 3,2);
    private DoubleSolenoid yellowRight = new DoubleSolenoid(10, PneumaticsModuleType.CTREPCM, 0,1);
    private DoubleSolenoid clampPiston = new DoubleSolenoid(11, PneumaticsModuleType.CTREPCM, 4,5); //double solenoid 4,5
    private TalonSRX winchMotor = new TalonSRX(9);








/*
3/7/22
In OI, winch motor needs a unique user input
The winch moves


*/




    public void climbInit()
{
    yellowLeft.set(Value.kReverse);
    yellowRight.set(Value.kReverse);
    clampPiston.set(Value.kReverse);
}





    

    int input = 0;
    public void climbDraft2(){
        double winchspeedF = 0.3;
        if (oi.Xbox1.getAButtonReleased())
        {
            input ++;
        }
        SmartDashboard.putNumber("Step", input);
        switch (input){
        case 1:
            yellowLeft.set(Value.kForward);
            yellowRight.set(Value.kForward);
            break;
        case 2:
            yellowLeft.set(Value.kReverse);
            yellowRight.set(Value.kReverse);
            break;
        case 3:
            clampPiston.set(Value.kForward);
            break;
        case 4:
            winchMotor.set(ControlMode.PercentOutput, -winchspeedF);
            break;
        case 5: 
            yellowLeft.set(Value.kForward);
            yellowRight.set(Value.kForward);
            winchMotor.set(ControlMode.PercentOutput, 0);
            break;
        case 6: 
            winchMotor.set(ControlMode.PercentOutput,winchspeedF);
            break;
        case 7:
            winchMotor.set(ControlMode.PercentOutput, 0);    
            yellowLeft.set(Value.kReverse);
            yellowRight.set(Value.kReverse);
            break;
        case 8: 
            clampPiston.set(Value.kReverse);
            break;
        case 9:
            winchMotor.set(ControlMode.PercentOutput, winchspeedF);
            break;
        //repeat 3-8
        case 10:
        clampPiston.set(Value.kForward);
        break;
    case 11:
        winchMotor.set(ControlMode.PercentOutput, -winchspeedF);
        break;
    case 12: 
        winchMotor.set(ControlMode.PercentOutput, 0);
        yellowLeft.set(Value.kForward);
        yellowRight.set(Value.kForward);
        break;
    case 13: 
        winchMotor.set(ControlMode.PercentOutput,-winchspeedF);
        break;
    case 14:
        winchMotor.set(ControlMode.PercentOutput, 0);
        yellowLeft.set(Value.kReverse);
        yellowRight.set(Value.kReverse);
        break;
    case 15: 
        clampPiston.set(Value.kReverse);
        break;
        }

    }

    public void climbDraft(){

       double winchspeedF = 0.3; //enter the speed which moves winch OUT
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
        yellowLeft.set(Value.kForward);
        yellowRight.set(Value.kForward);
    // Thread.sleep(1000);
    }

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 2
    yellowLeft.set(Value.kReverse);
    yellowRight.set(Value.kReverse);
    // Thread.sleep(1000);
    }

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 3
    clampPiston.set(Value.kForward);
    }

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 4
    winchMotor.set(ControlMode.PercentOutput, winchspeedF); //placeholder motor values and possible placeholder motor
    }

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 5
    yellowLeft.set(Value.kForward);
    yellowRight.set(Value.kForward);
    }

    // for(int i = 0; i < 500; i++) //placeholder value for time
    // { // STEP 6
    // clampPiston.set(k);
    // }

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 7. new step 6
    winchMotor.set(ControlMode.PercentOutput,-winchspeedF);
    }

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 8. new step 7
    yellowLeft.set(Value.kReverse);
    yellowRight.set(Value.kReverse);
    } 

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 9. new step 8
    clampPiston.set(Value.kReverse);
    }


//new repeat
for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 3
    clampPiston.set(Value.kForward);
    }

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 4
    winchMotor.set(ControlMode.PercentOutput, winchspeedF); //placeholder motor values and possible placeholder motor
    }

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 5
    yellowLeft.set(Value.kForward);
    yellowRight.set(Value.kForward);
    }

    // for(int i = 0; i < 500; i++) //placeholder value for time
    // { // STEP 6
    // clampPiston.set(k);
    // }

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 7. new step 6
    winchMotor.set(ControlMode.PercentOutput,-winchspeedF);
    }

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 8. new step 7
    yellowLeft.set(Value.kReverse);
    yellowRight.set(Value.kReverse);
    } 

    for(int i = 0; i < 500; i++) //placeholder value for time
    { // STEP 9. new step 8
    clampPiston.set(Value.kReverse);
    }

    // // the repeat. old
    // for(int i = 0; i < 500; i++) //placeholder value for time
    // { // STEP 4
    // winchMotor.set(ControlMode.PercentOutput, winchspeedF); //placeholder motor values and possible placeholder motor
    // }

    // for(int i = 0; i < 500; i++) //placeholder value for time
    // { // STEP 5
    // yellowPistons.set(Value.kForward);
    // }

    // for(int i = 0; i < 500; i++) //placeholder value for time
    // { // STEP 6
    // clampPiston.set(false);
    // }

    // for(int i = 0; i < 500; i++) //placeholder value for time
    // { // STEP 7
    // winchMotor.set(ControlMode.PercentOutput,-winchspeedF);
    // }

}

/*
starts with little hooks down winch in yellow in.

1. big hooks up, 
2. big hooks down
3.small hooks attach
4.winch motor goes out completely 
5.big hooks out, 
6.winch in 
7.small hooks open
8.big hooks in
9. winch in
10. repeat steps 3-7





*/













    public void winchMotor(){
        winchMotor.set(ControlMode.PercentOutput, oi.winchSpeed());
    } // temp b button and y button
    // y in b out. negative speed in, positive speed out.
    public void winchPistons(){
        boolean winchUp = oi.winchUp();
        if (yellowRight.get() == Value.kForward){
            SmartDashboard.putBoolean("Right Winch Pistons Down", true);
        }
        else if (yellowRight.get() == Value.kReverse){
            SmartDashboard.putBoolean("Right Winch Pistons Down", false);
        }

        
        if (winchUp){
            yellowLeft.set(Value.kForward);
            yellowRight.set(Value.kForward);
            }
        else {
            yellowLeft.set(Value.kReverse);
            yellowRight.set(Value.kReverse);
            }
        

        SmartDashboard.putBoolean("winchUp", winchUp);
    }


    public void clampPiston(){
        // false is the clamps not being clamped on
        // reverse is not being clamped on
        boolean clampOut = oi.clampOut();
        if (clampOut){
            clampPiston.set(Value.kForward);
        }
        else{
            clampPiston.set(Value.kReverse);
        }
        SmartDashboard.putBoolean("clampOut", clampOut);
    }



}
