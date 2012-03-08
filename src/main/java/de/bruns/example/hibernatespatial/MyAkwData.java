package de.bruns.example.hibernatespatial;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import com.vividsolutions.jts.geom.Point;

@Entity
@Table(name = "MYAKWS")
public class MyAkwData {

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "DESCRIPTION", length=100)
	private String description;

	@Column(name = "LOCATION")
	@Type(type = "org.hibernatespatial.GeometryUserType")
	private Point location;

	public MyAkwData() {
	}

	public Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	public Point getLocation() {
		return location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String value) {
		this.description = value;
	}

	public void setLocation(Point location) {
		this.location = location;
	}
}
