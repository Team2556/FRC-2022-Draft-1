package frc.robot;

import org.photonvision.PhotonCamera;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.TimedRobot;

public class CargoVision extends TimedRobot {
    PhotonCamera camera = new PhotonCamera("photonvision");
    PIDController turnController = new PIDController(Constants.visionP, 0, 0);

    public void visionInit(int allianceColor) {
        camera.setPipelineIndex(allianceColor);
    }

    public double getRotationValue() {
        double rotationValue = 0;
        var result = camera.getLatestResult();
        if (result.hasTargets()) {
            rotationValue = -turnController.calculate(result.getBestTarget().getYaw(), 0);
        }
        return rotationValue;
    }

    public void getDriverView() {
        camera.setDriverMode(true);
    }
}
