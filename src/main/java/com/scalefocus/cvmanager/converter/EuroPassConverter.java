package com.scalefocus.cvmanager.converter;

import com.scalefocus.cvmanager.model.biography.Address;
import com.scalefocus.cvmanager.model.biography.Biography;
import com.scalefocus.cvmanager.model.biography.Date;
import com.scalefocus.cvmanager.model.biography.Headline;
import com.scalefocus.cvmanager.model.biography.Period;
import com.scalefocus.cvmanager.model.biography.education.Education;
import com.scalefocus.cvmanager.model.biography.education.Organisation;
import com.scalefocus.cvmanager.model.biography.identification.ContactInfo;
import com.scalefocus.cvmanager.model.biography.identification.Country;
import com.scalefocus.cvmanager.model.biography.identification.Demographics;
import com.scalefocus.cvmanager.model.biography.identification.Identification;
import com.scalefocus.cvmanager.model.biography.identification.PersonName;
import com.scalefocus.cvmanager.model.biography.identification.Photo;
import com.scalefocus.cvmanager.model.biography.skill.ForeignLanguage;
import com.scalefocus.cvmanager.model.biography.skill.LinguisticSkills;
import com.scalefocus.cvmanager.model.biography.skill.ProficiencyLevel;
import com.scalefocus.cvmanager.model.biography.skill.Skills;
import com.scalefocus.cvmanager.model.biography.workexperience.Employer;
import com.scalefocus.cvmanager.model.biography.workexperience.WorkExperience;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Converts an {@link javax.persistence.Entity} of type {@link Biography} to the required from Europass API {@link JSONObject}.
 * All private methods are used to convert smaller entities so later they can be used to build the required {@link JSONObject}.
 *
 * @author Mariyan Topalov
 */
@SuppressWarnings("unchecked")
@Component
public class EuroPassConverter {

    private static final String LABEL = "Label";

    private static final String CONTACT = "Contact";

    private static final String ADDRESS = "Address";

    private static final String CONTACT_INFO = "ContactInfo";

    private static final String DESCRIPTION = "Description";

    /**
     * Converts the {@link Biography}, given as argument, to the required by the Europass REST API {@link JSONObject} and returns it.
     *
     * @param biography the {@link Biography} to be converted.
     * @return the {@link Biography}, given as argument, converted to {@link JSONObject}.
     */
    public JSONObject toJsonObject(Biography biography) {
        JSONObject result = new JSONObject();
        JSONObject learnerInfo = new JSONObject();
        learnerInfo.put("LearnerInfo", convertBiography(biography));
        result.put("SkillsPassport", learnerInfo);

        return result;
    }

    /**
     * Converts the {@link Biography}, given as argument, to the desired {@link JSONObject} and returns it.
     *
     * @param biography the {@link Biography} to be converted.
     * @return the argument converted to {@link JSONObject}.
     */
    private JSONObject convertBiography(Biography biography) {
        JSONObject result = new JSONObject();

        JSONArray workExperiences = biography.getWorkExperiences().stream()
                .map(this::convertWorkExperience)
                .collect(Collectors.toCollection(JSONArray::new));

        JSONArray educations = biography.getEducations().stream()
                .map(this::convertEducation)
                .collect(Collectors.toCollection(JSONArray::new));

        result.put("Identification", convertIdentification(biography.getIdentification()));
        result.put("Headline", convertHeadline(biography.getHeadline()));
        result.put("WorkExperience", workExperiences);
        result.put("Education", educations);
        result.put("Skills", convertSkills(biography.getSkills()));
        return result;
    }

    //Biography Identification convertion

    /**
     * Converts the {@link PersonName}, given as argument, to the desired {@link JSONObject} and returns it.
     *
     * @param personName the {@link PersonName} to be converted.
     * @return the argument converted to {@link JSONObject}.
     */
    private JSONObject convertPersonName(PersonName personName) {
        JSONObject result = new JSONObject();

        result.put("FirstName", personName.getFirstName());
        result.put("Surname", personName.getLastName());

        return result;
    }

    /**
     * Converts the {@link Country}, given as argument, to the desired {@link JSONObject} and returns it.
     *
     * @param country the {@link Country} to be converted.
     * @return the argument converted to {@link JSONObject}.
     */
    private JSONObject convertCountry(Country country) {
        JSONObject result = new JSONObject();

        result.put("Code", country.getCode());
        result.put(LABEL, country.getLabel());

        return result;
    }

