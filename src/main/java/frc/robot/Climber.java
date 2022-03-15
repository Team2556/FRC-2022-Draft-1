package frc.robot;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DutyCycleEncoder;

import java.util.concurrent.CancellationException;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class Climber {
    OI oi = new OI();
    private DoubleSolenoid yellowLeft  = new DoubleSolenoid(Constants.PCMLPort, PneumaticsModuleType.CTREPCM, Constants.yLForwardChannel, Constants.yLReverseChannel);
    private DoubleSolenoid yellowRight = new DoubleSolenoid(Constants.PCMLPort, PneumaticsModuleType.CTREPCM, Constants.yRForwardChannel, Constants.yRReverseChannel);
    private DoubleSolenoid clampPiston = new DoubleSolenoid(Constants.PCMRPort, PneumaticsModuleType.CTREPCM, Constants.clampForwardChannel, Constants.clampReverseChannel); //double solenoid 4,5
    private TalonSRX winchMotor = new TalonSRX(Constants.winchMotorPort);
    private DutyCycleEncoder winchEncoder = new DutyCycleEncoder(Constants.winchEncoderPort);
    private CANSparkMax yellowMotor = new CANSparkMax(Constants.yellowMotorPort, MotorType.kBrushless);
    RelativeEncoder yellowEncoder = yellowMotor.getEncoder();
    public void climbInit(){
        yellowLeft.set(Value.kReverse);
        yellowRight.set(Value.kReverse);
        clampPiston.set(Value.kReverse);
        winchMotor.configFactoryDefault();
        winchMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        winchMotor.setSelectedSensorPosition(0);
    }

    public void climbTeleop(){
        winchMotor(oi.winchSpeed());
        winchPistons(oi.winchUp());
        clampPiston(oi.clampOut());
    }






    public void winchMotor(double winchSpeed){
        winchMotor.set(ControlMode.PercentOutput, winchSpeed);
        } // temp b button and y button
        // y in b out. negative speed in, positive speed out.



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

    public void yellowMotor(double yellowSpeed){

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
}
