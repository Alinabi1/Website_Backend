package ekonomente.mission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "mission")
public class MissionController {

    private final MissionService missionService;

    @Autowired
    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    /*
    @GetMapping
    public List<Mission> getMissions(){
        return missionService.getMissions();
    }
     */

    @GetMapping(path = "/allMissions")
    public List<Map<Long, String>> getMissionsWithIds() {
        List<Mission> missions = missionService.getMissions();
        List<Map<Long, String>> missionsWithIds = new ArrayList<>();
        for (Mission mission : missions) {
            Map<Long, String> missionMap = new HashMap<>();
            missionMap.put(mission.getId(), mission.toString());
            missionsWithIds.add(missionMap);
        }
        return missionsWithIds;
    }


    @PostMapping(path = "/register")
    public void registerMission(@RequestBody Mission mission){
        missionService.addMission(mission);
    }

    @DeleteMapping(path = "/{missionId}/delete")
    public void deleteMission(@PathVariable("missionId") long missionId){
        missionService.deleteMission(missionId);
    }

    @PutMapping(path = "/{missionId}/update")
    public void updateMission(
            @PathVariable("missionId") long missionId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false)LocalDate startDate,
            @RequestParam(required = false)LocalDate endDate){
        missionService.updateMission(missionId,name,startDate,endDate);
    }

    @PutMapping(path = "/{missionId}/{consultId}/assignConsult")
    public void assignConsult(
            @PathVariable("missionId") long missionId,
            @PathVariable("consultId") long consultId) {
        missionService.assignConsult(missionId,consultId);
    }

    @DeleteMapping(path = "/{missionId}/{consultId}/unassignConsult")
    public void unassignConsult(
            @PathVariable("missionId") long missionId,
            @PathVariable("consultId") long consultId) {
        missionService.unassignConsult(missionId,consultId);
    }
}