    /**
     * Converts the {@link Address}, given as argument, to the desired {@link JSONObject} and returns it.
     *
     * @param address the {@link Address} to be converted.
     * @return the argument converted to {@link JSONObject}.
     */
    private JSONObject convertAddress(Address address) {
        JSONObject contact = new JSONObject();
        JSONObject result = new JSONObject();

        contact.put("AddressLine", address.getStreet());
        contact.put("PostalCode", address.getPostalCode());
        contact.put("Municipality", address.getMunicipality());
        contact.put("Country", convertCountry(address.getCountry()));

        result.put(CONTACT, contact);

        return result;
    }

    /**
     * Converts the {@link ContactInfo}, given as argument, to the desired {@link JSONObject} and returns it.
     *
     * @param contactInfo the {@link ContactInfo} to be converted.
     * @return the argument converted to {@link JSONObject}.
     */
    private JSONObject convertContactInfo(ContactInfo contactInfo) {
        JSONObject result = new JSONObject();

        JSONObject email = new JSONObject();
        email.put(CONTACT, contactInfo.getEmail());

        JSONArray telephones = new JSONArray();
        for (String telephone : contactInfo.getTelephones()) {
            JSONObject phone = new JSONObject();
            phone.put(CONTACT, telephone);
            telephones.add(phone);
        }

        result.put(ADDRESS, convertAddress(contactInfo.getAddress()));
        result.put("Email", email);
        result.put("Telephone", telephones);

        return result;
    }

    /**
     * Converts the {@link Demographics}, given as argument, to the desired {@link JSONObject} and returns it.
     *
     * @param demographics the {@link Demographics} to be converted.
     * @return the argument converted to {@link JSONObject}.
     */
    private JSONObject convertDemographics(Demographics demographics) {
        JSONObject result = new JSONObject();

        JSONObject birthDate = new JSONObject();
        birthDate.put("Year", demographics.getBirthDate().getYear());
        birthDate.put("Month", demographics.getBirthDate().getMonth());
        birthDate.put("Day", demographics.getBirthDate().getDay());

        JSONObject gender = new JSONObject();
        gender.put(LABEL, demographics.getGender());

        result.put("Birthdate", birthDate);
        result.put("Gender", gender);

        return result;
    }

    /**
     * Converts the {@link Photo}, given as argument, to the desired {@link JSONObject} and returns it.
     *
     * @param photo the {@link Photo} to be converted.
     * @return the argument converted to {@link JSONObject}.
     */
    private JSONObject convertPhoto(Photo photo) {
        JSONObject result = new JSONObject();

        result.put("MimeType", photo.getType());
        result.put("Data", photo.getData());

        return result;
    }

    /**
     * Converts the {@link Identification}, given as argument, to the desired {@link JSONObject} and returns it.
     *
     * @param identification the {@link Identification} to be converted.
     * @return the argument converted to {@link JSONObject}.
     */
    private JSONObject convertIdentification(Identification identification) {
        JSONObject result = new JSONObject();

        result.put("PersonName", convertPersonName(identification.getPersonName()));
        result.put(CONTACT_INFO, convertContactInfo(identification.getContactInfo()));
        result.put("Demographics", convertDemographics(identification.getDemographics()));
        result.put("Photo", convertPhoto(identification.getPhoto()));

        return result;
    }

    //Headline convertion

    /**
     * Converts the {@link Headline}, given as argument, to the desired {@link JSONObject} and returns it.
     *
     * @param headline the {@link Headline} to be converted.
     * @return the argument converted to {@link JSONObject}.
     */
    private JSONObject convertHeadline(Headline headline) {
        JSONObject result = new JSONObject();

        JSONObject type = new JSONObject();
        type.put(LABEL, headline.getType());

        JSONObject description = new JSONObject();
        description.put(LABEL, headline.getDescription());

        result.put("Type", type);
        result.put(DESCRIPTION, description);
        return result;
    }

    //Work experience convertion

