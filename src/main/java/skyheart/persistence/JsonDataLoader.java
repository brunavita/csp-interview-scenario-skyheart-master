package skyheart.persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import skyheart.domain.Programme;
import skyheart.domain.Schedule;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonDataLoader {

    public static List<Programme> getProgrammeData(){
        Type collectionType = new TypeToken<List<Programme>>(){}.getType();
        List<Programme> programmes = new ArrayList<>();

        programmes = (List<Programme>)loadJsonFromFile("/data/programme-data.json", collectionType, programmes);

        return programmes;
    }

    public static Schedule getScheduleData(){
        Type collectionType = new TypeToken<Schedule>(){}.getType();
        Schedule schedule = new Schedule();

        schedule = (Schedule)loadJsonFromFile("/data/schedule-data.json", collectionType, schedule);

        return schedule;
    }

    private static Object loadJsonFromFile(String fileName, Type collectionType, Object returnObject){

        try{
            InputStream is = JsonDataLoader.class.getResourceAsStream(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            JsonReader reader = new JsonReader(bufferedReader);
            returnObject = new Gson().fromJson(reader, collectionType);
        }
        catch(Exception e){
            System.out.print(e.getMessage());
        }

        return returnObject;
    }
}
