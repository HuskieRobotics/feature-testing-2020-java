
package frc.robot;
public final class Constants {
    public static final class ShooterConstants {
        public static final int shooterMotor1CanID = 10;
        public static final int shooterMotor2CanID = 11;

        public static double shootingSpeed = 1;
        public static double kP = 1;
        public static double kI = 0;
        public static double kD = 0;

        public static final boolean kEncoderReversed = false;
        public static final int kEncoderCPR = 2048;
        public static final double kEncoderDistancePerPulse =
        // Distance units will be rotations
        1.0 / (double) kEncoderCPR;

        public static final double kShooterFreeRPS = 5300;
        public static double kShooterTargetRPS = 4000;
        public static final double kShooterToleranceRPS = 50;

        // On a real robot the feedforward constants should be empirically determined; these are
        // reasonable guesses.
        public static final double kSVolts = 0.05;
        public static final double kVVoltSecondsPerRotation =
            // Should have value 12V at free speed...
            12.0 / kShooterFreeRPS;

        
    }
    public static final class StorageConstants {
        public static final int storageMotor1CanID = 5;
        public static final int storageMotor2CanID = 6;
        public static final int bottomSensorChannel = 0;
        public static final int topSensorChannel = 1;

        public static final double storageFastSpeed = .4;
        public static final double storageSlowSpeed = .2;
        public static final double storageDelay = .2;
        public static final int cycleLength = 20691;

        public static final double manualStorageSpeed = .5; 
        
    }
}
