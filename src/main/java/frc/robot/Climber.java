package frc.robot;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;

public class Climber {
    OI oi = new OI();
    private DoubleSolenoid yellowLeft  = new DoubleSolenoid(Constants.PCMLPort, PneumaticsModuleType.CTREPCM, Constants.yLForwardChannel, Constants.yLReverseChannel);
    private DoubleSolenoid yellowRight = new DoubleSolenoid(Constants.PCMLPort, PneumaticsModuleType.CTREPCM, Constants.yRForwardChannel, Constants.yRReverseChannel);
    private DoubleSolenoid clampPiston = new DoubleSolenoid(Constants.PCMRPort, PneumaticsModuleType.CTREPCM, Constants.clampForwardChannel, Constants.clampReverseChannel); //double solenoid 4,5
    //private DutyCycleEncoder winchEncoder = new DutyCycleEncoder(Constants.winchEncoderPort); Not needed anymore because there's limit switches on the yellow motors at top and bottom
    private CANSparkMax yellowMotor = new CANSparkMax(Constants.yellowMotorPort, MotorType.kBrushless);
    RelativeEncoder yellowEncoder = yellowMotor.getEncoder();
    SparkMaxPIDController yellowPID = yellowMotor.getPIDController();
    DigitalInput topWinchSwitch = new DigitalInput(7);
    //DigitalInput bottomWinchSwitch = new DigitalInput();

  //  private SparkMaxLimitSwitch limitSwitch = yellowMotor.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyClosed);
    int climbStepper = 10;    



    //1 yellow motor 
    public void climbInit(){
        // yellowLeft.set(Value.kReverse);
        // yellowRight.set(Value.kReverse);
        clampPiston.set(Value.kReverse); //clamp off
        climbStepper = 0;
        
        if (topWinchSwitch.get()) {
            yellowMotor.set(0.5);
        }
        else{
            yellowMotor.set(0);
            yellowEncoder.setPosition(0);
        }
    
        winchPistons(false); //winch perpendicular
        yellowMotor.setIdleMode(IdleMode.kBrake);
        yellowPID.setP(0.05);
        yellowPID.setI(0.0);
        yellowPID.setD(0);
        yellowPID.setIZone(0);
        yellowPID.setFF(0);
        yellowPID.setOutputRange(-1,1);




    }

    public void climbTeleop(){
        // winchPistons(oi.winchUp());
        clampPiston(oi.clampOut());
       // yellowMotor(oi.winchSpeed(), 5);
        
       // yellowMotor(oi.yellowMotorSpeed(), 1);
    //    yellowMotorRunToPos(oi.winchPos());
    //    if (!topWinchSwitch.get()) {
    //     clampPiston(true);
    //    }
    //    yellowMotorPCalibration();
       SmartDashboard.putBoolean("top switch", topWinchSwitch.get());
    //   SmartDashboard.putBoolean("bottom switch", bottomWinchSwitch.get());

        // if (oi.climbStep()) {
        //     yellowMotorRunToPos(Constants.maxYellowEncoderValue);
        // }
    


       // yellowMotorRunBySwitch(oi.winchSpeed());
        SmartDashboard.putNumber("yellowEncoder", yellowEncoder.getPosition());


       traversalClimbAutomation(oi.climbStep());
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
    // public void yellowMotorRunToPos(double targetPos){
    //     double difference = targetPos - yellowEncoder.getPosition();
    //     double kp = 0.000001;
    //     double error = difference * kp;
    //     double percentOutput = 0;
    // }
    public void yellowMotorRunToPos(double targetPos){
        yellowPID.setReference(targetPos, ControlType.kPosition);
    }
    
    public void yellowMotorRunBySwitch(double speed){
        // if(topWinchSwitch.get() ==false || bottomWinchSwitch .get()== false){
        //     yellowMotor.set(0);
        // }
        // else{
        //     yellowMotor.set(speed);
        // }
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
            case 5:
                yellowMotor.set(0.25);
                if(!topWinchSwitch.get()){
                yellowMotor.set(0);
                yellowEncoder.setPosition(0);
                climbStepper = 10;
            }
            break;
            case 10:
            // yellowMotor(-1, 5); //up
            yellowMotorRunToPos(-80);
            clampPiston(false);
            break;
            case 20:
            //yellowMotorRunToPos(90); //down
            yellowMotor(0.75, 1);
            if (!topWinchSwitch.get()) {
                climbStepper = 30;
            }
            break;
            case 30: 
            yellowMotor(0.75, 1);
            clampPiston(cone); //clamped on
            break;
            case 35:
            clampPiston(cone);
            yellowMotor(0, 1);
            break;
            case 40:
            clampPiston(cone); //clamped on
            yellowMotorRunToPos(-40);
            break;
             case 45:
             winchPistons(wone); //winch diagonal
             break;
             case 50: 
             yellowMotorRunToPos(-80); //goes up 
             break;
             case 55:
             winchPistons(wtwo); //winch perpendicular
             break;
            case 60:
             yellowMotor(.5, 1); //winch down
            break;
            case 65:
             clampPiston(ctwo); //clamps off
             climbStepper = 20;
            break;
            // case 100:
            // clampPiston(cone); //yellow motor needs to continue and then clamps on so they don't catch
            // yellowMotor(1,5);
            // break;
            // case 110:
            // yellowMotor(1, 5);//continues down
            // break;
            // case 120:
            // clampPiston(ctwo); //clamps out to be ready for clamping on
            // break;
            // case 130:
            // yellowMotor(1, 5); //fully pulls up to thing
            // break;
            // case 140:
            // clampPiston(cone); //clamps on
            // climbStepper = 40;
            // break;
        default:
            yellowMotor(0,0);
            break;
        }


    
    }
    public void yellowMotorPCalibration(){
        if(oi.winchPosBool){
            yellowPID.setP(0.05);
        }
        else{
            yellowPID.setP(0.1);
        }
    }
}
