// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.drivetrain.Butterfly;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class Robot extends TimedRobot {

    private Command autonomousCommand;

    @Override
    public void robotInit() {
        RobotContainer.getInstance();
    }

    @Override
    public void robotPeriodic() {
        //ToDo See if this actually functions as hoped
        CommandScheduler.getInstance().run();
//        RobotContainer.getInstance().setDropped(Drivetrain.getInstance().getCurrentCommand().getClass().equals(Butterfly.class));
    }

    /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
    @Override
    public void autonomousInit() {
        //ToDo Pass in parameters
        autonomousCommand = RobotContainer.getInstance().getAutonomous(0, Constants.Alliance.NONE);

        // schedule the autonomous command (example)
        if (autonomousCommand != null) {
            autonomousCommand.schedule();
        }
    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }

    @Override
    public void teleopPeriodic() {

    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {
//        Limelight.disableLEDs();
    }

    @Override
    public void testInit() {

    }

    @Override
    public void testPeriodic() {

    }
}