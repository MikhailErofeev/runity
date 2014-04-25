package com.github.mikhailerofeev.runity.domain.values;


/**
 * @author m-erofeev
 * @since 25.04.14
 */
public class ParamValueWithVersionId {
  private String versionId;
  private String value;
  private boolean actual;

  public ParamValueWithVersionId(String versionId, String value, boolean actual) {
    this.versionId = versionId;
    this.value = value;
    this.actual = actual;
  }

  public boolean isActual() {
    return actual;
  }

  public void setActual(boolean actual) {
    this.actual = actual;
  }

  public String getVersionId() {
    return versionId;
  }

  public void setVersionId(String versionId) {
    this.versionId = versionId;
  }


  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ParamValueWithVersionId)) return false;

    ParamValueWithVersionId that = (ParamValueWithVersionId) o;

    if (!versionId.equals(that.versionId)) return false;
    if (!value.equals(that.value)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = versionId.hashCode();
    result = 31 * result + value.hashCode();
    return result;
  }
}
