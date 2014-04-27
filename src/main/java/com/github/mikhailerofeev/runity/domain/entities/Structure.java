package com.github.mikhailerofeev.runity.domain.entities;

import com.github.mikhailerofeev.runity.domain.values.ParamValueWithVersionId;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author m-erofeev
 * @since 26.04.14
 */
public class Structure {

    @Id
    private String id;

    private String name;

    //detalisation for mongo purpose
    private Map<String, ArrayList<ParamValueWithVersionId>> param2valueAndVersion;
    private Map<String, Pair<Integer, Integer>> paramRating;


    Structure() {
    }

    public Structure(String name, Map<String, ArrayList<ParamValueWithVersionId>> param2valueAndVersion) {
        this.name = name;
        this.param2valueAndVersion = param2valueAndVersion;
        this.paramRating = Maps.newHashMap();
    }

    public Map<String, ArrayList<ParamValueWithVersionId>> getParam2valueAndVersion() {
        return Collections.unmodifiableMap(param2valueAndVersion);
    }

    public String getActualParamVaue(String paramName) {
        final List<ParamValueWithVersionId> values = param2valueAndVersion.get(paramName);
        if (values == null) {
            return null;
        }
        for (ParamValueWithVersionId paramValueWithVersionId : values) {
            if (paramValueWithVersionId.isActual()) {
                return paramValueWithVersionId.getValue();
            }
        }
        return null;
    }

    public Structure(String name) {
        this.name = name;
        param2valueAndVersion = Maps.newHashMap();
        paramRating = Maps.newHashMap();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Structure)) return false;

        Structure structure = (Structure) o;

        if (id != null ? !id.equals(structure.id) : structure.id != null) return false;
        if (name != null ? !name.equals(structure.name) : structure.name != null) return false;

        return true;
    }

    public void addParam(String names, ParamValueWithVersionId newValue) {
        ArrayList<ParamValueWithVersionId> values = param2valueAndVersion.get(names);
        if (values == null) {
            values = Lists.newArrayList();
            param2valueAndVersion.put(names, values);
        }
        if (newValue.isActual()) {
            for (ParamValueWithVersionId value : values) {
                value.setActual(false);
            }
        }
        values.add(newValue);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 43 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public Map<String, Pair<Integer, Integer>> getParamRating() {
        return paramRating;
    }

    public void addParamRating(String param) {
        this.paramRating.put(param, Pair.of(0, 0));
    }

    public void increaseParamRating(String param) {
        Pair<Integer, Integer> rating = this.paramRating.get(param);
        Pair<Integer, Integer> newRating = Pair.of(rating.getKey() + 1, rating.getValue());
        this.paramRating.remove(param);
        this.paramRating.put(param, newRating);
    }

    public void decreaseParamRating(String param) {
        Pair<Integer, Integer> rating = this.paramRating.get(param);
        Pair<Integer, Integer> newRating = Pair.of(rating.getKey(), rating.getValue() + 1);
        this.paramRating.remove(param);
        this.paramRating.put(param, newRating);
    }
}
