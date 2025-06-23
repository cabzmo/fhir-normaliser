package com.cabz.fhir_normaliser.util;

import com.cabz.fhir_normaliser.model.MappedPatient;
import org.hl7.fhir.r4.model.Patient;

public class PatientMapper {
    public static MappedPatient fromFhir(Patient fhirPatient) {
        return MappedPatient.builder()
                .fhirId(fhirPatient.getIdElement().getIdPart())
                .givenName(fhirPatient.getNameFirstRep().getGivenAsSingleString())
                .familyName(fhirPatient.getNameFirstRep().getFamily())
                .gender(fhirPatient.getGender() != null ? fhirPatient.getGender().toCode() : null)
                .birthDate(fhirPatient.getBirthDate() != null ? fhirPatient.getBirthDate().toString() : null)
                .build();
    }
}
