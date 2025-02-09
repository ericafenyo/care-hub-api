package com.ericafenyo.carehub.mapper;

import com.ericafenyo.carehub.entities.VitalEntity;
import com.ericafenyo.carehub.model.Vital;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * A class responsible for converting a {@link VitalEntity} to a {@link Vital}.
 */
@Component
public class VitalMapper implements Function<VitalEntity, Vital> {

    /**
     * Converts a {@link VitalEntity} to a {@link Vital}.
     *
     * @param entity the {@link VitalEntity} to be converted
     * @return the corresponding {@link Vital} object
     */
    @Override
    public Vital apply(VitalEntity entity) {
        var vital = new Vital();
        vital.setId(entity.getId());
        vital.setType(entity.getType());
        vital.setUnit(entity.getUnit());
        return vital;
    }
}
