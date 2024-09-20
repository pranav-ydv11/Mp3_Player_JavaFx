package application;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Mp3PlayerCon implements Initializable{
	
	@FXML
	private ComboBox<String> speedBut;
	
	@FXML
	private Slider mySlider;
	
	@FXML
	private Label myLabel;
	
	@FXML
	private ProgressBar myProgress;
	
	@FXML
	private Button playBut, pauseBut, prevBut, nextBut, resetBut;
	
	private File directory;
	private File[] files;
	private ArrayList<File> songs;
	private int songNumber;
	private double[] speed= {0.25, 0.50, 0.75, 1, 1.25, 1.50, 1.75, 2.0};
	
	private Timer timer;
	private TimerTask task;
	private boolean running;
	
	
	
	
    private Media media;
    private MediaPlayer mediaPlayer;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
    
		songs=new ArrayList<File>();
		directory = new File("music");
		
		files=directory.listFiles();
		if(files!=null) {
			
			for(File file:files) {
			songs.add(file);
			System.out.println(file);
			}
			
		}
		
    	
		
		media=new Media(songs.get(songNumber).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		myLabel.setText(songs.get(songNumber).getName());
		
		for(int i=0;i<speed.length; i++) {
			speedBut.getItems().add(Double.toString(speed[i]));
		}
		
		mySlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                 
				mediaPlayer.setVolume(mySlider.getValue()*0.1);
//				 System.out.println("Slider Value: " + mySlider.getValue());
//			        mediaPlayer.setVolume(mySlider.getValue() * 0.1);
//			        System.out.println("Media Player Volume: " + mediaPlayer.getVolume());
			}
			
		})	;	
		
		myProgress.progressProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
								
			}
			
		});
		
	}
	
	public void play() {
		
		beginTimer();
		changeSpeed(null);
		mediaPlayer.play();
		
	}
	public void pause() {
		cancelTimer();
		mediaPlayer.pause();
	}
	public void prev() {
		if(songNumber>0) {
			songNumber--;
			
			mediaPlayer.stop();
			
			if(running) {
				cancelTimer();
			}
			
			media=new Media(songs.get(songNumber).toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			myLabel.setText(songs.get(songNumber).getName());
			
			play();
		}else {
			songNumber=songs.size()-1;
            mediaPlayer.stop();
			
			media=new Media(songs.get(songNumber).toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			myLabel.setText(songs.get(songNumber).getName());
			
			play();
		}
	}
	public void reset() {
		
		myProgress.setProgress(0);
		mediaPlayer.seek(Duration.seconds(0));
		
	}
	public void next() {
		if(songNumber<songs.size()-1) {
			songNumber++;
			
			mediaPlayer.stop();
			if(running) {
				cancelTimer();
			}
			
			media=new Media(songs.get(songNumber).toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			myLabel.setText(songs.get(songNumber).getName());
			
			play();
		}else {
			songNumber=0;
            mediaPlayer.stop();
			
			media=new Media(songs.get(songNumber).toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			myLabel.setText(songs.get(songNumber).getName());
			
			play();
		}
		
	}
	public void changeSpeed(ActionEvent event) {
		
		if(speedBut.getValue()==null) {
			mediaPlayer.setRate(1);
		}else {
		mediaPlayer.setRate(Double.parseDouble(speedBut.getValue())*1);
		}
		
	}
	
	public void beginTimer() {
	    timer = new Timer();
	    task = new TimerTask() {
	        @Override
	        public void run() {
	            running = true;
	            try {
	                if (mediaPlayer != null && media != null) {
	                    double current = mediaPlayer.getCurrentTime().toSeconds();
	                    double end = media.getDuration().toSeconds();
	                    myProgress.setProgress(current / end);

	                    if (current / end == 1) {
	                        cancelTimer();
	                    }
	                } else {
	                    System.err.println("Media player or media is null.");
	                    cancelTimer();
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    };

	    timer.scheduleAtFixedRate(task, 1000, 1000);
	}

	
	public void cancelTimer() {
		
		running = false;
		timer.cancel();
		
	}
	

}
