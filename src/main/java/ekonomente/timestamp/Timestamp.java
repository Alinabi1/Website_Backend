package ekonomente.timestamp;

import ekonomente.consult.Consult;
import ekonomente.mission.Mission;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity(name = "Timestamp")
@Table(name = "timestamp")
public class Timestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "notes", nullable = false, columnDefinition = "TEXT")
    private String notes;
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "consult_id", nullable = false)
    private Consult consult;
    @ManyToOne
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    public Timestamp(Long id, String notes, LocalTime startTime, LocalTime endTime, LocalDate date, Consult consult, Mission mission) {
        this.id = id;
        this.notes = notes;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.consult = consult;
        this.mission = mission;
    }

    public Timestamp(String notes, LocalTime startTime, LocalTime endTime, LocalDate date, Consult consult, Mission mission) {
        this.notes = notes;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.consult = consult;
        this.mission = mission;
    }

    public Timestamp() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Consult getConsult() {
        return consult;
    }

    public void setConsult(Consult consult) {
        this.consult = consult;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timestamp that = (Timestamp) o;
        return Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(date, that.date) && Objects.equals(consult, that.consult) && Objects.equals(mission, that.mission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, date, consult, mission);
    }

    @Override
    public String toString() {
        return notes +
                ", " + startTime +
                " - " + endTime +
                ", " + date +
                ", consult: " + consult.getName() +
                ", mission: " + mission.getName();
    }
}

