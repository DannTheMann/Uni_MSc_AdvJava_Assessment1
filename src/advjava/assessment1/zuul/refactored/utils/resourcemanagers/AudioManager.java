package advjava.assessment1.zuul.refactored.utils.resourcemanagers;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import advjava.assessment1.zuul.refactored.Main;
import advjava.assessment1.zuul.refactored.room.Room;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class AudioManager {

	public static final AudioManager am = new AudioManager();

	private MediaPlayer player;
	private Map<String, Media> songs;
	private boolean canPlay = true;
	private double volume;
	private boolean isMuted;
	
	private AudioManager() {
		songs = new TreeMap<>();
		songs.put("main", new Media(new File(Main.RESOURCE_MUSIC + File.separator + "main.mp3").toURI().toString()));
		volume = .3;
		isMuted = false;
	}

	public void playSong(Room room) {
		
		if(!songs.containsKey(room.getName()))
			loadSong(room.getName(), room.getTheme());
		
		if(player == null && canPlay){
			playSong(room.getName());
		}else{
			canPlay = false;
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(player.volumeProperty(), 0)));
			timeline.play();
			timeline.setOnFinished(e->{
				player.stop();
				canPlay = true;
				player = null;
				playSong(room.getName());
			});
		
		}

	}
	
	public void playSong(String key){
		if(songs.containsKey(key)){
			Media hit = songs.get(key);
			startSong(hit);
		}else{
			startSong(songs.get("main"));
		}
		
	}

	private void startSong(Media hit) {
		
		if(player != null || canPlay == false)
			return;
			
		player = new MediaPlayer(hit);
		player.setVolume(volume);
		player.play();
		if(isMuted)
			player.setMute(true);
		player.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				player.seek(Duration.ZERO);
			}
		});
	}

	public void pause() {
		player.pause();
	}

	public boolean loadSong(String name, String song) {
		
		if(name == null || song == null || name.equalsIgnoreCase("NA"))
			return false;
		
		try {
			String bip = new File(Main.RESOURCE_MUSIC + File.separator + song).toURI().toString();
			Media hit = new Media(bip);
			songs.put(name, hit);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean isMuted() {
		return player.isMute();
	}

	public void setMuted(boolean muted) {
		player.setMute(muted);
		isMuted = muted;
	}

	public void setVolume(double vol) {
		player.setVolume(vol);
		volume = vol;
	}

}
