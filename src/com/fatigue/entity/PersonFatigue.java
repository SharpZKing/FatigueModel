package com.fatigue.entity;

import java.util.Date;

public class PersonFatigue {
	//�û�id
	private int userId;
	//��⿪ʼʱ��
	private Date start_time;
	//�����������ʱ��״̬
	private int state;
	//���Ե�����
	private Date test_time;
	//�ӿ�ʼ�˶�ʱ�䵽��ǰʱ�̵���֡��
	private int fn_count;
	//�ӿ�ʼ�˶�����ǰʱ����գ�۴���
	private int blink_count;
	//��ǰʱ���������ߴ�
	private double left_eyelid;
	//��ǰʱ���������ߴ�
	private double right_eyelid;
	//�������
	private double left_eye_range;
	//�������
	private double right_eye_range;
	//��ͫ�����
	private double left_pupil_range;
	//��ͫ�����
	private double right_pupil_range;
	//����ת���ٶ�
	private double line_sight_vel;
	//����������ͷ����
	private double distance;
	//���λ�ã��۾������ӡ���͡�üë�����䣩
	private String facial_feature_location;
	//���������С
	private double face_range;
	//����ſ������
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
