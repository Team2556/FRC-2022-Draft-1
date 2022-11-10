package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

    private static Intake instance;

    private final CANSparkMax intake = new CANSparkMax(Constants.intakeMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax translate = new CANSparkMax(Constants.translateMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final DigitalInput translateSwitch = new DigitalInput(8);
    private final DoubleSolenoid intakeSolenoid = new DoubleSolenoid(Constants.PCMRPort, PneumaticsModuleType.CTREPCM, Constants.intakeForwardChannel, Constants.intakeReverseChannel);

    private Intake() {
        intake.restoreFactoryDefaults();
        translate.restoreFactoryDefaults();
        liftIntakeSolenoid();
    }

    public void setIntakeSpeed(double intakeSpeed){
        intake.set(intakeSpeed);
    }

    public void setTranslateSpeed(double translateSpeed){
        translate.set(translateSpeed);
    }

    public double getIntakeCurrent() {
        return intake.getOutputCurrent();
    }
    //ToDo make sure this is correct:
    // True means it is not pressed, false means it is pressed
    public boolean getTranslateSwitch() {
        return translateSwitch.get();
    }

    public void releaseIntakeSolenoid() {
        intakeSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void liftIntakeSolenoid() {
        intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public static Intake getInstance() {
        if(instance == null)
            instance = new Intake();
        return instance;
    }
}