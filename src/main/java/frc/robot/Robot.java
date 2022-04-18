// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
  Drive drive = new Drive();
  Intake intake = new Intake();
  Shooter shooter = new Shooter(intake);
  Climber climber = new Climber();
  Limelight limelight = new Limelight();
  CargoVision cargoVision = new CargoVision();
  OI oi = new OI();
  Auto auto = new Auto(drive, intake, shooter, limelight, cargoVision);
  SDash smartDash = new SDash(drive, intake, shooter, limelight, climber, auto, cargoVision);
  private static final String kRedAuto = "Red Auto";
  private static final String kBlueAuto = "Blue Auto";
  private String m_autoSelected;
  public final SendableChooser<String> m_chooser = new SendableChooser<>();
  

  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Red Auto", kRedAuto);
    m_chooser.addOption("Blue Auto", kBlueAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    // if (m_chooser.getSelected() == kRedAuto) {
    //   auto.autoInit(Constants.redAlliance);
    // }
    // else if (m_chooser.getSelected() == kBlueAuto) {
    //   auto.autoInit(Constants.blueAlliance);
    // }
    drive.drivebaseInit(); 
    shooter.shooterInit();
    intake.intakeInit();
    climber.climbInit();
    // drive.lFEncoder.setPosition(0);
    shooter.hoodReset = false;
  }

 
  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    auto.autoInit(1);
    m_autoSelected = m_chooser.getSelected();
    m_autoSelected = SmartDashboard.getString("Auto Selector", kRedAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    shooter.hoodReset = false;
    shooter.timesShot = 0;
  }

  @Override
  public void autonomousPeriodic() {
  // auto.autoEncoder();
  auto.autoLimelightV4();
  }

  @Override
  public void teleopInit() {
    //intake.intakeSolenoid(true); //up
    shooter.hoodReset = false;
  }

  @Override
  public void teleopPeriodic() {
    cargoVision.getDriverView();
    drive.driveTeleop();
    shooter.shooterIntakeTeleop();
    climber.climbTeleop();
    smartDash.dashTele();

    SmartDashboard.putBoolean("translateswitch", intake.translateSwitch.get());
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}
 
  @Override
  public void testInit() {
    // climber.climbInit();
    // cargoVision.visionInit(1);
    // drive.lFEncoder.setPosition(0);
    shooter.shooterInit();
    cargoVision.visionInit(0);

  }
  double testHoodValue = 0;
  @Override
  public void testPeriodic() {
    drive.driveMecanum.driveCartesian(-oi.Xbox1.getLeftY(), oi.Xbox1.getLeftX(), oi.Xbox1.getRightX());
    if (oi.Xbox1.getYButton()) {
      drive.driveMecanum.driveCartesian(0, 0, cargoVision.getRotationValue());
    }
    //climber.winchPistons(oi.winchUp());
    // if(oi.Xbox1.getAButtonReleased()){
    //   testHoodValue --;
    // }
    // else if(oi.Xbox1.getBButtonReleased()){
    //   testHoodValue ++;
    // }
    // shooter.hoodMotorRunToPosManual(testHoodValue);  
    // SmartDashboard.putNumber("hood", testHoodValue);
    // SmartDashboard.putNumber("hoodEncoder", shooter.hoodEncoder());
    // drive.driveToCargo();
  }
  
}


