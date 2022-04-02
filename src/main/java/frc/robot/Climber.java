package frc.robot;
import java.io.OptionalDataException;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
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
    AnalogPotentiometer pot = new AnalogPotentiometer(3, Constants.potMax, 0);
    int climbStepperV2 = 0;


    //1 yellow motor 
    public void climbInit(){
        clampPiston.set(Value.kReverse); //clamp off
        climbStepperV2 = 0;
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
      // traversalClimbAutomation(oi.climbStep(), oi.resetClimber());
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






    public void traversalClimbAutomationV2(boolean climStepUp){
        boolean clampOff = false;
        boolean clampOn = true;
        boolean winchDiagonal = true;
        boolean winchUpright = false;
        int up = -1;
        int down = -up;
        SmartDashboard.putNumber("climbStepperV2", climbStepperV2);
        SmartDashboard.putNumber("climbMotorVoltage", yellowMotor.getBusVoltage());
        SmartDashboard.putNumber("climbMotorAmpOutput", yellowMotor.getOutputCurrent());
        if(climStepUp){
            climbStepperV2++;
        }
        if (!topWinchSwitch.get()) {
            yellowMotor(0, 0.5);
        }
        switch(climbStepperV2){
            case 1:
                yellowMotor.set(down);
                if(!topWinchSwitch.get()) {
                    yellowMotor.set(0);
                }
                clampPiston(clampOff);
            break;
            case 2:
                yellowMotor(up, 1); //motor goes up
            break;
            case 3:
                yellowMotor(down,1); //yellow hooks come down until limit switch hit
                if (!topWinchSwitch.get()) {
                    yellowMotor(0, 0.5);
                    climbStepperV2 = 4;
                }
            break;
            case 4:
                clampPiston(clampOn);//now hooked on mid rung
            break;
            case 5:
                yellowMotor(up,1); 
                if(analogPotentiometerAverageBounded() >= 50){
                    yellowMotor(0,1);
                    climbStepperV2 = 6;
                }
            break;
            case 6:
                winchPistons(winchDiagonal);
                yellowMotor(up, 1);
            break;
            case 7:
                winchPistons(winchUpright);
            break;
            case 8:
                yellowMotor(down,1); 
                if(analogPotentiometerAverageBounded() <= 280){
                    yellowMotor(0,1);
                    climbStepperV2 = 9;
                } 
                //needs to go perpedicular first
                // has to go down around to 225 and then it'll hooked onto the thing
            break;
            case 9:
                clampPiston(clampOff);
                yellowMotor(down, 1);
                climbStepperV2 = 10;
            break;
            case 10:
                yellowMotor(down,1); //yellow hooks come down until limit switch hit and then hooks on
                if (!topWinchSwitch.get()) {
                    yellowMotor(0, 0.5);
                    clampPiston(clampOn);
                    climbStepperV2 = 11;
                }
            break;
            case 11:
                yellowMotor(0,0.5);
            break;
            // on high rung between these steps
            case 12:
                yellowMotor(up, 1); 
                if(analogPotentiometerAverageBounded() >= 50){
                    yellowMotor(0,1);
                    climbStepperV2 = 13;
                }
            break;
            case 13:
                winchPistons(winchDiagonal); 
                yellowMotor(up, 1);
            break;
            case 14:
                winchPistons(winchUpright); // ready to try traversal
            break;
            case 15:
                yellowMotor(down,1); 
                if(analogPotentiometerAverageBounded() <= 280){
                    yellowMotor(0,1);
                    climbStepperV2 = 16;
                } 
            break;
            case 16:
                clampPiston(clampOff);
                yellowMotor(down,1);
                climbStepperV2 = 17;
            break;
            case 17: 
                if (!topWinchSwitch.get()) {
                    yellowMotor(0, 0.5);
                    climbStepperV2 = 18;
                }
                yellowMotor(down, 1); //climbs to traversal
            break;
            case 18:
                clampPiston(clampOn);
            break;
            case 900:
                yellowMotor(0, 0.5);
            break;
            default:
                yellowMotor(0, 1);
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




        double runningAvg = 0;
        double oldPotRead = 0;
        double boundedPot = 0;
    public double analogPotentiometerAverageBounded(){
        double newPotRead = pot.get();
        if(Math.abs(newPotRead) > Math.abs(Constants.potMax) || newPotRead < 0){
            newPotRead = oldPotRead;
        }
        else{
            oldPotRead = newPotRead;
            boundedPot = pot.get();
        }
        oldPotRead = newPotRead;
        runningAvg = runningAvg * 0.95 + newPotRead * 0.05;
        SmartDashboard.putNumber("oldPotRead", oldPotRead);
        SmartDashboard.putNumber("newPotRead", newPotRead);
        return runningAvg;
    }
}
