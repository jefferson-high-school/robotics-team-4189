/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.commands.AlignRobot;
import frc.robot.commands.AutoShoot;
import frc.robot.commands.AutoShootWithZone;
import frc.robot.commands.DriveToTarget;
import frc.robot.commands.DriveWithController;
import frc.robot.commands.GamePieceAuto;
import frc.robot.commands.ManualShooting;
import frc.robot.commands.PIDTranslate;
import frc.robot.commands.PointAtTarget;
import frc.robot.commands.ShootBallsThree;
import frc.robot.commands.ShooterPIDVelo;
import frc.robot.commands.TiltShooter;
import frc.robot.commands.autoRotate;
import frc.robot.commands.autoTranslate;
import frc.robot.subsystems.Camera;
import frc.robot.subsystems.Camera2;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.FlyWheel;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  private final DriveTrain driveTrain = new DriveTrain(); 
  private final XboxController controller = new XboxController(0);
  private final DriveWithController drive = new DriveWithController(driveTrain, controller);
  //private final Camera camera = new Camera();
  private final GamePieceAuto gameauto = new GamePieceAuto(driveTrain, camera);
  private final FlyWheel flywheel = new FlyWheel();
  //private final Camera2 camera2 = new Camera2();
  private final PointAtTarget target = new PointAtTarget(flywheel, camera2);
  private final autoRotate rotate = new autoRotate(driveTrain, camera2, 1);
  private final DriveToTarget prepare = new DriveToTarget(driveTrain, camera2, 1);
  private final ManualShooting shooting = new ManualShooting(controller, flywheel);
  private final PIDTranslate pidtimeohwowthisgonnabecool = new PIDTranslate(driveTrain, camera2);
  private final TiltShooter tiltShooter = new TiltShooter(flywheel,camera2);
  private final AlignRobot alignRobot = new AlignRobot(driveTrain, camera2, flywheel, 1);
  private final autoTranslate moving = new autoTranslate(driveTrain, camera2);
  private final ShootBallsThree shootBalls = new ShootBallsThree(flywheel);
  private final ShooterPIDVelo pidShooter = new ShooterPIDVelo(flywheel);
 // private final AutoShoot shoot = new AutoShoot(driveTrain, camera2, flywheel, 1);
  public RobotContainer() {
    driveTrain.setDefaultCommand(drive);
    //flywheel.setDefaultCommand(pidShooter);
   //flywheel.setDefaultCommand(target);
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    final JoystickButton pointZone1 = new JoystickButton(controller, Button.kA.value);
    pointZone1.whenPressed(new AutoShootWithZone(driveTrain, camera2, flywheel, 1));
    final JoystickButton pointAndShoot2 = new JoystickButton(controller, Button.kB.value);
    pointAndShoot2.whenPressed(new AutoShootWithZone(driveTrain, camera2, flywheel, 2));
    final JoystickButton pointAndShoot3 = new JoystickButton(controller, Button.kX.value);
    pointAndShoot3.whenPressed(new AutoShootWithZone(driveTrain, camera2, flywheel, 3));
    final JoystickButton pointAndShoot4 = new JoystickButton(controller, Button.kY.value);
    pointAndShoot4.whenPressed(new AutoShootWithZone(driveTrain, camera2, flywheel, 4));
    final JoystickButton up = new JoystickButton(controller, Button.kBumperLeft.value);
    up.whenPressed(()->flywheel.setTiltSpeed(1));
    up.whenReleased(()->flywheel.setTiltSpeed(0));
    final JoystickButton down = new JoystickButton(controller, Button.kBumperRight.value);
    down.whenPressed(()->flywheel.setTiltSpeed(-1));
    down.whenReleased(()->flywheel.setTiltSpeed(0));
    final JoystickButton shootBallsButton = new JoystickButton(controller, Button.kStart.value);
    shootBallsButton.whenPressed(shootBalls);
    final JoystickButton feedBallsButton = new JoystickButton(controller, Button.kBack.value);
    feedBallsButton.whenPressed(()->flywheel.setLoadSpeed(-1));
    feedBallsButton.whenReleased(()->flywheel.setLoadSpeed(0));


    //point.whenPressed(()->System.out.println(Math.toDegrees(Math.atan(36 / camera2.readCenterPixels()))));

    //point.whenPressed(new autoRotate(driveTrain, Math.toDegrees(Math.atan(36 / camera2.readCenterPixels()))));
  }



  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return target;

  }
}
