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
    private CANSparkMax yellowMotor = new CANSparkMax(Constants.yellowMotorPort, MotorType.kBrushless);
    RelativeEncoder yellowEncoder = yellowMotor.getEncoder();
    SparkMaxPIDController yellowPID = yellowMotor.getPIDController();
    DigitalInput topWinchSwitch = new DigitalInput(7);

    int climbStepper = 10;    

    //1 yellow motor 
    public void climbInit(){
        clampPiston.set(Value.kReverse); //clamp off
        climbStepper = 0;
        
        if (topWinchSwitch.get()) {
            yellowMotor.set(0.5);
        }
        else {
            yellowMotor.set(0);
            yellowEncoder.setPosition(0);
        }
    
        winchPistons(false); // winch perpendicular
        yellowMotor.setIdleMode(IdleMode.kBrake);
        yellowPID.setP(0.05);
        yellowPID.setI(0);
        yellowPID.setD(0.0005);
        yellowPID.setIZone(0);
        yellowPID.setFF(0);
        yellowPID.setOutputRange(-1,1);
    }

    public void climbTeleop(){
        clampPiston(oi.clampOut());
        SmartDashboard.putBoolean("top switch", topWinchSwitch.get());
        SmartDashboard.putNumber("yellowEncoder", yellowEncoder.getPosition());
       traversalClimbAutomation(oi.climbStep(), oi.resetClimber());
    }

    public void winchPistons(boolean winchUp){  
        if (winchUp) {
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
        yellowPID.setReference(targetPos, ControlType.kPosition);
    }

    public void clampPiston(boolean clampOn){
        // false is the clamps not being clamped on
        // reverse is not being clamped on
        if (clampOn) {
            clampPiston.set(Value.kForward);
        }
        else {
            clampPiston.set(Value.kReverse);
        }
    }

    public void traversalClimbAutomation(boolean climstep, boolean climbreset){
        boolean cone = true;
        boolean ctwo = false;
        boolean wone = true;
        boolean wtwo = !wone;
        SmartDashboard.putNumber("climbStepper", climbStepper);
        if(climstep) {
            climbStepper+= 5;
        }
        else if(climbreset) {
            climbStepper = 10;
        }
        switch(climbStepper){
            case 5:
                yellowMotor.set(1);
                if(!topWinchSwitch.get()) {
                    yellowMotor.set(0);
                    yellowEncoder.setPosition(0);
                    climbStepper = 10;
                }
            break;
            case 10:
            yellowMotorRunToPos(-700); // up
            clampPiston(false);
            break;
            case 20:
            yellowMotor(1, 1);
            if (!topWinchSwitch.get()) {
                yellowMotor(0, 0.5);
                climbStepper = 30;
            }
            break;
            case 30: 
            yellowMotor(0, 1);
            clampPiston(cone); //clamped on
            break;
            case 35:
            clampPiston(cone);
            yellowMotor(0, 1);
            break;
            case 40:
            clampPiston(cone); //clamped on
            yellowMotorRunToPos(-300);
            break;
            case 45:
            clampPiston(cone); //clamped on
            winchPistons(wone); //winch diagonal
            break;
            case 50: 
            clampPiston(cone); //clamped on
            yellowMotorRunToPos(-700); //goes up 
            break;
            case 55:
            clampPiston(cone); //clamped on
            yellowMotorRunToPos(-300); //goes up 
            winchPistons(wtwo); //winch perpendicular
            if (yellowEncoder.getPosition() > -650) {
                clampPiston(ctwo); //clamped off
            }
            break;
            case 60:
            clampPiston(ctwo);
            break;
            case 65:
            yellowMotor(1, 1);
            if (!topWinchSwitch.get()) {
                yellowMotor(0, 1);
                climbStepper = 70;
            }
            break;
            case 70:
            clampPiston(cone);
            break;
            case 75:
            clampPiston(ctwo); //clamps off
            climbStepper = 20;
            break;
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
