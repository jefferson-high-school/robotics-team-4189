/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.SPI.Port;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class DriveConstants{
        public static final int kLeftMasterID = 1;
        public static final int kLeftSlaveID = 7;
        public static final int kRightMasterID = 2;
        public static final int kRightSlaveID = 5;
        public static final int kSlotIdx = 0;
        public static final int kTimeoutMs = 0;
	    public static final int kPIDLoopIdx = 0;
        public static final double kDriveDeadband = 0.1;
    }
    public static final class OIConstants{
        public static final int kDriveControllerPort = 0;
        public static final int kSecondaryControllerPort = 1;
    }
    public static final class DriveSensorConstants{
        public static final int kLeftUltrasonicPort = 0;
        public static final int kFrontUltrasonicPort = 2;
        public static final int kRightTopUltrasonicPort = 3;
        public static final int kRightBottomUltrasonicPort = 1;
        public static final double kVoltToDistance = 38.0952381;
        public static final int kTicksPerRev = 21807;
        public static final double kWheelCir = (2 * Math.PI * (7.5/2));
        public static final Port kNavxPort = Port.kMXP;
    }

    public static final class DumperConstants{
        public static final int kRollerTalonID = 4;
        public static final int kSolenoidPort = 0;
    }
    
    public static final class ElevatorConstants{
        public static final int kElevatorTalonID = 6;
        public static final int kAdjusmentTalonID = 3;
        public static final int kElevatorSolenoidPort = 7;
        public static final int kSlotIdx = 0;
        public static final int kTimeoutMs = 30;
        public static final int kPIDLoopIdx = 0;
        public static final int kCruiseVelocity = 1500;
        public static final int kAcceleration = 3000;
        public static final Gains kGains = new Gains(0.2, 0.0, 0.0, 0.2, 0, 1.0);
    }

    public static final class ElevatorSensorConstants{
        public static final int kTicksPerInch = 1461;
        //if negative encoder value go to controlelvatorheight
    }

    public static final class AutoConstants{
        public static final int kCruiseVelocity = 20000;
        public static final int kAcceleration = 10000;
        public static final Gains kGains = new Gains(0.5, 0.0, 0.0, 0.2, 0, 1.0);
    }
    
}
