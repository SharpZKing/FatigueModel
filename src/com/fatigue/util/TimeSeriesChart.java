package com.fatigue.util;

import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import javax.print.attribute.standard.NumberUpSupported;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.fatigue.entity.PersonFatigueResult;

public class TimeSeriesChart {
	
	ChartPanel frame1;
	
	PersonFatigueResult personFatigueResult;
	
    public TimeSeriesChart(PersonFatigueResult personFatigueResult){
    	this.personFatigueResult = personFatigueResult;
        XYDataset xydataset = createDataset();  
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("ƣ�ͳ̶�չʾ", "ʱ��", "ƣ��ˮƽ",xydataset, true, true, true);  
        XYPlot xyplot = (XYPlot) jfreechart.getPlot(); 
        NumberAxis dateaxis =  (NumberAxis) xyplot.getRangeAxis();
        dateaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
       /* DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();  
        dateaxis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));*/
        frame1=new ChartPanel(jfreechart,true);  
        dateaxis.setLabelFont(new Font("����",Font.BOLD,14));         //ˮƽ�ײ�����  
        dateaxis.setTickLabelFont(new Font("����",Font.BOLD,12));  //��ֱ����  
        ValueAxis rangeAxis=xyplot.getRangeAxis();//��ȡ��״  
        rangeAxis.setLabelFont(new Font("����",Font.BOLD,15));  
        jfreechart.getLegend().setItemFont(new Font("����", Font.BOLD, 15));  
        jfreechart.getTitle().setFont(new Font("����",Font.BOLD,20));//���ñ�������  
  
    }
    
    private  XYDataset createDataset() {  
		/*TimeSeries timeseries = new TimeSeries("PERCLOS && BN",  
		        org.jfree.data.time.Month.class);*/
    	XYSeries timeseries = new XYSeries("FATIGUE");
		
		List<Integer> numbers = personFatigueResult.getNumbers();
		List<Double> list2 = personFatigueResult.getFatigueLevels();
		for(int i=0; i<numbers.size(); i++){
			//timeseries.add(i+1, list2.get(i));
			timeseries.add(i, list2.get(i));
		}
		
		/*timeseries.add(new Month(2, 2001), 181.80000000000001D);  
		timeseries.add(new Month(3, 2001), 167.30000000000001D);  
		timeseries.add(new Month(4, 2001), 153.80000000000001D);  
		timeseries.add(new Month(5, 2001), 167.59999999999999D);  
		timeseries.add(new Month(6, 2001), 158.80000000000001D);  
		timeseries.add(new Month(7, 2001), 148.30000000000001D);  
		timeseries.add(new Month(8, 2001), 153.90000000000001D);  
		timeseries.add(new Month(9, 2001), 142.69999999999999D);  
		timeseries.add(new Month(10, 2001), 123.2D);  
		timeseries.add(new Month(11, 2001), 131.80000000000001D);  
		timeseries.add(new Month(12, 2001), 139.59999999999999D);  
		timeseries.add(new Month(1, 2002), 142.90000000000001D);  
		timeseries.add(new Month(2, 2002), 138.69999999999999D);  
		timeseries.add(new Month(3, 2002), 137.30000000000001D);  
		timeseries.add(new Month(4, 2002), 143.90000000000001D);  
		timeseries.add(new Month(5, 2002), 139.80000000000001D);  
		timeseries.add(new Month(6, 2002), 137D);  
		timeseries.add(new Month(7, 2002), 132.80000000000001D);*/
		
		
		//TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		XYSeriesCollection timeseriescollection = new XYSeriesCollection();
		timeseriescollection.addSeries(timeseries);  
		return timeseriescollection;  
     }  
    
     public ChartPanel getChartPanel(){  
    	 return frame1;          
     } 
	
}
