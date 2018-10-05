package result;

import connectSQLite.*;
import model.Domain;
import model.Exercise;
import model.User;
import model.User_Ex;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class SaveResult {
    // Save all data to file
    public static void saveDataToFile(){
        // get all domain
        List<Domain> domainList = ActDomain.getAllDomainAndEx();
        // get exercise and add to domain
        for (int k = 0; k < domainList.size(); k++) {
            List<Exercise> exerciseList = domainList.get(k).getExerciseList();
            Exercise exercise;
            for (int i = 0; i < exerciseList.size(); i++) {
                exercise = exerciseList.get(i);
                // get sample and add to exercise
                exercise.setSampleList(ActSample.getSampleListByExID(exercise.getId()));
                //get leaderboard and add to exercise
                List<User_Ex> userExList = ActUserEx.getUserExListByExID(exercise.getId());
                for (int j = 0; j < userExList.size(); j++) {
                    userExList.get(j).setUser(ActUser.getUserByUserID(userExList.get(j).user_id));
                }
                exercise.setUser_exList(userExList);
            }
            domainList.get(k).setExerciseList(exerciseList);
        }
        // save to file
        ObjectMapper mapper = new ObjectMapper();
        String path = System.getProperty("user.dir");
        try {
            String jsonInString = "var data = " + mapper.writeValueAsString(domainList) + ";";
            BufferedWriter writer = null;
            try
            {
                writer = new BufferedWriter( new FileWriter(path + "\\view\\data.js"));
                writer.write(jsonInString);

            }
            catch ( IOException e)
            {
            }
            finally
            {
                try
                {
                    if ( writer != null)
                        writer.close( );
                }
                catch ( IOException e)
                {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        saveDataToFile();
    }
}
