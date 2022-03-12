package frc.robot;

// import javax.print.CancelablePrintJob;

import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {
    OI oi = new OI();
    private CANSparkMax intake = new CANSparkMax(Constants.intakeMotorPort, MotorType.kBrushless);
    private CANSparkMax translate = new CANSparkMax(Constants.translateMotorPort, MotorType.kBrushless);

    DoubleSolenoid intakeSolenoid = new DoubleSolenoid(Constants.PCMRPort, PneumaticsModuleType.CTREPCM, Constants.intakeForwardChannel, Constants.intakeReverseChannel);
/*
3/7/22
Tested intakeMotor. Right motor is powered on, but it is not moving even though 
smart dashboard says it is

NEED TO CHANGE OI.INTAKESOLENOID BACK TO XBOX2
Intake solenoid moves up and down. 
All pneumatics commented out for now.


NEED TO CHANGE TRANSLATEMOTOR IN OI BACK TO XBOX2   
Translate motor works
*/

    public void intakeMotor(){
        intake.set(oi.intakeSpeed());
        SmartDashboard.putNumber("Intake", intake.get());
    }
    public void translateMotor(){
        translate.set(oi.translateSpeed());
    }



    public void intakeSolenoid(){
        //intakeSolenoid.set(oi.intakeSolenoid());
         if (oi.intakeSolenoid()){ //Takes in boolean and switches solenoid output based on it. 
            intakeSolenoid.set(Value.kForward);
            }
        else {
            intakeSolenoid.set(Value.kReverse);
            }
        SmartDashboard.putBoolean("intakeSolenoid", oi.intakeSolenoid());

    }
}
