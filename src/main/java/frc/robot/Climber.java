package frc.robot;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber {
    OI oi = new OI();
    private DoubleSolenoid yellowLeft  = new DoubleSolenoid(Constants.PCMLPort, PneumaticsModuleType.CTREPCM, Constants.yLForwardChannel, Constants.yLReverseChannel);
    private DoubleSolenoid yellowRight = new DoubleSolenoid(Constants.PCMLPort, PneumaticsModuleType.CTREPCM, Constants.yRForwardChannel, Constants.yRReverseChannel);
    private DoubleSolenoid clampPiston = new DoubleSolenoid(Constants.PCMRPort, PneumaticsModuleType.CTREPCM, Constants.clampForwardChannel, Constants.clampReverseChannel); //double solenoid 4,5
    //private DutyCycleEncoder winchEncoder = new DutyCycleEncoder(Constants.winchEncoderPort); Not needed anymore because there's limit switches on the yellow motors at top and bottom
    private CANSparkMax yellowMotor = new CANSparkMax(Constants.yellowMotorPort, MotorType.kBrushless);
    RelativeEncoder yellowEncoder = yellowMotor.getEncoder();

  //  private SparkMaxLimitSwitch limitSwitch = yellowMotor.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyClosed);
    int climbStepper = 10;    



    //1 yellow motor 
    public void climbInit(){
        // yellowLeft.set(Value.kReverse);
        // yellowRight.set(Value.kReverse);
        clampPiston.set(Value.kReverse); //clamp off
        climbStepper = 0;
        yellowEncoder.setPosition(0);
        winchPistons(false); //winch perpendicular


    }

    public void climbTeleop(){
        winchPistons(oi.winchUp());
        clampPiston(oi.clampOut());

        yellowMotor(oi.winchSpeed(), 5);
        //yellowMotor(0.5, 1);
       // yellowMotor.set(0.5);
        // hook();
        SmartDashboard.putNumber("yellowEncoder", yellowEncoder.getPosition());


       // traversalClimbAutomation(oi.climbStep());
    }

    // public void hook() {
    //     if (limitSwitch.isPressed()) {
    //         clampPiston.set(Value.kForward);
    //     }
    // }




    // public void winchMotor(double winchSpeed){
    //     winchMotor.set(ControlMode.PercentOutput, winchSpeed);
    //     } 



    public void winchPistons(boolean winchUp){
             
        if (winchUp){
            yellowLeft.set(Value.kForward);
            yellowRight.set(Value.kForward);
            }
        else {
            yellowLeft.set(Value.kReverse);
            yellowRight.set(Value.kReverse);
            }
    }

    public void yellowMotor(double yellowSpeed, double rate){
        yellowMotor.set(yellowSpeed);
        yellowMotor.setClosedLoopRampRate(rate);
    }
    public void yellowMotorRunToPos(double targetPos){
        double difference = targetPos - yellowEncoder.getPosition();
        double kp = 0.000001;
        double error = difference * kp;
        double percentOutput = 0;
    }
           
    public void clampPiston(boolean clampOn){
        // false is the clamps not being clamped on
        // reverse is not being clamped on
        if (clampOn){
            clampPiston.set(Value.kForward);
        }
        else{
            clampPiston.set(Value.kReverse);
        }
    }




    public void traversalClimbAutomation(boolean climstep){
        boolean cone = true;
        boolean ctwo = !cone;
        boolean wone = true;
        boolean wtwo = !wone;
        SmartDashboard.putNumber("climbStepper", climbStepper);
        if(climstep){
            climbStepper+= 5;
        }
        switch(climbStepper){
            case 10:
            yellowMotor(-1, 5); //up
            break;
            case 20:
            yellowMotor(1, 5); //down
            break;
            case 30: 
            clampPiston(cone); //clamped on
            break;
            case 40:
            yellowMotor(-1, 5); //goes up only a little bit
            break;
            case 50:
            winchPistons(wone); //winch diagonal
            break;
            case 60: 
            yellowMotor(-1,5); //goes up 
            break;
            case 70:
            winchPistons(wtwo); //winch perpendicular
            break;
            case 80:
            yellowMotor(1, 5); //winch down
            break;
            case 90:
            clampPiston(ctwo); //clamps off
            break;
            case 100:
            clampPiston(cone); //yellow motor needs to continue and then clamps on so they don't catch
            yellowMotor(1,5);
            break;
            case 110:
            yellowMotor(1, 5);//continues down
            break;
            case 120:
            clampPiston(ctwo); //clamps out to be ready for clamping on
            break;
            case 130:
            yellowMotor(1, 5); //fully pulls up to thing
            break;
            case 140:
            clampPiston(cone); //clamps on
            climbStepper = 40;
            break;
        default:
            yellowMotor(0,0);
            break;
        }



    }
}
