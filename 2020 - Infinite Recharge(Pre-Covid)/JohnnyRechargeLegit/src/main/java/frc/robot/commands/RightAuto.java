/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.DumpMechanism;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class RightAuto extends SequentialCommandGroup {
  /**
   * Creates a new RightAuto.
   */
  public RightAuto(DriveTrain driveTrain, DumpMechanism dumper) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new DriveToDistanceMotionMagic(driveTrain, 87, dumper), new TurnToAngle(driveTrain, -90), new DriveToDistanceMotionMagic(driveTrain, 81, dumper),new TurnToAngle(driveTrain, 90),new DriveToDistanceMotionMagic(driveTrain, 27, dumper),new Dump(dumper),new TurnToAngle(driveTrain, 45), new DriveToDistanceMotionMagic(driveTrain, -100, dumper));
  }
}
