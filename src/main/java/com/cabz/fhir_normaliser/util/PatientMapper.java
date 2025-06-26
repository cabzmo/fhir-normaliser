package com.cabz.fhir_normaliser.util;

import com.cabz.fhir_normaliser.dto.PatientResponseDto;
import com.cabz.fhir_normaliser.model.Gender;
import com.cabz.fhir_normaliser.model.MappedPatient;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;
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
            expression = "java(mapGender(fhirPatient.getGender()))")
    @Mapping(target = "birthDate", source = "fhirPatient.birthDate")
    MappedPatient map(Patient fhirPatient);

    default Gender mapGender(AdministrativeGender gender) {
        if (gender == null) return null;
        return switch (gender) {
            case MALE -> Gender.MALE;
            case FEMALE -> Gender.FEMALE;
            case OTHER -> Gender.OTHER;
            case UNKNOWN -> Gender.UNKNOWN;
            default -> Gender.NULL;
        };
    }

    @Mapping(target = "firstName", source = "patient.givenName")
    @Mapping(target = "lastName", source = "patient.familyName")
    @Mapping(target = "gender", expression = "java(patient.getGender().toString())")
    @Mapping(target = "birthDate", expression = "java(patient.getBirthDate().toString())")
    PatientResponseDto map(MappedPatient patient);
}
