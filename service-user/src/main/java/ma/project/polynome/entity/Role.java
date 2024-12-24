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
}
