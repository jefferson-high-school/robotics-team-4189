/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.AlignColor;
import frc.robot.commands.ControlElevatorHeight;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.DriveToDistance;
import frc.robot.commands.DriveToDistanceMotionMagic;
import frc.robot.commands.Dump;
import frc.robot.commands.LeftDriveAuto;
import frc.robot.commands.MoveElevatorUpMotionMagic;
import frc.robot.commands.PIDTurn;
import frc.robot.commands.RightWallAuto;
import frc.robot.commands.RotateWheel;
import frc.robot.commands.SongSelect;
import frc.robot.commands.SpinRollers;
import frc.robot.commands.SpinRollersOLC;
import frc.robot.commands.StraightAuto;
import frc.robot.commands.TurnHardStop;
import frc.robot.commands.TurnNonPID;
import frc.robot.commands.TurnToAngle;
import frc.robot.commands.WallPickUpAuto;
import frc.robot.subsystems.ClimbElevator;
import frc.robot.subsystems.ColorWheelSpinner;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.DumpMechanism;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final SendableChooser<Command> autoChooser = new SendableChooser<Command>();

  private final XboxController mDriveController = new XboxController(OIConstants.kDriveControllerPort);

  private final XboxController mSecondaryController = new XboxController(OIConstants.kSecondaryControllerPort);

  private final DriveTrain mDriveTrain = new DriveTrain();

  private final DriveCommand mDriveCommand = new DriveCommand(mDriveTrain, mDriveController);

  private final DumpMechanism mDumpMechanism = new DumpMechanism();

  private final SpinRollers mSpinRollers = new SpinRollers(mDumpMechanism);

  private final Dump mDumpCommand = new Dump(mDumpMechanism);

  private final ClimbElevator mElevator = new ClimbElevator();

  private final ColorWheelSpinner mColorSpinner = new ColorWheelSpinner();

  private final RotateWheel mRotateWheel = new RotateWheel(mColorSpinner);

  private final ControlElevatorHeight mControlElevatorHeight = new ControlElevatorHeight(mElevator, mSecondaryController);

  private final SpinRollersOLC mSpinRollersOLC = new SpinRollersOLC(mDumpMechanism, mSecondaryController);

  private final Compressor compressor = new Compressor();

  private final TurnNonPID mTurnNonPID = new TurnNonPID(mDriveTrain, -90);

  private final DriveToDistance mDriveToDistance = new DriveToDistance(mDriveTrain, 120);

  private final DriveToDistanceMotionMagic mDriveToDistanceMotionMagic = new DriveToDistanceMotionMagic(mDriveTrain, 36, mDumpMechanism);

  private final TurnToAngle mTurnToAngle = new TurnToAngle(mDriveTrain, 90);

  private final MoveElevatorUpMotionMagic mRaiseElevator = new MoveElevatorUpMotionMagic(mElevator, 12);

  private final AlignColor mAlignColor = new AlignColor(mColorSpinner);

  private final PIDTurn turn = new PIDTurn(mDriveTrain);

  private final TurnHardStop hardstop = new TurnHardStop(mDriveTrain);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    autoChooser.addOption("Middle Start Auto", new StraightAuto(mDriveTrain, mDumpMechanism));
    autoChooser.addOption("Right Wall Auto (No pick up)", new RightWallAuto(mDriveTrain, mDumpMechanism));
    autoChooser.addOption("Right Wall Auto (Pick up)", new WallPickUpAuto(mDriveTrain, mDumpMechanism));
    autoChooser.addOption("Left Straight Auto", new LeftDriveAuto(mDriveTrain));
    SmartDashboard.putData("Autonomous mode chooser", autoChooser);
    
    configureButtonBindings();
    mDriveTrain.setDefaultCommand(mDriveCommand);
    mDumpMechanism.setDefaultCommand(mSpinRollersOLC);
    mElevator.setDefaultCommand(mControlElevatorHeight);
    mDriveTrain.outputTelemetry();
    mElevator.outputTelemetry();
    CameraServer.getInstance().startAutomaticCapture(0);
    CameraServer.getInstance().startAutomaticCapture(1);
    compressor.setClosedLoopControl(true);
    mElevator.lockElevatorClamp();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    final JoystickButton dumpButton = new JoystickButton(mDriveController, Button.kBumperLeft.value);
    dumpButton.whenPressed(mDumpCommand);
    final JoystickButton spinSetRotationsButton = new JoystickButton(mDriveController, Button.kB.value);
    spinSetRotationsButton.whenPressed(mRotateWheel.withTimeout(4.7));
    final JoystickButton spinRollers = new JoystickButton(mDriveController, Button.kBumperRight.value);
    spinRollers.whileHeld(mSpinRollers);
    
    final JoystickButton resetGyro = new JoystickButton(mDriveController, Button.kA.value);
    resetGyro.whenPressed(()-> System.out.println(mDriveTrain.getGyroAngle()));
    final JoystickButton turnButton = new JoystickButton(mDriveController, Button.kX.value);
    turnButton.whenPressed(hardstop);

    final JoystickButton lockElevatorClampButton = new JoystickButton(mSecondaryController, Button.kBumperLeft.value);
    lockElevatorClampButton.whenPressed(() -> mElevator.lockElevatorClamp());    
    final JoystickButton unlockElevatorClampButton = new JoystickButton(mSecondaryController, Button.kBumperRight.value);
    unlockElevatorClampButton.whenPressed(() -> mElevator.unlockElevatorClamp());
    final JoystickButton openDumper = new JoystickButton(mSecondaryController, Button.kX.value);
    openDumper.whenPressed(() -> mDumpMechanism.dumpCells(false));
    final JoystickButton closeDumper = new JoystickButton(mSecondaryController, Button.kY.value);
    closeDumper.whenPressed(() -> mDumpMechanism.dumpCells(true));
    // final JoystickButton playMusic = new JoystickButton(mSecondaryController, Button.kStart.value);
    // playMusic.whenPressed(() -> mDriveTrain.music(mDriveTrain.getSong()));
    // final JoystickButton changeSong = new JoystickButton(mSecondaryController, Button.kBack.value);
    // changeSong.whenPressed(() -> mDriveTrain.nextSong());


    // final JoystickButton outputColor = new JoystickButton(mDriveController, Button.kStart.value);
    // outputColor.whenPressed(() -> mColorSpinner.outputTelemetry());
    // final JoystickButton testTurn = new JoystickButton(mDriveController, Button.kX.value);
    // testTurn.whenPressed(mTurnNonPID);
    // final JoystickButton testDriveTrainMM = new JoystickButton(mDriveController, Button.kY.value);
    // testDriveTrainMM.whenPressed(new HitWall(mDriveTrain));
    // final JoystickButton raiseToSepointButton = new JoystickButton(mSecondaryController, Button.kY.value);
    // raiseToSepointButton.whenPressed(mRaiseElevator);
    final JoystickButton spinToColorButton = new JoystickButton(mDriveController, Button.kY.value);
    spinToColorButton.whenPressed(mAlignColor.withTimeout(5));
    final JoystickButton spinColorWheel = new JoystickButton(mSecondaryController, Button.kB.value);
    spinColorWheel.whileHeld(() -> mColorSpinner.spinOLC(.3));
    spinColorWheel.whenReleased(() -> mColorSpinner.spinOLC(0));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return autoChooser.getSelected();
  }
}