    /**
     * Converts the {@link WorkExperience}, given as argument, to the desired {@link JSONObject} and returns it.
     *
     * @param workExperience the {@link WorkExperience} to be converted.
     * @return the argument converted to {@link JSONObject}.
     */
    private JSONObject convertWorkExperience(WorkExperience workExperience) {
        JSONObject result = new JSONObject();

        result.put("Period", convertPeriod(workExperience.getPeriod()));
        result.put("Position", convertPosition(workExperience));
        result.put("Activities", workExperience.getActivities());
        result.put("Employer", convertEmployer(workExperience.getEmployer()));

        return result;
    }

    /**
     * Converts the {@link Date}, given as argument, to the desired {@link JSONObject} and returns it.
     *
     * @param date the {@link Date} to be converted.
     * @return the argument converted to {@link JSONObject}.
     */
    private JSONObject convertDate(Date date) {
        JSONObject result = new JSONObject();

        result.put("Year", date.getYear());
        result.put("Month", date.getMonth());

        return result;
    }

    /**
     * Converts the {@link Period}, given as argument, to the desired {@link JSONObject} and returns it.
     *
     * @param period the {@link Period} to be converted.
     * @return the argument converted to {@link JSONObject}.
     */
    private JSONObject convertPeriod(Period period) {
        JSONObject result = new JSONObject();
        result.put("From", convertDate(period.getStart()));
        if (!period.isCurrent() && period.getEnd() != null) {
            result.put("To", convertDate(period.getEnd()));
        }
        result.put("Current", period.isCurrent());
        return result;
    }

    /**
     * Converts the {@link WorkExperience}, given as argument, to the desired {@link JSONObject} and returns it.
     *
     * @param workExperience the {@link WorkExperience} to be converted.
     * @return the argument converted to {@link JSONObject}.
     */
    private JSONObject convertPosition(WorkExperience workExperience) {
        JSONObject result = new JSONObject();
        result.put(LABEL, workExperience.getPosition());
        return result;
    }

    /**
     * Converts the {@link Employer}, given as argument, to the desired {@link JSONObject} and returns it.
     *
     * @param employer the {@link Employer} to be converted.
     * @return the argument converted to {@link JSONObject}.
     */
    private JSONObject convertEmployer(Employer employer) {
        JSONObject result = new JSONObject();
        JSONObject contactInfo = new JSONObject();
        contactInfo.put(ADDRESS, convertAddress(employer.getAddress()));

        result.put("Name", employer.getName());
        result.put(CONTACT_INFO, contactInfo);

        return result;
    }

    //Education convertion

    /**
     * Converts the {@link Education}, given as argument, to the desired {@link JSONObject} and returns it.
     *
     * @param education the {@link Education} to be converted.
     * @return the argument converted to {@link JSONObject}.
     */
    private JSONObject convertEducation(Education education) {
        JSONObject result = new JSONObject();
        result.put("Period", convertPeriod(education.getPeriod()));
        result.put("Title", education.getTitle());
        result.put("Organisation", convertOrganisation(education.getOrganisation()));

        return result;
    }

    /**
     * Converts the {@link Organisation}, given as argument, to the desired {@link JSONObject} and returns it.
     *
     * @param organisation the {@link Organisation} to be converted.
     * @return the argument converted to {@link JSONObject}.
     */
    private JSONObject convertOrganisation(Organisation organisation) {
        JSONObject result = new JSONObject();

        JSONObject contactInfo = new JSONObject();
        contactInfo.put(ADDRESS, convertAddress(organisation.getAddress()));

        result.put("Name", organisation.getName());
        result.put(CONTACT_INFO, contactInfo);

        return result;
    }

    //Skills convertion

    /**
     * Converts the {@link Skills}, given as argument, to the desired {@link JSONObject} and returns it.
     *
     * @param skills the {@link Skills} to be converted.
     * @return the argument converted to {@link JSONObject}.
     */
    private JSONObject convertSkills(Skills skills) {
        JSONObject result = new JSONObject();

        result.put("Linguistic", convertLinguisticSkills(skills.getLinguisticSkills()));
        result.put("Communication", convertOtherSkills(skills.getCommunication()));
        result.put("Organisational", convertOtherSkills(skills.getOrganisational()));
        result.put("Computer", convertOtherSkills(skills.getComputer()));
        result.put("Driving", convertDrivingSkills(skills.getDriving()));
        result.put("Other", convertOtherSkills(skills.getOther()));

        return result;
    }

