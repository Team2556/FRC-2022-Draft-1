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
  Shooter shooter = new Shooter();
  Climber climber = new Climber();
  Limelight limelight = new Limelight();
  OI oi = new OI();
  Auto auto = new Auto(drive, intake, shooter, limelight);
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();


  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    //drive.drivebaseInit(); apparently motors can't be reversed 
    //shooter.shooterInit();
  }

 
  @Override
  public void robotPeriodic() {
    SmartDashboard.putBoolean("rF is Inverted", drive.rightMotorsReversed());
    // drive.lFEncoder.setInverted(true);
    // SmartDashboard.putBoolean("lFEncoder Inverted", drive.lFEncoder.getInverted());
  }

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    auto.step = 0;
    drive.lFEncoder.setPosition(0);
  }

  @Override
  public void autonomousPeriodic() {
  auto.autoDraft1();
  }

  @Override
  public void teleopInit() {
   // intake.intakeSolenoid(true); //up
    drive.lFEncoder.setPosition(0);
    shooter.shooterInit();
    
    //climber.clampPiston(true);
  }

  @Override
  public void teleopPeriodic() {
    SmartDashboard.putNumber("encoder lF", drive.lFEncoderValue());
    // intake.translateSwitchTest();
    //drive.dualDrivebase();
    //drive.reverseRightMotors(oi.Xbox1.getAButtonReleased());
    intake.intakeTeleop();
    shooter.shooterTeleop();
    SmartDashboard.putNumber("hood Encoder", shooter.hoodEncoder());
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}
