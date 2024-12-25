package ma.project.polynome.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
public class Role {
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
private Long id;

@Enumerated(EnumType.STRING)
	@Column(length = 20)
private ERole name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ERole getName() {
		return name;
	}

	public void setName(ERole name) {
		this.name = name;
	}
}
