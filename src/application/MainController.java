package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sun.media.jfxmedia.events.NewFrameEvent;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class MainController implements Initializable
{
	@FXML private MediaView mv;
	private MediaPlayer mp;
	private Media me;
	@FXML private Button playbtn;
	@FXML private Button revbtn;
	@FXML private Button forbtn;
	@FXML private Slider ts ;
	@FXML private Slider vs;
     public void openMenu(ActionEvent event) {
		try {
		FileChooser chooser = new FileChooser();
		File file = chooser.showOpenDialog(null);
		me = new Media(file.toURI().toURL().toString());
		
		if(mp!=null)
		{
			mp.dispose();
		}
		mp = new MediaPlayer(me);
		mv.setMediaPlayer(mp);
		DoubleProperty width = mv.fitWidthProperty();
		DoubleProperty height = mv.fitHeightProperty();
		width.bind(Bindings.selectDouble(mv.sceneProperty(),"width"));
		height.bind(Bindings.selectDouble(mv.sceneProperty(),"height"));
		vs.setValue(mp.getVolume()*100);
		vs.valueProperty().addListener(new InvalidationListener() {
			
			@Override
			public void invalidated(Observable observable) {
				// TODO Auto-generated method stub
				mp.setVolume(vs.getValue()/100);
			}
		});
		mp.setOnReady(()->{
			// when player gets ready
			ts.setMin(0);
			ts.setMax(mp.getMedia().getDuration().toMinutes());
			ts.setValue(0);
			try {
				playbtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icon/play.png"))));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		// listener on player
		mp.currentTimeProperty().addListener(new ChangeListener<Duration>() {
			@Override
			public void changed(ObservableValue<? extends Duration> observable,
					Duration oldValue, Duration newValue) {
				// TODO Auto-generated method stub
				// coding
				Duration d = mp.getCurrentTime();
				ts.setValue(d.toMinutes());
				
			}
				
			});
		//time slider
		ts.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				if(ts.isPressed()){
					double val = ts.getValue();
					mp.seek(new Duration(val*60*1000));
				// TODO Auto-generated method stub
				}
			}
		});
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

    }

	
	public void play (ActionEvent event)
	{
		try
		{
			MediaPlayer.Status status = mp.getStatus();
			if(status==MediaPlayer.Status.PLAYING)
			{
			// pause
				mp.pause();
				playbtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icon/play.png"))));
			}
			else
			{
				mp.play();
				playbtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icon/pause.png"))));
			}
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
			playbtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icon/play.png"))));
			revbtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icon/rewind.png"))));
			forbtn.setGraphic(new ImageView(new Image(new FileInputStream("src/icon/fast-forward.png"))));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@FXML
    void forw(ActionEvent event) {
		double d = mp.getCurrentTime().toSeconds();
		d=d+10;
		mp.seek(new Duration(d*1000));
    }
	@FXML
    void rev(ActionEvent event) {
		double d = mp.getCurrentTime().toSeconds();
		d=d-10;
		mp.seek(new Duration(d*1000));
    }
	public void exitPlayer()
	{
		System.exit(0);
	}
}
