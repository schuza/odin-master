package net.floodlightcontroller.odin.applications;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.tools.javac.util.List;

import net.floodlightcontroller.odin.master.OdinApplication;

public class SimpleSpectralScan extends OdinApplication {

	private final int INTERVAL = 5000;

	public enum Foo {
		a, b, v, f;
	}

	public String[] channels_24 = {
		"2412",
		"2417",
		"2422",
		"2427",
		"2432",
		"2437",
		"2442",
		"2447",
		"2452",
		"2457",
		"2462"
//		"2467",
//		"2472",
//		"2484"
		};

	public String[] channels_5 = {
			"5180" // just the first channel, ignore the others
			};

	@Override
	public void run() {


		while (true) {
			try {
				Thread.sleep(INTERVAL);

				/*
				 * Probe each AP to get the spectral scan data from the APs
				 */
				for (InetAddress agentAddr: getAgents()) {
					byte[] ssdata = getSpectralScanFromAgent(agentAddr);


					HashMap<String, ArrayList<Double>> spectralMap = new HashMap<String, ArrayList<Double>>();
					spectralMap = computeSS(spectralMap, ssdata, agentAddr);
					for (String channel: channels_24) {
						ArrayList<Double> a = spectralMap.get(channel);
						double maxval = max(a);
						double meanval = mean(a);
						System.out.println("Channel: " + channel + " max: " + maxval + " mean: " + meanval);
					}
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

    public static double max(ArrayList<Double> a) {
        double max = -200;
        for (Double val: a) {
            if (val > max) max = val;
        }
        return max;
    }

    public static double mean(ArrayList<Double> a) {
    	double sum = 0;
        for (Double val: a) {
            sum += val;
        }

        return sum / a.size();
    }


	private HashMap<String, ArrayList<Double>> computeSS(HashMap<String, ArrayList<Double>> spectralMap, byte[] spectralData, InetAddress agentAddr) throws IOException {

		if (spectralData == null)
			return null;

		File file = new File("/home/odin/spectraldata/"+agentAddr);

		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		FileOutputStream fos = new FileOutputStream("/home/odin/spectraldata/"+agentAddr);
		fos.write(spectralData);
		fos.close();

		System.out.println(spectralData.length);
		System.out.println("/home/odin/ath9k-spectral-scan/spectral " + "/home/odin/spectraldata/" + agentAddr);
		Process p = Runtime.getRuntime().exec("/home/odin/ath9k-spectral-scan/spectral " + "/home/odin/spectraldata/" + agentAddr);


		BufferedReader reader = new BufferedReader(
				new InputStreamReader(p.getInputStream()));
		String line = null;//reader.readLine();
		while ( (line = reader.readLine()) != null) {

			String[] csv = line.split(" ");
			String freq = csv[0];
			String rssi = csv[2];

			if (!spectralMap.containsKey(freq))
				spectralMap.put(freq, new ArrayList<Double>());

			spectralMap.get(freq).add(Double.valueOf(rssi));

		}


		return spectralMap;

	}



}
