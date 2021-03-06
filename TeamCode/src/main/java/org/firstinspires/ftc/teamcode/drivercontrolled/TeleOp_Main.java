package org.firstinspires.ftc.teamcode.drivercontrolled;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp Main", group="Driver Controlled")
public class TeleOp_Main extends OpMode {
    private float inputScale = 0.7f;

    private DcMotor FL;
    private DcMotor BL;
    private DcMotor FR;
    private DcMotor BR;
    private DcMotor motorWinch;

    private Servo servoLeftClaw;
    private Servo servoRightClaw;
    private boolean claw;

    @Override
    public void init() {

        FL = hardwareMap.dcMotor.get("FL");
        BL = hardwareMap.dcMotor.get("BL");
        FR = hardwareMap.dcMotor.get("FR");
        BR = hardwareMap.dcMotor.get("BR");

        motorWinch = hardwareMap.dcMotor.get("winch");

        servoLeftClaw = hardwareMap.servo.get("leftClaw");
        servoRightClaw = hardwareMap.servo.get("rightClaw");

        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);
        FR.setDirection(DcMotor.Direction.REVERSE); // uncomment if weird motor does its thing

        servoLeftClaw.setPosition(0);
        servoRightClaw.setPosition(1);
        claw = false;

    }

    @Override
    public void loop() {
        float right = gamepad1.right_stick_y;
        float left = gamepad1.left_stick_y;

        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        right *= inputScale;
        left *= inputScale;

        FR.setPower(right);
        FL.setPower(left);
        BR.setPower(right);
        BL.setPower(left);
//---------------------------------------------------------------
        if(gamepad2.b) motorWinch.setPower(0.3);
        else if(gamepad2.x) motorWinch.setPower(-0.3);
        else motorWinch.setPower(0);

//---------------------------------------------------------------
        if (gamepad1.a) claw = true;
        else if(claw) {
            if (servoLeftClaw.getPosition() == 1d) {
                servoLeftClaw.setPosition(0d);
                servoRightClaw.setPosition(1d);
            } else if (servoLeftClaw.getPosition() == 0d) {
                servoLeftClaw.setPosition(1d);
                servoRightClaw.setPosition(0d);
            }

            claw = false;
        }

    }

    @Override
    public void stop() {

    }

}
