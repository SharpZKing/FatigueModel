package com.fatigue.entity;

import java.util.Date;

public class PersonFatigue {
	//用户id
	private int userId;
	//检测开始时间
	private Date start_time;
	//代表测试数据时的状态
	private int state;
	//测试的日期
	private Date test_time;
	//从开始运动时间到当前时刻的总帧数
	private int fn_count;
	//从开始运动到当前时刻总眨眼次数
	private int blink_count;
	//当前时刻左眼睑尺寸
	private double left_eyelid;
	//当前时刻右眼睑尺寸
	private double right_eyelid;
	//左眼面积
	private double left_eye_range;
	//右眼面积
	private double right_eye_range;
	//左瞳孔面积
	private double left_pupil_range;
	//右瞳孔面积
	private double right_pupil_range;
	//视线转移速度
	private double line_sight_vel;
	//脸部与摄像头距离
	private double distance;
	//五官位置（眼睛、鼻子、嘴巴、眉毛、耳朵）
	private String facial_feature_location;
	//脸部面积大小
	private double face_range;
	//嘴巴张开的面积
	private double mouse_ratio;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Date getTest_time() {
		return test_time;
	}
	public void setTest_time(Date test_time) {
		this.test_time = test_time;
	}
	public int getFn_count() {
		return fn_count;
	}
	public void setFn_count(int fn_count) {
		this.fn_count = fn_count;
	}
	public int getBlink_count() {
		return blink_count;
	}
	public void setBlink_count(int blink_count) {
		this.blink_count = blink_count;
	}
	public double getLeft_eyelid() {
		return left_eyelid;
	}
	public void setLeft_eyelid(double left_eyelid) {
		this.left_eyelid = left_eyelid;
	}
	public double getRight_eyelid() {
		return right_eyelid;
	}
	public void setRight_eyelid(double right_eyelid) {
		this.right_eyelid = right_eyelid;
	}
	public double getLeft_eye_range() {
		return left_eye_range;
	}
	public void setLeft_eye_range(double left_eye_range) {
		this.left_eye_range = left_eye_range;
	}
	public double getRight_eye_range() {
		return right_eye_range;
	}
	public void setRight_eye_range(double right_eye_range) {
		this.right_eye_range = right_eye_range;
	}
	public double getLeft_pupil_range() {
		return left_pupil_range;
	}
	public void setLeft_pupil_range(double left_pupil_range) {
		this.left_pupil_range = left_pupil_range;
	}
	public double getRight_pupil_range() {
		return right_pupil_range;
	}
	public void setRight_pupil_range(double right_pupil_range) {
		this.right_pupil_range = right_pupil_range;
	}
	public double getLine_sight_vel() {
		return line_sight_vel;
	}
	public void setLine_sight_vel(double line_sight_vel) {
		this.line_sight_vel = line_sight_vel;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public String getFacial_feature_location() {
		return facial_feature_location;
	}
	public void setFacial_feature_location(String facial_feature_location) {
		this.facial_feature_location = facial_feature_location;
	}
	public double getFace_range() {
		return face_range;
	}
	public void setFace_range(double face_range) {
		this.face_range = face_range;
	}
	public double getMouse_ratio() {
		return mouse_ratio;
	}
	public void setMouse_ratio(double mouse_ratio) {
		this.mouse_ratio = mouse_ratio;
	}

}
