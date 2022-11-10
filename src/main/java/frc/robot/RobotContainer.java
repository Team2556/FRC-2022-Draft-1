// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.drivetrain.AutoAlignment;
import frc.robot.commands.drivetrain.Butterfly;
import frc.robot.commands.drivetrain.TeleopDrive;
import frc.robot.commands.elevator.TeleopClimb;
import frc.robot.commands.intake.Active;
import frc.robot.commands.shooter.Shoot;
import frc.robot.subsystems.*;

public class RobotContainer {

    private static RobotContainer instance;
    private static final Drivetrain drivetrain = Drivetrain.getInstance();

    private static final Intake intake = Intake.getInstance();
    private static final Shooter shooter = Shooter.getInstance();
    private static final Elevator elevator = Elevator.getInstance();

    // Controllers
    XboxController xbox1 = new XboxController(0);
    XboxController xbox2 = new XboxController(1);
    // ToDo See if it is possible to just pass 'dropped' as a reference rather than needing it to be static & getter.
    private static boolean dropped = false;

    public RobotContainer() {
        Limelight.nothing();
        configureControls();
    }

    private void configureControls() {

        drivetrain.setDefaultCommand(
                new TeleopDrive(xbox1::getLeftY, xbox1::getLeftX, xbox1::getRightX));

        new JoystickButton(xbox1, XboxController.Button.kB.value)
                .whenReleased(() -> dropped = !dropped);

        intake.setDefaultCommand(
                new Active(xbox1::getRightTriggerAxis,0.0));

        shooter.setDefaultCommand(
                new Shoot(xbox1::getLeftTriggerAxis, xbox1::getLeftBumper, xbox1::getRightBumper)
        );

        elevator.setDefaultCommand(
                new TeleopClimb(xbox1::getYButton, xbox1::getAButton, xbox1::getLeftBumper, xbox1::getRightBumper, xbox1::getXButton)
        );
    }

    public Command getAutonomous(int auto, Constants.Alliance alliance) {
        switch (auto) {

            default: return () -> null;
        }
    }

    public boolean getDropped() {
        return dropped;
    }

    public void setDropped(boolean dropped) {
        RobotContainer.dropped = dropped;
    }

    public static RobotContainer getInstance() {
        if(instance == null)
            instance = new RobotContainer();
        return instance;
    }
}