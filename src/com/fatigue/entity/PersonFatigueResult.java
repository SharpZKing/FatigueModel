package com.fatigue.entity;

import java.util.Date;
import java.util.List;

public class PersonFatigueResult {
	
	//�û�ID
	private int userId;
	
	//ƣ�Ϳ�ʼ��ʱ��
	private Date fatigue_start_time;
	
	//ƣ�͵ĳ̶�
	//private int level;  //1����  2���ƣ��  3�ض�ƣ��
	private List<Integer> levels;  //1����  2���ƣ��  3�ض�ƣ��
	
	//ƣ�͵ĳ̶�
	private List<Double> fatigueLevels;
	
	//���Ե�����
	private Date test_time;
	
	//���ݴ���
	List<Integer> numbers;
	
	public PersonFatigueResult(int userId, Date test_time){
		this.userId = userId;
		this.test_time  = test_time;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getFatigue_start_time() {
		return fatigue_start_time;
	}

	public void setFatigue_start_time(Date fatigue_start_time) {
		this.fatigue_start_time = fatigue_start_time;
	}

	/*public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}*/

	public List<Double> getFatigueLevels() {
		return fatigueLevels;
	}

	public List<Integer> getLevels() {
		return levels;
	}

	public void setLevels(List<Integer> levels) {
		this.levels = levels;
	}

	public void setFatigueLevels(List<Double> fatigueLevels) {
		this.fatigueLevels = fatigueLevels;
	}

	public Date getTest_time() {
		return test_time;
	}

	public void setTest_time(Date test_time) {
		this.test_time = test_time;
	}

	public List<Integer> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<Integer> numbers) {
		this.numbers = numbers;
	}
	
	
}
