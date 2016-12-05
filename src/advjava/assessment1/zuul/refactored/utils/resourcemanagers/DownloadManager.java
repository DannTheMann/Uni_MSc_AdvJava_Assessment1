package advjava.assessment1.zuul.refactored.utils.resourcemanagers;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class DownloadManager {

	public static final String URL = "http://www.danielandrews.co.uk/zuul";

	public static boolean downloadFile(String directory, String fileName, String destination) {

		String url = URL + (directory != "" || directory != null ? "/" + directory : "") + "/" + fileName;

		URL website;
		try {
			website = new URL(url);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			File f = new File(destination);
			if(!f.exists())
				f.mkdir();
			FileOutputStream fos = new FileOutputStream(destination + File.separator + fileName);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
			System.err.println("Downloaded file '" + fileName + "' to " + destination + ".");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}

}
