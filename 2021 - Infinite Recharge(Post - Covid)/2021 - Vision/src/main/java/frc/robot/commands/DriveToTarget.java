/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Camera2;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.FlyWheel;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class DriveToTarget extends SequentialCommandGroup {
  /**
   * Creates a new DriveToTarget.
   */
  public DriveToTarget(DriveTrain drive, Camera2 cam, int targetZone) {
    //new autoRotate(drive, -1 * Math.atan(60 / pixels)
    //new PIDTranslate(drive, cam)\
    super(new autoRotate(drive, cam, targetZone), new autoTranslate(drive, cam), new RotateBackToZero(drive,cam));
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
  }
}
