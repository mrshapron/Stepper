package Flow.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @Override
    public String toString() {
        return time.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " " + content;
    }

    public LocalDateTime getTime(){
        return time;
    }

}
