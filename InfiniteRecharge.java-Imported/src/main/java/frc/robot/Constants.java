package frc.robot; 


import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;;

public final class Constants {
    public static final class DriveConstants {
        public static final double ksVolts = 0.846;
        public static final double kvVoltSecondsPerMeter = .209;
        public static final double kaVoltSecondsSquaredPerMeter = 0.0376;

        // Example value only - as above, this must be tuned for your drive!
        public static final double kPDriveVel = 1.77;
        
        public static final double kTrackwidthMeters = .7014113334078461;
        public static final DifferentialDriveKinematics kDriveKinematics =
            new DifferentialDriveKinematics(kTrackwidthMeters);

        public static final double kMaxSpeedMetersPerSecond = 3;
        public static final double kMaxAccelerationMetersPerSecondSquared = 3;
        
        // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;

        public static final int kEncoderCPR = 4096;
        public static final double kWheelDiameterMeters = 0.0762;
        public static final double kEncoderDistancePerPulse =
        // Assumes the encoders are directly mounted on the wheel shafts
        (kWheelDiameterMeters * Math.PI) / (double) kEncoderCPR;
    }
}