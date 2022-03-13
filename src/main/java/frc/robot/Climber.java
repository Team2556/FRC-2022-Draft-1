package frc.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
//import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class Climber {
    //Drive drive = new Drive();
    OI oi = new OI();
    // as of 3/8/22, yellow pistons work.
    private DoubleSolenoid yellowLeft  = new DoubleSolenoid(Constants.PCMLPort, PneumaticsModuleType.CTREPCM, Constants.yLForwardChannel, Constants.yLReverseChannel);
    private DoubleSolenoid yellowRight = new DoubleSolenoid(Constants.PCMLPort, PneumaticsModuleType.CTREPCM, Constants.yRForwardChannel, Constants.yRReverseChannel);
    private DoubleSolenoid clampPiston = new DoubleSolenoid(Constants.PCMRPort, PneumaticsModuleType.CTREPCM, Constants.clampForwardChannel, Constants.clampReverseChannel); //double solenoid 4,5
    private TalonSRX winchMotor = new TalonSRX(Constants.winchMotorPort);
    private DutyCycleEncoder winchEncoder = new DutyCycleEncoder(Constants.winchEncoderPort);







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
    winchMotor.configFactoryDefault();
    winchMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
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

 








    public void resetWinchEncoder(){
        if(oi.Xbox1.getAButton()){
            winchMotor.setSelectedSensorPosition(0);
        }
    }
    public void winchEncoder(){
        // winchMotor.
         //winchEncoder.get();
        double y = winchMotor.getSelectedSensorVelocity();
         double x = winchMotor.getSelectedSensorPosition();
         SmartDashboard.putBoolean("winchBool", winchEncoder.isConnected());
         SmartDashboard.putNumber("winchEncoder", y);
         SmartDashboard.putNumber("winchEncoder2", x);
     }

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
