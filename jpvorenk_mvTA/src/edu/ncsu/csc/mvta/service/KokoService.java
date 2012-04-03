package edu.ncsu.csc.mvta.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import android.util.Log;

import com.derekandbritt.koko.client.json.JSONEndpointException;
import com.derekandbritt.koko.client.json.KokoEndpoint;
import com.derekandbritt.koko.configuration.ConfigurationUtil;
import com.derekandbritt.koko.configuration.DataDefinition;
import com.derekandbritt.koko.configuration.DataType;
import com.derekandbritt.koko.emotion.EmotionType;
import com.derekandbritt.koko.emotion.EmotionVector;
import com.derekandbritt.koko.events.DataInstance;

public class KokoService {

    public KokoService() {
        
        try {
            // create the endpoint for your application (appID, emotionTypes, dataDefinitions)
            KokoEndpoint koko = createKokoEndpoint();
            
            // register a user with that application (username, firstName, lastName)
            // NOTE: all names must not include spaces
            koko.initialize("testUser", "test", "user");
            
            // inform Koko about how the user is feeling
            learnEvent(koko);
            printEmotionVector(koko.getCurrentEmotion());
            
            // inform Koko about what is happening to the user (no emotions)
            sendEvent(koko);
            printEmotionVector(koko.getCurrentEmotion());
            
            // predict how the user would feel if this event occured
            predictEmotion(koko);
        } catch (JSONEndpointException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    /**
     * This method registers your application with Koko by providing Koko
     * with all the necessary information.  The appID is directly tied to a specific
     * set of emotionTypes and dataDefinitions.  If you update either of those
     * sets then you will need to provide a new appID.
     * 
     * @return a KokoEndpoint configured for a specific application
     */
    private KokoEndpoint createKokoEndpoint() {
        
        HashSet<EmotionType> emotionTypes = new HashSet<EmotionType>();
        ArrayList<DataDefinition> dataDefinitions = new ArrayList<DataDefinition>();
        
        emotionTypes.add(EmotionType.LIKE);
        emotionTypes.add(EmotionType.DISLIKE);
        
        dataDefinitions.add(ConfigurationUtil.createEnumDataDefinition("weather", "cold,hot,foggy"));
        dataDefinitions.add(new DataDefinition("highScore", DataType.DOUBLE));
        dataDefinitions.add(new DataDefinition("attemptsToday", DataType.INT));
        dataDefinitions.add(new DataDefinition("timeToRespond", DataType.LONG));
        dataDefinitions.add(new DataDefinition("birthday", DataType.DATE));
        
        return new KokoEndpoint("vTA_Test_v01", emotionTypes, dataDefinitions);
    }
    
    /**
     * This is an example of how an application can inform Koko about the
     * emotional state of the user.  Koko uses this info to predict how the 
     * user will respond to other events in the future.
     * 
     * NOTE: the koko.sendEvent(...) includes the user's current emotion
     * 
     * @param koko the endpoint for your application
     * @throws JSONEndpointException
     */
    private void learnEvent(KokoEndpoint koko) throws JSONEndpointException {
        
        ArrayList<DataInstance> instances = new ArrayList<DataInstance>();
        
        for(DataDefinition definition : koko.getDataDefinitions()) {
            Serializable value = null;
        
            if(definition.getName().equals("weather")) {
                value = "hot";
            } else if(definition.getName().equals("highScore")) {
                value = 93.24;
            } else if(definition.getName().equals("attemptsToday")) {
                value = 3;
            } else if(definition.getName().equals("timeToRespond")) {
                value = (long)600000;
            } else if(definition.getName().equals("birthday")) {
                value = new Date();
            } else {
                throw new RuntimeException("Unknown data definition");
            }
        
            instances.add(new DataInstance(definition, value));
        }    
        
        // leave the second param null if the user has not provided any data
        koko.sendEvent(instances, EmotionType.LIKE);
    }

    /**
     * This is an example of how an application can inform Koko about what is 
     * happening in the application.  This info is used by Koko to keep track of
     * what the user is doing so it can better model their emotional state.
     * 
     * NOTE: the koko.sendEvent(...) excludes the user's current emotion because
     *       it is unknown to the application at this time.
     * 
     * @param koko the endpoint for your application
     * @throws JSONEndpointException
     */
    private void sendEvent(KokoEndpoint koko) throws JSONEndpointException {
        
        ArrayList<DataInstance> instances = new ArrayList<DataInstance>();
        
        for(DataDefinition definition : koko.getDataDefinitions()) {
            Serializable value = null;
        
            if(definition.getName().equals("weather")) {
                value = "cold";
            } else if(definition.getName().equals("highScore")) {
                value = 74.67;
            } else if(definition.getName().equals("attemptsToday")) {
                value = 5;
            } else if(definition.getName().equals("timeToRespond")) {
                value = (long)300000;
            } else if(definition.getName().equals("birthday")) {
                value = new Date();
            } else {
                throw new RuntimeException("Unknown data definition");
            }
        
            instances.add(new DataInstance(definition, value));
        }    
        
        // leave the second param null if the user has not provided any data
        koko.sendEvent(instances, null);
    }
    
    /**
     * This is an example of how an application can ask Koko how it thinks the
     * user will react to a given event.  Koko takes a hypothetical event and 
     * evaluates it based on the information it has been given. You can call this
     * method as many times as you like in order to see how the user may respond
     * to different scenarios.
     * 
     * @param koko the endpoint for your application
     * @throws JSONEndpointException
     */
    private void predictEmotion(KokoEndpoint koko) throws JSONEndpointException {
        
        ArrayList<DataInstance> instances = new ArrayList<DataInstance>();
        
        for(DataDefinition definition : koko.getDataDefinitions()) {
            Serializable value = null;
        
            if(definition.getName().equals("weather")) {
                value = "hot";
            } else if(definition.getName().equals("highScore")) {
                value = 93.24;
            } else if(definition.getName().equals("attemptsToday")) {
                value = 3;
            } else if(definition.getName().equals("timeToRespond")) {
                value = (long)600000;
            } else if(definition.getName().equals("birthday")) {
                value = new Date();
            } else {
                throw new RuntimeException("Unknown data definition");
            }
        
            instances.add(new DataInstance(definition, value));
        }
        
        printEmotionVector(koko.getPredictedEmotion(instances));
    }

    private void printEmotionVector(EmotionVector v) {
        
        Log.i("KokoExample", "like=" + v.getValue(EmotionType.LIKE) + " " +
                             "dislike=" + v.getValue(EmotionType.DISLIKE));
    }
}