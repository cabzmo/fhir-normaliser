package com.cabz.fhir_normaliser.util;

import com.cabz.fhir_normaliser.dto.PatientResponseDto;
import com.cabz.fhir_normaliser.model.MappedPatient;
import org.hl7.fhir.r4.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    @Mapping(ignore = true, target = "id")
    @Mapping(target = "fhirId", source = "fhirPatient.idElement.idPart")
    @Mapping(target = "givenName", source = "fhirPatient.nameFirstRep.givenAsSingleString")
    @Mapping(target = "familyName", source = "fhirPatient.nameFirstRep.family")
    @Mapping(target = "gender",
            expression = "java(mapGender(fhirPatient))")
    @Mapping(target = "birthDate",
            expression = "java(mapBirthDate(fhirPatient))")
    MappedPatient map(Patient fhirPatient);

    default String mapGender(Patient fhirPatient) {
        return fhirPatient.getGender() != null ? fhirPatient.getGender().toCode() : null;
    }

    default String mapBirthDate(Patient fhirPatient) {
        return fhirPatient.getBirthDate() != null ? fhirPatient.getBirthDate().toString() : null;
    }

    PatientResponseDto map(MappedPatient patient);

}
