/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public class Gains {
	public static double kPTurn = 0.08;
    public static double kITurn = 0.0000000;
    public static double kDTurn = 0.088150959;
    // public static double kP = -0.1;
    // public static double kI = 0.0;
	// public static double kD = -0.19;
	
    public final double kP;
	public final double kI;
	public final double kD;
	public final double kF;
	public final int kIzone;
	public final double kPeakOutput;
	
	public Gains(double _kP, double _kI, double _kD, double _kF, int _kIzone, double _kPeakOutput){
		kP = _kP;
		kI = _kI;
		kD = _kD;
		kF = _kF;
		kIzone = _kIzone;
        kPeakOutput = _kPeakOutput;
    }
}
