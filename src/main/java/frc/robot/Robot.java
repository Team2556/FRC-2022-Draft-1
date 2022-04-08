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
  }

 
  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    auto.autoInit(1);
    m_autoSelected = m_chooser.getSelected();
    m_autoSelected = SmartDashboard.getString("Auto Selector", kRedAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  @Override
  public void autonomousPeriodic() {
  // auto.autoEncoder();
  auto.autoLimelightV3();
  }

  @Override
  public void teleopInit() {
    //intake.intakeSolenoid(true); //up
    drive.lFEncoder.setPosition(0);
    shooter.shooterInit();
    climber.climbInit();
  }

  @Override
  public void teleopPeriodic() {
    cargoVision.getDriverView();
    drive.driveTeleop();
    shooter.shooterIntakeTeleop();
    climber.climbTeleop();
    smartDash.dashTele();
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

  }

  @Override
  public void testPeriodic() {
    // if (oi.Xbox1.getYButton()) {
    //   drive.mecanumDrive(0, 0, cargoVision.getRotationValue());
    // }
    
    // smartDash.dashTest();
    //shooter.hoodMotorRunToPosManual(500);
    //  shooter.hoodMotor(oi.hoodSpeed());
    // climber.winchPistons(oi.winchUp());
    // climber.clampPiston(oi.clampOut());
    // if(oi.Xbox1.getAButton()){
    // drive.limelightTurn();}
    // else{
    // drive.dualDrivebase(oi.dropped());
    // }
    // drive.triDrivebase();
    // if(oi.limeLightTurn()){
    //   shooter.shooterMotor(Math.abs(-13750));
    // }
    // else{
    //   shooter.shooterMotor(0);
    // } 
    // if(oi.limeLightTurn()){
    //   if(Math.abs(limelight.x) <= 5 && Math.abs(shooter.difference) <=50){
    //   intake.translateMotor(oi.translateRunSpeed);
    //   }
    //   else
    //   {
    //     intake.translateMotor(0);
    //   } 
    // }
  }
}


