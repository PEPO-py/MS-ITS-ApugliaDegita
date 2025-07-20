package it.MS_ITS_ApugliaDegital.MS_ITS_ApugliaDegita.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
public class DynamicPayload {



    // This map will hold all "unlimited" or dynamic attributes
    private Map<String, Object> dynamicAttributes = new HashMap<>();

    // Constructor (optional, but good practice)
    public DynamicPayload() {
    }

    /**
     * This annotation tells Jackson to put any unknown JSON properties
     * into the 'dynamicAttributes' map during deserialization.
     * The key is the JSON property name, and the value is its deserialized object.
     */
    @JsonAnySetter
    public void addDynamicAttribute(String key, Object value) {
        this.dynamicAttributes.put(key, value);
    }

    /**
     * This annotation tells Jackson to serialize all entries in the 'dynamicAttributes' map
     * as top-level JSON properties during serialization.
     *
     * If you want to serialize the map itself as a nested object, remove this annotation
     * and provide a standard getter for `dynamicAttributes`.
     */
    @JsonAnyGetter
    public Map<String, Object> getDynamicAttributes() {
        return dynamicAttributes;
    }

    // If you want to expose the map directly (e.g., for internal use or if you remove @JsonAnyGetter)
    // You might use @JsonIgnore if you have @JsonAnyGetter and don't want a duplicate field in JSON.
    @JsonIgnore
    public Map<String, Object> getRawDynamicAttributesMap() {
        return dynamicAttributes;
    }


    // Optional: toString for easy debugging
}