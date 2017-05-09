package nl.markv;

public class TestDataGen {
    public static Group generateMedewerkerAfdeling() {
        Value project = new Value("projectId", true);
        Value medewerker = new Value("medewerkerId", true);
        Value afdeling = new Value("afdelingId", true);
        return new Group(
            project,
            new Value("naam", project),
            medewerker,
            new Value("naam", medewerker),
            afdeling,
            new Value("naam", afdeling),
            new Value("chef", afdeling),
            new Value("aantalUren")
        );
    }

    public static Group generateDrugSideeffect() {
        Value drug = new Value("drugId", true);
        Value sideeff = new Value("sideeffectId", true);
        return new Group(
            drug,
            new Value("naam", drug),
            sideeff,
            new Value("description", sideeff),
            new Value("cure", sideeff),
            new Value("brand", drug)
        );
    }

    public static Group generatePatientMedicineDosage() {
        Value patient = new Value("patientId", true);
        Value drug = new Value("drugId", true);
        Value manufacturer = new Value("manufacturerId", drug, true);
        return new Group(
            patient,
            new Value("name", patient),
            new Group(
                drug,
                manufacturer,
                new Value("name", manufacturer),
                new Value("name", drug),
                new Value("dosage")
            )
        );
    }

}
