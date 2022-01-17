/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.DriveWithController;
import frc.robot.commands.GamePieceAutoSequential;
// import frc.robot.commands.Onboarding;
import frc.robot.commands.RecordJoy;
import frc.robot.commands.ReplayJoy;
// import frc.robot.commands.ShootTrigger;
import frc.robot.commands.ShooterPIDVelo;
// import frc.robot.commands.SpitBalls;
import frc.robot.subsystems.Camera;
import frc.robot.subsystems.Camera2;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Onboarder;
import frc.robot.subsystems.Shooter;

public class RobotContainer {
  //subbies and controller
  private final DriveTrain driveTrain = new DriveTrain();

  private final Onboarder onboarder = new Onboarder();

  private final Camera cam = new Camera();

  private final Shooter shooter = new Shooter();

  private final Camera2 cam2 = new Camera2();

  private final XboxController controller = new XboxController(0);

  //commands
  private final DriveWithController drive = new DriveWithController(driveTrain, controller);

  private final RecordJoy record = new RecordJoy(driveTrain, controller, onboarder);

  private final ReplayJoy replay = new ReplayJoy(driveTrain, onboarder);

  // private final ShootTrigger shootTrigger = new ShootTrigger(controller, shooter);


  // private final Onboarding onboard = new Onboarding(onboarder);

  //autos 
  private final GamePieceAutoSequential gamePieceAuto = new GamePieceAutoSequential(driveTrain, cam, onboarder);

  private final ShooterPIDVelo shootPIDVelo = new ShooterPIDVelo(shooter, cam2);

  // private final SpitBalls spitBalls = new SpitBalls(onboarder);
  public RobotContainer() {
    configureButtonBindings();
    driveTrain.setDefaultCommand(drive);
    //shooter.setDefaultCommand(shootPIDVelo);

    // onboarder.setDefaultCommand(onboard);
  }

  private void configureButtonBindings() {
   // final JoystickButton recordJoy = new JoystickButton(controller, Button.kStickLeft.value);
   //   recordJoy.toggleWhenPressed(record);
   // final JoystickButton replayJoy = new JoystickButton(controller, Button.kStickRight.value);
   //   replayJoy.whenPressed(replay);

    final JoystickButton zoneOneButton = new JoystickButton(controller, Button.kY.value);
      zoneOneButton.whenPressed(() -> SmartDashboard.putNumber("targetAngle", -4000));
    final JoystickButton zoneTwoButton = new JoystickButton(controller, Button.kB.value);
      zoneTwoButton.whenPressed(() -> SmartDashboard.putNumber("targetAngle", -7000));
    final JoystickButton zoneThreeButton = new JoystickButton(controller, Button.kA.value);
      zoneThreeButton.whenPressed(() -> SmartDashboard.putNumber("targetAngle", -12000));
    final JoystickButton zoneFourButton = new JoystickButton(controller, Button.kX.value);
      zoneFourButton.whenPressed(() -> SmartDashboard.putNumber("targetAngle", -23000));

    // final JoystickButton resetEncoder = new JoystickButton(controller, Button.kStart.value);
    //   resetEncoder.whenPressed(() -> driveTrain.resetEncoder());
    // final JoystickButton unplay = new JoystickButton(controller, Butt
    // final JoystickButton suck = new JoystickButton(controller, Button.kBumperLeft.value);
      //  suck.whenHeld(onboard);
     // suck.whenPressed(() -> shooter.tilt(-1));
     //  suck.whenReleased(() -> shooter.tilt(0));
    final POVButton turretLeft = new POVButton(controller, 270);//left
      turretLeft.whenPressed(() -> shooter.swivel(.25));
      turretLeft.whenReleased(() -> shooter.swivel(0));
    final POVButton turretRight = new POVButton(controller, 90);//right
      turretRight.whenPressed(() -> shooter.swivel(-.25));
      turretRight.whenReleased(() -> shooter.swivel(0));
    final POVButton loadButton = new POVButton(controller, 180);//down
      loadButton.whenPressed(() -> SmartDashboard.putNumber("targetAngle", 0));
    // final JoystickButton shootToVelocityAuto = new JoystickButton(controller, Button.kB.value);
    //   shootToVelocityAuto.whenPressed(shootToVelocity);
    // final POVButton gamePieceAutoButton = new POVButton(controller, 0);//up
    //   gamePieceAutoButton.whenPressed(shooter.setInitTilt(""));
      //shootButton.whenPressed(() -> shooter.shoot(1));
      // shootButton.whenReleased(() -> shooter.shoot(0));
    final JoystickButton tiltUp = new JoystickButton(controller, Button.kBumperRight.value);
      tiltUp.whenPressed(() -> shooter.tilt(1));
      tiltUp.whenReleased(() -> shooter.tilt(0));
   final JoystickButton tiltDown = new JoystickButton(controller, Button.kBumperLeft.value);
     tiltDown.whenPressed(() -> shooter.tilt(-1));
     tiltDown.whenReleased(() -> shooter.tilt(0));
    // final JoystickButton startAuto = new JoystickButton(controller, Button.kStart.value);
    //   startAuto.whenPressed(() -> driveTrain.setAuto(true));
    // final JoystickButton endAuto = new JoystickButton(controller, Button.kBack.value);
    //   endAuto.whenPressed(() -> driveTrain.setAuto(false));
    final JoystickButton shootPIDVeloButton = new JoystickButton(controller, Button.kStart.value);
      shootPIDVeloButton.whenPressed(shootPIDVelo);
    final JoystickButton shootBalls = new JoystickButton(controller, Button.kBack.value);
      shootBalls.whenPressed(()-> shooter.load(-1));
      shootBalls.whenReleased(()-> shooter.load(0));

    // final JoystickButton spitBallsButton = new JoystickButton(controller, Button.kBumperLeft.value);
    //   spitBallsButton.whenPressed(spitBalls);

  }
  public Command getAutonomousCommand() {
    // return gamePieceAuto;
    return replay;
  }
}
