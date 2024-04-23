package org.am.core.web.domain.entity.admingeneral;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "area_parameters")
@NoArgsConstructor
@Getter
@Setter
public class AreaParameters {
    @Id
    @Column(name = "area_id")
    private Integer areaId;
    @Column(name = "monday_schedule")
    private Boolean mondaySchedule;
    @Column(name = "tuesday_schedule")
    private Boolean tuesdaySchedule;
    @Column(name = "wednesday_schedule")
    private Boolean wednesdaySchedule;
    @Column(name = "thursday_schedule")
    private Boolean thursdaySchedule;
    @Column(name = "friday_schedule")
    private Boolean fridaySchedule;
    @Column(name = "saturday_schedule")
    private Boolean saturdaySchedule;
    @Column(name = "sunday_schedule")
    private Boolean sundaySchedule;
    @Column(name = "start_time_schedule", columnDefinition="TIME")
    private LocalTime startTimeSchedule;
    @Column(name = "end_time_schedule", columnDefinition="TIME")
    private LocalTime endTimeSchedule;
    @Column(name = "time_interval_schedule")
    private Integer timeIntervalSchedule;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Area area;

    public AreaParameters(Boolean mondaySchedule, Boolean tuesdaySchedule,
                          Boolean wednesdaySchedule, Boolean thursdaySchedule,
                          Boolean fridaySchedule, Boolean saturdaySchedule,
                          Boolean sundaySchedule, LocalTime startTimeSchedule,
                          LocalTime endTimeSchedule, Integer timeIntervalSchedule,
                          Area area) {
        this.mondaySchedule = mondaySchedule;
        this.tuesdaySchedule = tuesdaySchedule;
        this.wednesdaySchedule = wednesdaySchedule;
        this.thursdaySchedule = thursdaySchedule;
        this.fridaySchedule = fridaySchedule;
        this.saturdaySchedule = saturdaySchedule;
        this.sundaySchedule = sundaySchedule;
        this.startTimeSchedule = startTimeSchedule;
        this.endTimeSchedule = endTimeSchedule;
        this.timeIntervalSchedule = timeIntervalSchedule;
        this.area = area;
    }
}
