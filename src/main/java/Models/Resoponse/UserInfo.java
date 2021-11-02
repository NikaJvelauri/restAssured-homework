package Models.Resoponse;

import Utils.TimeFormatter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;


public class UserInfo {
    public String name ;
    public String job;
    public String id;
    @JsonDeserialize(using = TimeFormatter.class)
    public LocalDateTime createdAt;

    public String getName() {
        return name;
    }
     public String getJob() {
        return job;
     }
     public String getId() {
        return id;
     }
     public  LocalDateTime getCreatedAt() {
        return createdAt;
     }

     public void setName(String name) {
            this.name = name;
     }

     public void setJob(String job) {
        this.job = job;
     }




}
