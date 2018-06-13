import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import org.json.*;
import org.apache.derby.*;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import net.miginfocom.swing.MigLayout;

import javax.swing.JTable;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JTable table;

	String[] columnNames = {"Miasto",
							"Temperatura",
							"Typ pogody",
							"Opis",
							"Wilgoæ",
							"Prêdkoœæ wiatru",
							"Ciœnienie"};
	private JTextField miastoField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public class WeatherClass{
		public String getTemp() {
			return temp;
		}
		public String getWeatherType() {
			return weatherType;
		}
		public String getDescription() {
			return description;
		}
		public String getHumidity() {
			return humidity;
		}
		public String getWind() {
			return wind;
		}
		public String getPressure() {
			return pressure;
		}
		private String jsonStr;
		private String temp;
		private String weatherType;
		private String description;
		private String humidity;
		private String wind;
		private String pressure;
		public WeatherClass(String url){
			try {
				URL testUrl = new URL(url);
				BufferedReader in = new BufferedReader(new InputStreamReader(testUrl.openStream()));
				jsonStr = in.readLine();
				in.close();
				JSONObject obj = new JSONObject(jsonStr);
				
				
				weatherType = obj.getJSONArray("weather").getJSONObject(0).getString("main");
				description = obj.getJSONArray("weather").getJSONObject(0).getString("description");
				temp = String.valueOf(Double.parseDouble(obj.getJSONObject("main").getString("temp")) - 273.15);
				humidity = obj.getJSONObject("main").getString("humidity");
				wind = obj.getJSONObject("wind").getString("speed");
				pressure = obj.getJSONObject("main").getString("pressure");
				
			} catch (IOException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		public String print(){
			
			return temp+" "+weatherType+" "+description+" "+humidity+" "+wind+" "+pressure;
		}
	}
	
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 100, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		String data[][] = {{"Wroclaw", "typ", "losowe", "trzy", "siedem","szesc","siedem"}};
		JPanel tablePanel = new JPanel(new BorderLayout());
		JTable table = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 13, 958, 300);
		//table.setBounds(12, 25, 958, 240);
		
		contentPane.add(scrollPane);
		
		miastoField = new JTextField();
		miastoField.setBounds(32, 353, 222, 28);
		contentPane.add(miastoField);
		miastoField.setColumns(10);
		
		JLabel lblMiasto = new JLabel("Miasto:");
		lblMiasto.setBounds(22, 324, 56, 16);
		contentPane.add(lblMiasto);
		
		JButton btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String APPID = "014b5d4258bc8726f9bda9865f5a7a66";
				WeatherClass wth = new WeatherClass("http://api.openweathermap.org/data/2.5/weather?q="+miastoField.getText()+",pl&APPID="+APPID);
				
				WeatherDBClass.addToDb(miastoField.getText(), wth.getTemp(), wth.getWeatherType(), wth.getDescription(), wth.getHumidity(), wth.getWind(), wth.getPressure());
				//data
			}
		});
		btnDodaj.setBounds(266, 355, 97, 25);
		contentPane.add(btnDodaj);
		//contentPane.add(table);
		
		//String city = "Wroclaw";
		//WeatherClass wth = new WeatherClass("http://api.openweathermap.org/data/2.5/weather?q="+city+",pl&APPID="+APPID);
		
		//WeatherDBClass.addToDb(city, wth.getTemp(), wth.getWeatherType(), wth.getDescription(), wth.getHumidity(), wth.getWind(), wth.getPressure());
		
		
		//
		// skladnia http://api.openweathermap.org/data/2.5/weather?q=Miasto,pl&APPID=XXXXXXXX
		
		
		
	}
}
