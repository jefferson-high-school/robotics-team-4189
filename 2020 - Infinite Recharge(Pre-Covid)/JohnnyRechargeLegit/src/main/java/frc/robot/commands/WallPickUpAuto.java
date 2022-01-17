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
public class WallPickUpAuto extends SequentialCommandGroup {
  /**
   * Creates a new WallPickUpAuto.
   */
  public WallPickUpAuto(DriveTrain driveTrain, DumpMechanism dumper) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new DriveToDistancePickUp(driveTrain, 158, dumper), new DriveToDistanceBack(driveTrain, -238), new TurnNonPID(driveTrain, 90), new DriveToDistance(driveTrain, 51), new TurnNonPID(driveTrain, 90), new HitWall(driveTrain), new Dump(dumper), new DriveToDistanceBack(driveTrain, -5), new TurnNonPID(driveTrain, 90), new DriveToDistance(driveTrain, 24));
  }
}
