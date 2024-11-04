package ekonomente.timestamp;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class TimestampService {
    private final TimestampRepository timestampRepository;

    public TimestampService(TimestampRepository timestampRepository){
        this.timestampRepository = timestampRepository;
    }

    public List<Timestamp> getTimestamps(){
        return timestampRepository.findAll();
    }

    public void addTimestamp(Timestamp timestamp) {
        Optional<Timestamp> existingTimeStamp = timestampRepository.findIdenticTimestamp(
                                                timestamp.getStartTime(),
                                                timestamp.getEndTime(),
                                                timestamp.getDate(),
                                                timestamp.getConsult(),
                                                timestamp.getMission()
        );

        if (existingTimeStamp.isPresent()) {
            throw new IllegalArgumentException("Identic timestamp already exists");
        }

        timestampRepository.save(timestamp);
    }

    public void deleteTimestamp(long timestampId) {
        boolean idExists = timestampRepository.existsById(timestampId);

        if (!idExists){
            throw new IllegalArgumentException("Timestamp with id " + timestampId + " does not exist");
        }

        timestampRepository.deleteById(timestampId);
    }

    @Transactional
    public void updateTimestamp(long timestampId, String notes, LocalTime startTime, LocalTime endTime, LocalDate date) {
        Timestamp timestamp = timestampRepository.findById(timestampId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Timestamp with id " + timestampId + " does not exist"));

        if (notes != null && !notes.isEmpty()){
            timestamp.setNotes(notes);
        }

        if (startTime != null && endTime != null && date != null) {
            if (startTime.isBefore(endTime) && !date.isAfter(LocalDate.now())) {
                timestamp.setStartTime(startTime);
                timestamp.setEndTime(endTime);
                timestamp.setDate(date);
            }
        }
    }
}
