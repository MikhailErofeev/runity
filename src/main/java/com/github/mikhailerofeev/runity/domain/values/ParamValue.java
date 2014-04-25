package com.github.mikhailerofeev.runity.domain.values;


/**
 * @author m-erofeev
 * @since 25.04.14
 */
public class ParamValue {
  private String dataId;
  private String value;

  ParamValue( String dataId,  String value) {
    this.dataId = dataId;
    this.value = value;
  }

  
  public String getDataId() {
    return dataId;
  }

  public void setDataId( String dataId) {
    this.dataId = dataId;
  }

  
  public String getValue() {
    return value;
  }

  public void setValue( String value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ParamValue)) return false;

    ParamValue that = (ParamValue) o;

    if (!dataId.equals(that.dataId)) return false;
    if (!value.equals(that.value)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = dataId.hashCode();
    result = 31 * result + value.hashCode();
    return result;
  }
}
