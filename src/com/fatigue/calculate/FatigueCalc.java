package com.fatigue.calculate;

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.fatigue.entity.PersonFatigue;
import com.fatigue.entity.PersonFatigueResult;
import com.fatigue.util.FatigueStatics;
import com.fatigue.util.JDBCUtil;
import com.fatigue.util.TimeSeriesChart;

public class FatigueCalc {
	
	/**
	 * 1. ������Դ��ȡ����
	 * 2. �������ݳɰ�ʱ�������ʵ��
	 * @return
	 */
	public static List<PersonFatigue> formatFatigueDatas(int userId, Date date, int state){
		
		Connection conn = JDBCUtil.getConnection();
		String sqlQuery = "select * from face_origin_params where userId = ? and test_time = ? and state = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<PersonFatigue> personDatas = null ;
		try {
			ps = (PreparedStatement) conn.prepareStatement(sqlQuery);
			ps.setInt(1,userId);
			ps.setDate(2, new java.sql.Date(date.getTime()));
			ps.setInt(3, state);
			rs = (ResultSet) ps.executeQuery();
			personDatas = new ArrayList<PersonFatigue>();
			PersonFatigue pf ;
			while(rs.next()){
				pf = new PersonFatigue();
				pf.setUserId(rs.getInt(2));
				pf.setLeft_eyelid(rs.getDouble(3));
				pf.setRight_eyelid(rs.getDouble(4));
				pf.setStart_time(rs.getDate(5));
				pf.setBlink_count(rs.getInt(6));
				pf.setFn_count(rs.getInt(7));
				pf.setTest_time(date);
				pf.setState(state);
				
				personDatas.add(pf);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JDBCUtil.freeAll(conn, ps, rs);
		return personDatas;
	}
	
	/**
	 * 1. ��ʵ��������Ϊ���룬����ģ�ͽ��м�������յ�ֵ
	 * 2. ��ֵ����ֵ�Ƚϣ�Ȼ��ó�ƣ�ͳ̶�
	 * @param persons
	 * @return
	 */
	public static PersonFatigueResult calcFatigueWithLevel(int userId,Date testTime){
		
		//�����ƣ��״̬�µ�������ֵ��С
		//double eyelid_threshold = normalEyelid(persons);
		double eyelid_threshold = normalEyelid(formatFatigueDatas(userId, testTime, 1));
		
		List<PersonFatigue> persons = formatFatigueDatas(userId, testTime, 2);
		
		//ʵʱ�ж�������С��������ֵ�ľ��루ʱ�䴰10��һ�ο��жϳ�ƣ�Ϳ�ʼ��ʱ�䣩
		//PersonFatigue[] personArray = (PersonFatigue[]) persons.toArray();
		PersonFatigue[] personArray = new PersonFatigue[persons.size()];
		for(int k=0; k<persons.size();k++){
			personArray[k] = persons.get(k);
		}
		
		PersonFatigueResult pfr = new PersonFatigueResult(personArray[0].getUserId(),personArray[0].getTest_time());
		int perclosFn = 0;
		int SUM = FatigueStatics.FATIGUE_P80_MAXTIME * FatigueStatics.FATIGUE_FN;
		for(int i=1; i<=personArray.length; i++){
			if( personArray[i-1].getLeft_eyelid() < eyelid_threshold ){
				perclosFn++;
			}
			if( i%SUM==0 ){
				if( perclosFn*1.0/SUM > FatigueStatics.FATIGUE_P80_MIN){
					//�ҵ�ƣ�ͳ��ֵ�ʱ���
					int happen = i - SUM/2;
					
					long start = personArray[0].getStart_time().getTime();
					long length = happen*(int)1000/(FatigueStatics.FATIGUE_FN);
					long start_happen = start + length;
					//ƣ�ͳ��ֵĿ�ʼʱ��
					pfr.setFatigue_start_time(new Date(start_happen)); 
					
					break;
				}else{
					perclosFn = 0;
				}
			}
		}
		
		System.out.println(pfr.getUserId()+"=-="+pfr.getFatigue_start_time().toString()); //��ӡƣ�Ϳ�ʼ��ʱ���
		
		if( personArray.length < SUM ){
			if( perclosFn*1.0/personArray.length > FatigueStatics.FATIGUE_P80_MIN ){
				//�ҵ�ƣ�ͳ��ֵ�ʱ���
				int happen = personArray.length;
				
				long start = personArray[0].getStart_time().getTime();
				long length = happen*(int)1000/(FatigueStatics.FATIGUE_FN);
				long start_happen = start + length;
				//ƣ�ͳ��ֵĿ�ʼʱ��
				pfr.setFatigue_start_time(new Date(start_happen)); 
				
			}
		}
		
		//����ƣ�͵�ˮƽÿ60��һ��
		int No_ = 1;
		int per_min = 0;
		int per_max = 161;
		int bn_min = 1;
		int bn_max = 10;
		double fatigue_avg_min = 0.8*(2*0.1 - 0.4)*1.0/(0.4) + 0.2*(2*7 - bn_max - bn_min)*1.0/(bn_max - bn_min);
		double fatigue_avg_max = 0.8*(2*0.2 - 0.4)*1.0/(0.4) + 0.2*(2*4 - bn_max - bn_min)*1.0/(bn_max - bn_min);
		
		//double fatigue_avg_min = 0.8*FatigueStatics.FATIGUE_P80_MIN + 0.2*FatigueStatics.FATIGUE_BN;
		//double fatigue_avg_max = 0.8*FatigueStatics.FATIGUE_P80_MAX + 0.2*FatigueStatics.FATIGUE_BN;
		perclosFn = 0;
		List<Double> levels = new ArrayList();
		List<Integer> levelState = new ArrayList<>();
		List<Integer> numbers = new ArrayList<>();
		pfr.setFatigueLevels(levels);
		pfr.setLevels(levelState);
		pfr.setNumbers(numbers);
		double tmp = 0.0;
		DecimalFormat df = new DecimalFormat("#.00");
		for(int k=0; k<personArray.length; k++){
			if(personArray[k].getLeft_eyelid() < eyelid_threshold){
				perclosFn ++;
			}
			if( k%(FatigueStatics.FATIGUE_FN*60)==0 && k!=0){
//				System.out.print("PERCLOS��"+perclosFn+" ");
//				System.out.println("Blink��"+(personArray[k].getBlink_count()-personArray[k-FatigueStatics.FATIGUE_FN*60].getBlink_count()));
				/*tmp = (perclosFn*1.0)/(FatigueStatics.FATIGUE_FN*60)*0.8 
						+ (personArray[k].getBlink_count()-personArray[k-FatigueStatics.FATIGUE_FN*60].getBlink_count())*0.2;*/
				tmp = ( (2*(perclosFn*1.0)/(FatigueStatics.FATIGUE_FN*60)-0.4)/0.4 )*0.8 
						+ ( (2.0*(personArray[k].getBlink_count()-personArray[k-FatigueStatics.FATIGUE_FN*60].getBlink_count())-bn_max-bn_min)/(bn_max-bn_min) )*0.2;
				//System.out.println(tmp);
				if(tmp<fatigue_avg_min){
					levelState.add(1);
				}else if(tmp>fatigue_avg_min && tmp<fatigue_avg_max){
					levelState.add(2);
				}else if( tmp>fatigue_avg_max ){
					levelState.add(3);
				}
				
				levels.add(Double.parseDouble(df.format(tmp - fatigue_avg_min)));
				numbers.add(No_);
				No_++;
				perclosFn = 0;
			}
		}	
		if(personArray.length%(FatigueStatics.FATIGUE_FN*60)!=0){
			int tp = personArray.length/(FatigueStatics.FATIGUE_FN*60);
			tmp = (perclosFn*1.0)/(FatigueStatics.FATIGUE_FN*60)*0.8 
					+ (personArray[personArray.length-1].getBlink_count() 
							- personArray[tp*FatigueStatics.FATIGUE_FN*60].getBlink_count())*0.2;
			
			if(tmp<fatigue_avg_min){
				levelState.add(1);
			}else if(tmp>fatigue_avg_min && tmp<fatigue_avg_max){
				levelState.add(2);
			}else if( tmp>fatigue_avg_max ){
				levelState.add(1);
			}
			levels.add(Double.parseDouble(df.format(tmp - fatigue_avg_min)));
			numbers.add(No_);
			No_++;
		}
		
		return pfr;
		
	}
	
	/**
	 * 
	 * @param persons
	 * @return
	 */
	public static double normalEyelid(List<PersonFatigue> persons){
		int count = 0;
		double sum = 0.0;
		for(int i=0; i<persons.size(); i++){
			if(i+1<persons.size()){
				if( Math.abs(persons.get(i).getLeft_eyelid()) - Math.abs(persons.get(i+1).getLeft_eyelid()) <= 2 ){
					if( Math.abs(persons.get(i).getLeft_eyelid()) > Math.abs(persons.get(i+1).getLeft_eyelid())){
						sum += Math.abs(persons.get(i).getLeft_eyelid());
						count++;
					}
				}
				
			}else{
				if( Math.abs(persons.get(i).getLeft_eyelid()) - Math.abs(persons.get(i-1).getLeft_eyelid()) <= 2 ){
					if( Math.abs(persons.get(i).getLeft_eyelid()) > Math.abs(persons.get(i-1).getLeft_eyelid())){
						sum += Math.abs(persons.get(i).getLeft_eyelid());
						count++;
					}
				}
			}
			
		}
		
		sum = sum/count;
		
		double eyelid_threshold = sum * 0.2;
		return eyelid_threshold;
	}
	
	/* ************************** ����ģ������ ************************************ */
	//��ƣ��״̬�� ����ģ��
	public static void insertNornalDatas(int userId){
		System.out.println("Insert normal datas......");
		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		
		String sql = null;
		try {
			//1�û�ID, 2�������ߴ� , 3�������ߴ�, 4��ʼʱ��, 5գ�۴���, 6(��ʼ�˶�ʱ�䵽��ǰʱ�̵���֡��),7 ���Զ�ϵͳʱ��, 8״̬1��ƣ������
			sql = "insert into face_origin_params (userId,left_eyelid, right_eyelid, start_time, blink_count,fn_count,test_time,state)"
					+ " values (?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			Random random = new Random();
			Calendar calendar = Calendar.getInstance();
			calendar.set(2017, 3, 25, 10, 0, 0);
			int tmp = 0;
			for(int i=1; i<=200; i++){
				ps.setInt(1, userId);
				ps.setDouble(2, (random.nextInt(16)+95)*1.0/10 );
				ps.setDouble(3, (random.nextInt(16)+95)*1.0/10 );
				ps.setDate(4, new java.sql.Date(calendar.getTime().getTime()) );
				if(i%(FatigueStatics.FATIGUE_FN*60)==0 ){
					tmp = tmp + random.nextInt(4)+7; 
					ps.setInt(5, tmp );
				}else{
					ps.setInt(5, tmp );
				}
				ps.setInt(6, i);
				ps.setDate(7, new java.sql.Date(calendar.getTime().getTime()) );
				ps.setInt(8, 1);
				ps.addBatch();
			}	
			
			ps.executeBatch();
			ps.clearBatch();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.freeAll(conn, ps, null);
		}
		System.out.println("Insert normal datas completed!");
	
	}
	
	//ƣ�ͼ��ʱ���������ģ��
	public static void insertFatigueDatas(int userId){
		System.out.println("Insert Non-Normal datas......");
		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		
		String sql = null;
		int tempp = 0;
		try {
			//1�û�ID, 2�������ߴ� , 3�������ߴ�, 4��ʼʱ��, 5գ�۴���, 6(��ʼ�˶�ʱ�䵽��ǰʱ�̵���֡��),7 ���Զ�ϵͳʱ��, 8״̬1��ƣ������
			sql = "insert into face_origin_params (userId,left_eyelid, right_eyelid, start_time, blink_count,fn_count,test_time,state)"
					+ " values (?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			Random random = new Random();
			Calendar calendar = Calendar.getInstance();
			calendar.set(2017, 3, 25, 14, 0, 0);
			for(int i=1; i<=36000; i++){   //��ʼ��Сʱ������
				ps.setInt(1, userId);
				ps.setDouble(2, (random.nextInt(16)+95)*1.0/10 );
				ps.setDouble(3, (random.nextInt(16)+95)*1.0/10 );
				ps.setDate(4, new java.sql.Date(calendar.getTime().getTime()) );
				if(i%(FatigueStatics.FATIGUE_FN*60)==0){
					tempp = tempp + random.nextInt(4)+7; 
					ps.setInt(5, tempp );
				}else{
					ps.setInt(5, tempp );
				}
				ps.setInt(6, i);
				ps.setDate(7, new java.sql.Date(calendar.getTime().getTime()) );
				ps.setInt(8, 2);
				ps.addBatch();
				
				if(i%144==0){
					ps.executeBatch();
					ps.clearBatch();
				}
			}	
			if(36000%144!=0){
				ps.executeBatch();
				ps.clearBatch();
			}
			
			//1�û�ID, 2�������ߴ� , 3�������ߴ�, 4��ʼʱ��, 5գ�۴���, 6(��ʼ�˶�ʱ�䵽��ǰʱ�̵���֡��),7 ���Զ�ϵͳʱ��, 8״̬1��ƣ������
			for(int i=1; i<=72000; i++){   //�м�һСʱƣ�ͽ׶�����  random.nextInt(4)+1(1.0----4.0)      3��7��գ��
				ps.setInt(1, userId);
				ps.setDouble(2, (random.nextInt(100)+9)*1.0/10 );
				ps.setDouble(3, (random.nextInt(100)+9)*1.0/10 );
				ps.setDate(4, new java.sql.Date(calendar.getTime().getTime()) );
				if(i%(FatigueStatics.FATIGUE_FN*60)==0){
					tempp = tempp + random.nextInt(10)+1; 
					ps.setInt(5, tempp );
				}else{
					ps.setInt(5, tempp );
				}
				ps.setInt(6, i+36000);
				ps.setDate(7, new java.sql.Date(calendar.getTime().getTime()) );
				ps.setInt(8, 2);
				ps.addBatch();
				
				if(i%144==0){
					ps.executeBatch();
					ps.clearBatch();
				}
			}	
			if(72000%144!=0){
				ps.executeBatch();
				ps.clearBatch();
			}
			
			for(int i=1; i<=36000; i++){   //����Сʱ������
				ps.setInt(1, userId);
				ps.setDouble(2, (random.nextInt(16)+95)*1.0/10 );
				ps.setDouble(3, (random.nextInt(16)+95)*1.0/10 );
				ps.setDate(4, new java.sql.Date(calendar.getTime().getTime()) );
				if(i%(FatigueStatics.FATIGUE_FN*60)==0){
					tempp = tempp + random.nextInt(4)+7; 
					ps.setInt(5, tempp );
				}else{
					ps.setInt(5, tempp );
				}
				ps.setInt(6, i+108000);
				ps.setDate(7, new java.sql.Date(calendar.getTime().getTime()) );
				ps.setInt(8, 2);
				ps.addBatch();
				
				if(i%144==0){
					ps.executeBatch();
					ps.clearBatch();
				}
			}	
			if(36000%144!=0){
				ps.executeBatch();
				ps.clearBatch();
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.freeAll(conn, ps, null);
		}
		System.out.println("Insert Non-Normal datas completed!");
	}
	/* ************************** ����ģ�����ݽ��� ************************************ */
	
	public static void main(String[] args) {
		
		/*double fatigue_avg_min = 0.8*(2*0.1 - 0.4)*1.0/(0.4) + 0.2*(2*7 - 10 - 1)*1.0/(9);
		double fatigue_avg_max = 0.8*(2*0.2 - 0.4)*1.0/(0.4) + 0.2*(2*4 - 10 - 1)*1.0/(9);
		System.out.println(fatigue_avg_min+"==="+ fatigue_avg_max);*/
		
		/*insertNornalDatas(1);
		insertFatigueDatas(1);*/
		Calendar calendar = Calendar.getInstance();
		calendar.set(2017, 3, 25, 14, 0, 0);
		double threshold = normalEyelid(formatFatigueDatas(1, calendar.getTime(), 1));
		System.out.println("Threshold: "+threshold);
		PersonFatigueResult pfr = calcFatigueWithLevel(1, calendar.getTime());
		List<Double> list = pfr.getFatigueLevels();
		List<Integer> list2 = pfr.getLevels();
		for(int i=0; i<list.size(); i++){
			System.out.println(list.get(i)+"==="+list2.get(i));
		}
		
		
		JFrame frame=new JFrame("Java����ͳ��ͼ"); 
		frame.setLayout(new GridLayout(2,2,10,10));
		frame.add(new TimeSeriesChart(pfr).getChartPanel());    //�������ͼ
		frame.setBounds(50, 50, 800, 600);  
		frame.setVisible(true); 
		
		
		
		
	}
	
	
}
