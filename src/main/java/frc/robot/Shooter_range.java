package frc.robot;

public class Shooter_range {
    Limelight limelight = new Limelight();
        public int shootdist(){
                
                if (limelight.limeLightDistanceInches() < 77.5){
                    return 1;
                }
                if (77.5 < limelight.limeLightDistanceInches() && limelight.limeLightDistanceInches() < 191){
                    return 2;
                }
                if (limelight.limeLightDistanceInches() > 191 && limelight.limeLightDistanceInches() < 267){
                    return 3;
                }
                else{
                    return 0;
                }
            
        } 
    
}
