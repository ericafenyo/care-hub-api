package com.ericafenyo.carehub.mapper;

import com.ericafenyo.carehub.entities.VitalReportEntity;
import com.ericafenyo.carehub.model.PartialVitalReport;
import com.ericafenyo.carehub.model.VitalReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;


@Component
@RequiredArgsConstructor
public class PartialVitalReportMapper implements Function<VitalReportEntity, PartialVitalReport> {
    private final PartialUserMapper userMapper;

    public PartialVitalReport apply(VitalReportEntity entity) {
        var vital = new PartialVitalReport();
        vital.setId(entity.getId());
        vital.setNotes(entity.getNotes());
        vital.setRecordedAt(entity.getRecordedAt());

        Optional.ofNullable(entity.getMember())
                .map(userMapper)
                .ifPresent(vital::setMember);

        System.out.println("entity.getVitals() = " + entity.getVitals());

        Optional.ofNullable(entity.getVitals())
                .map(List::size)
                .ifPresent(vital::setVitalCount);
        return vital;
    }
}
