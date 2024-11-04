package ekonomente.timestamp;

import ekonomente.consult.Consult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "timestamp")
public class TimestampController {

    private final TimestampService timestampService;

    @Autowired
    public TimestampController(TimestampService timestampService) {
        this.timestampService = timestampService;
    }

    /*
    @GetMapping
    public List<Timestamp> getTimestamps(){
        return timestampService.getTimestamps();
    }
     */

    @GetMapping(path = "/allTimestamps")
    public List<Map<Long, String>> getConsults(){
        List<Timestamp> timestamps = timestampService.getTimestamps();
        List<Map<Long, String>> timestampsWithIds = new ArrayList<>();
        for (Timestamp timestamp : timestamps) {
            Map<Long, String> timestampMap = new HashMap<>();
            timestampMap.put(timestamp.getId(), timestamp.toString());
            timestampsWithIds.add(timestampMap);
        }
        return timestampsWithIds;
    }

    @PostMapping(path = "/register")
    public void registerTimestamps(@RequestBody Timestamp timestamp){
        timestampService.addTimestamp(timestamp);
    }

    @DeleteMapping(path = "/{timestampId}/delete")
    public void deleteTimestamp(@PathVariable("timestampId") long timestampId){
        timestampService.deleteTimestamp(timestampId);
    }

    @PutMapping(path = "/{timestampId}/update")
    public void updatetimestamp(
            @PathVariable("timestampId") long timestampId,
            @RequestParam(required = false) String notes,
            @RequestParam(required = false)LocalTime startTime,
            @RequestParam(required = false)LocalTime endTime,
            @RequestParam(required = false)LocalDate date) {
            timestampService.updateTimestamp(timestampId,notes,startTime,endTime,date);
    }

}
