package com.github.mikhailerofeev.runity.domain.entities;

import org.springframework.data.annotation.Id;

/**
 * @author m-erofeev
 * @since 26.04.14
 */
public class Structure {

  @Id
  private String id;

  private String name;

  Structure() {
  }

  public Structure(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setId(String id) {
    this.id = id;
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

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }
}
