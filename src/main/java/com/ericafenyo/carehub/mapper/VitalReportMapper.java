package com.ericafenyo.carehub.mapper;

import com.ericafenyo.carehub.entities.VitalReportEntity;
import com.ericafenyo.carehub.model.VitalReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;


/**
 * A class responsible for converting a {@link VitalReportEntity} to a {@link VitalReport}.
 */
@Component
@RequiredArgsConstructor
public class VitalReportMapper implements Function<VitalReportEntity, VitalReport> {
    private final PartialUserMapper userMapper;
    private final VitalMeasurementMapper vitalMeasurementMapper;

    /**
     * Converts a {@link VitalReportEntity} to a {@link VitalReport}.
     *
     * @param entity the {@link VitalReportEntity} to be converted
     * @return the corresponding {@link VitalReport} object
     */
    @Override
    public VitalReport apply(VitalReportEntity entity) {
        var vital = new VitalReport();
        vital.setId(entity.getId());
        vital.setNotes(entity.getNotes());
        vital.setRecordedAt(entity.getRecordedAt());

        Optional.ofNullable(entity.getMember())
                .map(userMapper)
                .ifPresent(vital::setMember);

        System.out.println("entity.getVitals() = " + entity.getVitals());

        Optional.ofNullable(entity.getVitals())
                .ifPresent(vitals -> {
                    var measurements = vitals.stream().map(vitalMeasurementMapper).toList();
                    vital.setMeasurements(measurements);
                });
        return vital;
    }
}
