package org.cnd.models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.Assert;

/**
 * Base class that contains the basic attributes to be inherited
 * 
 * @author JParra
 */
public class Base implements Serializable {

	/** serial version UID */
	private static final long serialVersionUID = -6813624487023861708L;
	/** attributes */
	@Id
	private String id;

	@CreatedDate
	private Date created;

	@LastModifiedDate
	private Date modified;

	/** constructor */
	public Base() {
		this(UUID.randomUUID());
	}

	public Base(UUID guid) {
		Assert.notNull(guid, "UUID is required");
		id = guid.toString();
	}

	public Base(String guid) {
		Assert.notNull(guid, "UUID is required");
		id = guid;
	}

	/** methods */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Override
	public String toString() {
		return "Base [id=" + id + ", created=" + created + ", modified=" + modified + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Base other = (Base) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
