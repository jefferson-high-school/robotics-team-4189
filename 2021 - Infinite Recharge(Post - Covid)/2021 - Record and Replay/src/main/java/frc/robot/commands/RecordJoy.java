/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Onboarder;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;
import java.io.PrintWriter;
import java.io.File;

public class RecordJoy extends CommandBase {
  /**
   * Creates a new RecordJoy.
   */
  private final DriveTrain mDriveTrain;
  private final XboxController mController;
  private String mName;
  private PrintWriter Writer;
  private double yAxis;
  private double xAxis;
  private boolean end;
  private boolean input;
  private JoystickButton button;
  private JoystickButton onboardControl;
  private Onboarder onboard;
  private boolean suck;
  private String shooting;
  
  public RecordJoy(DriveTrain driveTrain, XboxController controller, Onboarder onboarding) {
    mDriveTrain = driveTrain;
    mController = controller;
    onboard = onboarding;
    button = new JoystickButton(mController, Button.kStart.value);
    onboardControl = new JoystickButton(mController, Button.kBumperLeft.value);
    addRequirements(mDriveTrain);
  }

  @Override
  public void initialize() {
    String name = SmartDashboard.getString("GamePieceAuto", "Output");
    mName = "/home/lvuser/"+ name + ".csv";
    
    try {
      Writer = mDriveTrain.initWriter(Writer, mName);
    } catch (final Exception e) {
      e.addSuppressed(e);
    }
    
    new File(mName);

    Writer.append("yAxis,xAxis,Offloading\n");
    
    end = false;
    input = false;
    suck = false;
    SmartDashboard.putBoolean("Recording", true);

    mDriveTrain.resetEncoder();
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    mDriveTrain.outputTelemetry();
    if (!input) {
      if(suck){
        onboard.onboard(1);
        onboardControl.whenReleased(() -> suck = false);
      }
      else{
        onboard.onboard(0);
        onboardControl.whenReleased(() -> suck = true);
      }
      button.whenPressed(() -> input = true);
      yAxis = -this.mController.getY(Hand.kLeft) ;
      xAxis = this.mController.getX(Hand.kRight) ;
      shooting = ""+this.mController.getBackButtonPressed();
      mDriveTrain.drive(yAxis, xAxis);

      Writer.append(yAxis + "," + xAxis + "," + shooting + "\n"  );
      mDriveTrain.resetEncoder();

    }else{
      SmartDashboard.putBoolean("Recording", false);
      mDriveTrain.drive(0, 0);
      end = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {
    Writer.flush();
    Writer.close();
    SmartDashboard.putBoolean("Recording", false);
    
    onboard.onboard(0);
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return end;
  }
}