    /**
     * Converts the {@link LinguisticSkills}, given as argument, to the desired {@link JSONObject} and returns it.
     *
     * @param linguisticSkills the {@link LinguisticSkills} to be converted.
     * @return the argument converted to {@link JSONObject}.
     */
    private JSONObject convertLinguisticSkills(LinguisticSkills linguisticSkills) {
        JSONObject result = new JSONObject();

        result.put("MotherTongue", convertMotherLanguages(linguisticSkills.getMotherLanguages()));
        result.put("ForeignLanguage", convertForeignLanguages(linguisticSkills.getForeignLanguages()));
        return result;
    }

    /**
     * Converts the {@link List} of {@link String}, given as argument,
     * to the desired {@link JSONArray} and returns it.
     *
     * @param motherLanguages the list of Strings to be converted.
     * @return the argument converted to {@link JSONArray}.
     */
    private JSONArray convertMotherLanguages(List<String> motherLanguages) {
        JSONArray result = new JSONArray();
        for (String language : motherLanguages) {
            JSONObject label = new JSONObject();
            label.put(LABEL, language);
            JSONObject desc = new JSONObject();
            desc.put(DESCRIPTION, label);
            result.add(desc);
        }
        return result;
    }

    /**
     * Converts the {@link List} of {@link ForeignLanguage},
     * given as argument, to the desired {@link JSONArray} and returns it.
     *
     * @param foreignLanguages the list to be converted.
     * @return the argument converted to the desired {@link JSONArray}.
     */
    private JSONArray convertForeignLanguages(List<ForeignLanguage> foreignLanguages) {
        JSONArray result = new JSONArray();

        for (ForeignLanguage language : foreignLanguages) {
            JSONObject label = new JSONObject();
            label.put(LABEL, language.getDescription());

            JSONObject foreignLanguage = new JSONObject();

            foreignLanguage.put(DESCRIPTION, label);
            foreignLanguage.put("ProficiencyLevel", convertProficiencyLevel(language.getProficiencyLevel()));
            foreignLanguage.put("Certificate", convertCertificates(language.getCertificates()));
            result.add(foreignLanguage);
        }

        return result;
    }

    /**
     * Converts the {@link ProficiencyLevel} argument to the desired {@link JSONObject} and returns it.
     *
     * @param proficiencyLevel the {@link ProficiencyLevel} to be converted.
     * @return the argument converted to the desired {@link JSONObject}.
     */
    private JSONObject convertProficiencyLevel(ProficiencyLevel proficiencyLevel) {
        JSONObject result = new JSONObject();
        result.put("Listening", proficiencyLevel.getListening());
        result.put("Reading", proficiencyLevel.getReading());
        result.put("SpokenInteraction", proficiencyLevel.getSpokenInteraction());
        result.put("SpokenProduction", proficiencyLevel.getSpokenProduction());
        result.put("Writing", proficiencyLevel.getWriting());
        return result;
    }

    /**
     * Converts {@link List} of {@link String} to the desired {@link JSONArray} and returns it.
     *
     * @param certificates the list to be converted.
     * @return the argument converted to the desired {@link JSONArray}.
     */
    private JSONArray convertCertificates(List<String> certificates) {
        JSONArray result = new JSONArray();
        for (String certificate : certificates) {
            JSONObject title = new JSONObject();
            title.put("Title", certificate);
            result.add(title);
        }
        return result;
    }

    /**
     * Converts the given {@link String} argument to the desired {@link JSONObject}
     * and returns it.
     *
     * @param skill skill to be converted.
     * @return the argument converted to the desired {@link JSONObject}.
     */
    private JSONObject convertOtherSkills(String skill) {
        JSONObject result = new JSONObject();
        result.put(DESCRIPTION, skill);
        return result;
    }

    /**
     * Converts the {@link List} of {@link String}, given as argument,
     * to the desired {@link JSONObject} and returns it.
     *
     * @param drivingSkills the list of driving skills.
     * @return the argument converted to the desired {@link JSONObject}.
     */
    private JSONObject convertDrivingSkills(List<String> drivingSkills) {
        JSONObject result = new JSONObject();
        result.put(DESCRIPTION, drivingSkills);
        return result;
    }
}



