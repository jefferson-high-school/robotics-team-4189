/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ColorWheelSpinner extends SubsystemBase {
  /**
   * Creates a new ColorSensor.
   */
  private final I2C.Port i2c = I2C.Port.kOnboard;
  private final ColorSensorV3 colorSensor = new ColorSensorV3(i2c);

  private final ColorMatch m_colorMatcher = new ColorMatch();

  private final Color kBlueTarget = ColorMatch.makeColor(0.1240234375, 0.428955078125, 0.44677734375);
  private final Color kGreenTarget = ColorMatch.makeColor(0.161865234375, 0.589599609375, 0.248779296875);
  private final Color kRedTarget = ColorMatch.makeColor(0.527099609375, 0.343017578125, 0.1298828125);
  private final Color kYellowTarget = ColorMatch.makeColor(0.3125, 0.569580078125, 0.117919921875);

  private Color detectedColor = colorSensor.getColor();

  ColorMatchResult match;

  String colorString;

  private final WPI_TalonSRX mWheelSpinner = new WPI_TalonSRX(6);

  public ColorWheelSpinner() {
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);
  }

  @Override
  public void periodic() {
    outputTelemetry();
    // This method will be called once per scheduler run
  }
  public void colorToString(){
    if (match.color == kBlueTarget) {
      colorString = "r";
    } else if (match.color == kRedTarget) {
      colorString = "b";
    } else if (match.color == kGreenTarget) {
      colorString = "y";
    } else if (match.color == kYellowTarget) {
      colorString = "g";
    } else {
      colorString = "Unknown";
    }
  }
  public void printColor(){
    Color rgb = colorSensor.getColor();
    System.out.println("Red: " + rgb.red + "\n" + "Green: " + rgb.green + "\n"+ "Blue: " + rgb.blue + "\n");
  }
  public String detectColors(){
    detectedColor = colorSensor.getColor();
    match = m_colorMatcher.matchClosestColor(detectedColor);
    colorToString();
    return colorString;
  }

  public void spinOLC(double speed){
    mWheelSpinner.set(ControlMode.PercentOutput, speed);
  }

  public void outputTelemetry(){
    detectColors();
    // SmartDashboard.putNumber("Red", detectedColor.red);
    // SmartDashboard.putNumber("Green", detectedColor.green);
    // SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", colorString);
    SmartDashboard.putString("FMS Color", DriverStation.getInstance().getGameSpecificMessage());
  }

}