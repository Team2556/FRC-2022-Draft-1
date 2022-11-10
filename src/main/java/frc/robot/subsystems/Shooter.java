package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxLimitSwitch;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import java.util.HashMap;

public class Shooter extends SubsystemBase {
    private static Shooter instance;

    private final WPI_TalonFX shooterMotor = new WPI_TalonFX(Constants.shooterMotorPort);
    private final CANSparkMax hoodMotor = new CANSparkMax(Constants.hoodMotorPort, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final RelativeEncoder hoodEncoder = hoodMotor.getEncoder();
    private final SparkMaxLimitSwitch hoodSwitch = hoodMotor.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
    private boolean hoodReset;
    private final HashMap<Integer, ShooterDataAtDistance> shooterData;

    private Shooter() {
        shooterData = new HashMap<>();
        shooterData.put(75, new ShooterDataAtDistance(12878.32, 2.17));
        shooterData.put(50, new ShooterDataAtDistance(12128.0, 9.11));
        shooterData.put(100, new ShooterDataAtDistance(13628.0, -4.76));


        hoodMotor.restoreFactoryDefaults();
        hoodMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        shooterMotor.configFactoryDefault();
        shooterMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 10);
        shooterMotor.setNeutralMode(NeutralMode.Coast);
        hoodReset = false;

        shooterMotor.configNeutralDeadband(0.001);

        /* Config sensor used for Primary PID [Velocity] */
        shooterMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 30);


        /* Config the peak and nominal outputs */
        shooterMotor.configNominalOutputForward(0, 30);
        shooterMotor.configNominalOutputReverse(0, 30);
        shooterMotor.configPeakOutputForward(1, 30);
        shooterMotor.configPeakOutputReverse(-1, 30);

        /* Config the Velocity closed loop gains in slot0 */
        shooterMotor.config_kF(0, 1023.0/20660.0, 30);
        shooterMotor.config_kP(0, 0.1, 30);
        shooterMotor.config_kI(0, 0.001, 30);
        shooterMotor.config_kD(0, 5, 30);
    }
    double lastOutput = 0;
    public boolean shootWithRamp(double targetSpeed) {
        double difference, error, percentOutput, actSpeed;
        actSpeed = shooterMotor.getSelectedSensorVelocity();
        difference  = Math.abs(targetSpeed) - Math.abs(actSpeed);
        error = difference * 0.00125;
        lastOutput =+ error;
        percentOutput = lastOutput;
        if(percentOutput > 1){
            percentOutput = 1;
        }
        if (targetSpeed == 0){
            percentOutput = 0;
            difference = 0;
        }
        SmartDashboard.putNumber("Shooter Actual Speed", actSpeed);
        SmartDashboard.putNumber("Shooter Speed", -percentOutput);
        shooterMotor.set(ControlMode.PercentOutput, -percentOutput);

        if(Math.abs(difference)<300){
            return targetSpeed != 0;
        }
        return false;
    }

    public boolean bangShoot(double targetRPM) {
        double actSpeed = shooterMotor.getSelectedSensorVelocity();
        double diff =  Math.abs(targetRPM) - Math.abs(actSpeed);
        setShooterSpeed(ControlMode.Velocity, -targetRPM);
        return diff < 100;
    }

    public void hoodRunToPos(double targetPos){
        double currentPos = hoodEncoder.getPosition();
        double difference = currentPos - targetPos;
        double k = 0.005;
        double sign = -1;
        double hoodSoftLimit = -50;
        if (targetPos < hoodSoftLimit || currentPos < hoodSoftLimit) {
            hoodMotor.set(0);
        } else {
            hoodMotor.set(currentPos < hoodSoftLimit ? 0 : difference * k *  sign);
        }
    }

    public void hoodResetBySwitch() {
        double currentPos = hoodEncoder.getPosition();
        double hoodSoftLimit = -50;
        hoodMotor.set(currentPos < hoodSoftLimit ? 0 : 0.1);
        if (hoodSwitch.isPressed()) {
            hoodMotor.set(0);
            hoodEncoder.setPosition(0);
            hoodReset = true;
        }
    }

    public double getShooterEquation(){ //x=31.6513 c=10628.3234
        return 30 * Limelight.getLimelight().getDistance() + 10628.3234;
    }

    public double getHoodEquation(){
        return -0.2773 * Limelight.getLimelight().getDistance() + 22.9709;
    }

    public void setHoodSpeed(double speed) {
        hoodMotor.set(speed);
    }

    public void setShooterSpeed(ControlMode mode, double speed) {
        shooterMotor.set(mode, speed);
    }

    public double getShooterVelocity() {
        return shooterMotor.getSelectedSensorVelocity();
    }

    public boolean getHoodSwitchPressed() {
        return hoodSwitch.isPressed();
    }

    public double getHoodEncoderValue() {
        return hoodEncoder.getPosition();
    }

    public void setHoodEncoderValue(double x) {
        hoodEncoder.setPosition(x);
    }

    public boolean isHoodReset() {
        return hoodReset;
    }

    public void setHoodReset(boolean hoodReset) {
        this.hoodReset = hoodReset;
    }

    public static Shooter getInstance() {
        if(instance == null)
            instance = new Shooter();
        return instance;
    }

    static class ShooterDataAtDistance {
        private final double shooterSpeed;
        private final double hoodAngle;

        ShooterDataAtDistance(double shooterSpeed, double hoodAngle) {
            this.shooterSpeed = shooterSpeed;
            this.hoodAngle = hoodAngle;
        }

        public double getShooterSpeed() {
            return shooterSpeed;
        }

        public double getHoodAngle() {
            return hoodAngle;
        }
    }
}