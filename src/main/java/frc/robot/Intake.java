package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Intake {
    OI oi = new OI();
    
    private CANSparkMax intake = new CANSparkMax(Constants.intakeMotorPort, MotorType.kBrushless);
    private CANSparkMax translate = new CANSparkMax(Constants.translateMotorPort, MotorType.kBrushless);
    DigitalInput translateSwitch = new DigitalInput(8);
    DoubleSolenoid intakeSolenoid = new DoubleSolenoid(Constants.PCMRPort, PneumaticsModuleType.CTREPCM, Constants.intakeForwardChannel, Constants.intakeReverseChannel);

    public void intakeInit() {
        intake.restoreFactoryDefaults();
    }

    public void intakeTeleop(){
        intakeMotor(oi.intakeSpeed());
        
        if(oi.Xbox1.getRightTriggerAxis() >=0.9 && translateSwitch.get() == true){
            translateMotor(-0.1);
        }
        else{
            translateMotor(oi.translateSpeed());
        }
        intakeSolenoid(oi.intakeSolenoid());
    }

    public void intakeMotor(double intakeSpeed){
        intake.set(intakeSpeed); 
    }
    public void translateMotor(double translateSpeed){
        translate.set(translateSpeed);
    }

    public void intakeSolenoid(boolean intakeSolenoidOut){
         if (intakeSolenoidOut){ //Takes in boolean and switches solenoid output based on it. 
            intakeSolenoid.set(Value.kForward);
            }
        else {
            intakeSolenoid.set(Value.kReverse);
            }
    }

    public void translateSwitchTest(){
        SmartDashboard.putBoolean("translateSwitch", translateSwitch.get());
    }
}
