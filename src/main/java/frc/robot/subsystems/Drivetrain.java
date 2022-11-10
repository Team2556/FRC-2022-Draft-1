package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
    private static Drivetrain instance;

    private final CANSparkMax rFMotor = new CANSparkMax(Constants.rFMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax rRMotor = new CANSparkMax(Constants.rRMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax lFMotor = new CANSparkMax(Constants.lFMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax lRMotor = new CANSparkMax(Constants.lRMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
    // RelativeEncoder rFEncoder = new RelativeEncoder(SparkMaxRelativeEncoder.Type,Constants.rFMotorPort);
    RelativeEncoder lFEncoder = lFMotor.getEncoder();
    RelativeEncoder rFEncoder = rFMotor.getEncoder();
    // Limit switches
//    private final DigitalInput rFLimit = new DigitalInput(Constants.rFLimitPort);
//    private final DigitalInput rRLimit = new DigitalInput(Constants.rRLimitPort);
//    private final DigitalInput lFLimit = new DigitalInput(Constants.lFLimitPort);
//    private final DigitalInput lRLimit = new DigitalInput(Constants.lRLimitPort);

    private final PneumaticsControlModule PCML = new PneumaticsControlModule(Constants.PCMLPort); //left
    private final PneumaticsControlModule PCMR = new PneumaticsControlModule(Constants.PCMRPort); //right

    // Compressor compressor = new Compressor(Constants.PCMLPort, PneumaticsModuleType.CTREPCM);
    private final DoubleSolenoid frontDrivePistons = new DoubleSolenoid(Constants.PCMRPort, PneumaticsModuleType.CTREPCM, Constants.fDPForwardChannel, Constants.fDPReverseChannel);
    private final DoubleSolenoid rearDrivePistons = new DoubleSolenoid(Constants.PCMRPort, PneumaticsModuleType.CTREPCM, Constants.rDPForwardChannel, Constants.rDPReverseChannel);

    MecanumDrive driveMecanum = new MecanumDrive(lFMotor, lRMotor, rFMotor, rRMotor);

    public Drivetrain() {
        rFMotor.restoreFactoryDefaults();
        rRMotor.restoreFactoryDefaults();
        lFMotor.restoreFactoryDefaults();
        lRMotor.restoreFactoryDefaults();

        rFMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rRMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        lFMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        lRMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        rFMotor.setInverted(true);
        rRMotor.setInverted(true);
    }

    public void dropDrivePistons() {
        frontDrivePistons.set(DoubleSolenoid.Value.kForward);
        rearDrivePistons.set(DoubleSolenoid.Value.kForward);
    }

    public void liftDrivePistons() {
        frontDrivePistons.set(DoubleSolenoid.Value.kReverse);
        rearDrivePistons.set(DoubleSolenoid.Value.kReverse);
    }

    public void driveTeleop(double forward, double strafe, double turn) {
        driveMecanum.driveCartesian(forward, strafe, turn);
    }

    public static Drivetrain getInstance() {
        if(instance == null)
            instance = new Drivetrain();
        return instance;
    }
}
