package Stepper.Flow.Logger;

import java.time.LocalDateTime;

public class FlowLog {
    private LocalDateTime time;
    private String content;

    public FlowLog(LocalDateTime time, String content){
        this.time=time;
        this.content = content;
    }

    public String getContent(){
        return content;
    }
    public LocalDateTime getTime(){
        return time;
    }

}
