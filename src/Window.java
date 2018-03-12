import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class Window extends JFrame{
	
	static public Chart currentChart;
	
	private String currency = "USD";
	
	private JComboBox curList = new JComboBox();
	private JComboBox modeList = new JComboBox();
	
	private JPanel menu = new JPanel();
	private JPanel chart = new JPanel();
	private JPanel info = new JPanel();
	private JPanel container = new JPanel();
	
	private JLabel curValue = new JLabel("BTC : ");
	private JLabel bitValue = new JLabel("BTC : ");
	private JLabel usdValue = new JLabel("USD : ");
	
	// Info labels
	private JLabel rankInfo = new JLabel("| Rank : ");
	private JLabel btcInfo = new JLabel(" | BTC : ");
	private JLabel usdInfo = new JLabel(" | USD : ");
	private JLabel volume24hInfo = new JLabel(" | 24h Volume : ");
	private JLabel change24hInfo = new JLabel(" | 24h Change : ");
	
	private JLabel curNameInfo = new JLabel("NULL");
	private JLabel rankValueInfo = new JLabel("NULL");
	private JLabel btcValueInfo = new JLabel("NULL");
	private JLabel usdValueInfo = new JLabel("NULL");
	private JLabel volume24hValueInfo = new JLabel("NULL");
	private JLabel change24hValueInfo = new JLabel("NULL");
	
	private JTextField curFieldValue = new JTextField();
	private JTextField bitFieldValue = new JTextField();
	private JTextField usdFieldValue = new JTextField();
	
	private JButton vertcoin = new JButton("Vertcoin");
	private JButton bitcoin = new JButton("Bitcoin");
	private JButton litecoin = new JButton("Litecoin");
	private JButton convert = new JButton("Convert");

	// Default constructor
	public Window() {
		
		this.setSize(420, 380);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		AddPanel();
		
		System.out.println("Window create !");
	}
	
	// Overload constructors
	public Window(int width, int height) {
		
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		AddPanel();
		
		System.out.println("Window create !");
	}
	
	public void AddPanel() {
		
		currentChart = new Chart("Bitcoin", "");
		
		chart.setPreferredSize(new Dimension(this.getWidth(), 50));
		menu.setPreferredSize(new Dimension(this.getWidth(), 50));
		info.setPreferredSize(new Dimension(this.getWidth(), 50));
		
		// --------------------- Menu Panel -------------------- //
		menu.setBorder(BorderFactory.createLineBorder(Color.black));
		menu.add(modeList);
		menu.add(curList);
		menu.add(vertcoin);
		menu.add(bitcoin);
		menu.add(litecoin);
		
		vertcoin.addActionListener(new VertcoinListener());
		bitcoin.addActionListener(new BitcoinListener());
		litecoin.addActionListener(new LitecoinListener());
		
		curList.addItem("USD");
		curList.addItem("EUR");
		curList.addItemListener(new CurList());
		
		modeList.addItem("Day");
		modeList.addItem("Night");
		modeList.addItemListener(new ModeList());
		// ----------------------------------------------------- //
		
		// ---------------------- Chart Panel ------------------- //
		
		convert.addActionListener(new ConvertListener());
		chart.setBorder(BorderFactory.createLineBorder(Color.black));
		curFieldValue.setPreferredSize(new Dimension(100, 20));
		bitFieldValue.setPreferredSize(new Dimension(100, 20));
		usdFieldValue.setPreferredSize(new Dimension(100, 20));
		
		//chart.add(chartValue, BorderLayout.WEST);
		chart.add(curValue);
		chart.add(curFieldValue);
		chart.add(convert);
		chart.add(bitValue);
		chart.add(bitFieldValue);
		chart.add(usdValue);
		chart.add(usdFieldValue);
		// ------------------------------------------------------ //
		
		// ---------------------- Info Panel -------------------- //
		info.setBorder(BorderFactory.createLineBorder(Color.black));
		info.add(curNameInfo);
		info.add(rankInfo); info.add(rankValueInfo);
		info.add(btcInfo); info.add(btcValueInfo);
		info.add(usdInfo); info.add(usdValueInfo);
		info.add(volume24hInfo); info.add(volume24hValueInfo);
		info.add(change24hInfo); info.add(change24hValueInfo);
		// ------------------------------------------------------ //
		
		// ---------------------- Container Panel --------------- //
		container.add(menu, BorderLayout.NORTH);
		container.add(chart);
		container.add(info);
		// ------------------------------------------------------ //
		
		dayMode();
		
		this.setContentPane(container);
		this.setVisible(true);
		
		setNewCurrency("Bitcoin", "https://api.coinmarketcap.com/v1/ticker/bitcoin/?convert=EUR");
	}
	
	private void resetField() {
		curFieldValue.setText("");
		bitFieldValue.setText("");
		usdFieldValue.setText("");
	}
	
	private void updateInfo() {
		curNameInfo.setText(currentChart.getName());
		rankValueInfo.setText(Integer.toString(currentChart.getRank()));
		btcValueInfo.setText(String.format("%.5f", currentChart.getCurrentBtcValue()) + "B");
		volume24hValueInfo.setText(String.format("%.2f", currentChart.getVolume24h()) + "$");
		change24hValueInfo.setText(String.format("%.2f", currentChart.getChange24h()) + "%");
		
		if(currency == "USD")
			usdValueInfo.setText(String.format("%.4f", currentChart.getCurrentUsdValue()) + "$");
		else if(currency == "EUR")
			usdValueInfo.setText(String.format("%.4f", currentChart.getCurrentEurValue()) + "€");
		else
			System.out.println("Bad currency !");
	}

	private void setNewCurrency(String curName, String curUrlName) {
		currentChart = new Chart(curName, curUrlName);
		currentChart.updateChart();
		currentChart.printInfo();
		resetField();
		updateInfo();
	}
	
	private void dayMode() {
		container.setBackground(Color.black);
		info.setBackground(Color.green);
		chart.setBackground(Color.green);
		menu.setBackground(Color.green);
	}
	
	private void nightMode() {
		container.setBackground(Color.black);
		info.setBackground(Color.gray);
		chart.setBackground(Color.gray);
		menu.setBackground(Color.gray);
	}
	
	class VertcoinListener implements ActionListener {
	
		@Override
		public void actionPerformed(ActionEvent e) {
			curValue.setText("VTC : ");	
			setNewCurrency("Vertcoin", "https://api.coinmarketcap.com/v1/ticker/vertcoin/?convert=EUR");
		}
	}
	
	class BitcoinListener implements ActionListener{
	
		@Override
		public void actionPerformed(ActionEvent e) {
			curValue.setText("BTC : ");
			setNewCurrency("Bitcoin", "https://api.coinmarketcap.com/v1/ticker/bitcoin/?convert=EUR");
		}
	}
	
	class LitecoinListener implements ActionListener{
	
		@Override
		public void actionPerformed(ActionEvent e) {
			curValue.setText("LTC : ");
			setNewCurrency("Litecoin", "https://api.coinmarketcap.com/v1/ticker/litecoin/?convert=EUR");
		}	
	}
	
	class ConvertListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			float convertValueToUsd = 0.0f;
			float convertValueToBtc = currentChart.getCurrentBtcValue() * Integer.valueOf(curFieldValue.getText());
			
			if(currency == "USD")
				convertValueToUsd = currentChart.getCurrentUsdValue() * Integer.valueOf(curFieldValue.getText());
			else if(currency == "EUR")
				convertValueToUsd = currentChart.getCurrentEurValue() * Integer.valueOf(curFieldValue.getText());
			else
				System.out.println("Bad currency !");
			
			bitFieldValue.setText(String.valueOf(convertValueToBtc));
			usdFieldValue.setText(String.valueOf(convertValueToUsd));
		}
	}
	
	class CurList implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {		
			currency = e.getItem().toString();
			usdValue.setText(e.getItem().toString() + " : ");
			usdInfo.setText(" | " + e.getItem().toString() + " : ");
			updateInfo();
		}
		
	}
	
	class ModeList implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {		
			if(e.getItem().toString() == "Day")
				dayMode();
			else if(e.getItem().toString() == "Night")
				nightMode();
		}
		
	}
}