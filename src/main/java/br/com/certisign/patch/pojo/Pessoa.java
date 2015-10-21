package br.com.certisign.patch.pojo;

import lombok.Data;
import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity( name = "Pessoa" )
@Table( name = "tbl_pessoa" )
public class Pessoa {

    @Id
    @Column( name = "id" )
    @GeneratedValue
    private Long id;

    @Index( name = "index_created_at" )
    @Column( name = "created_at", nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private Date createdAt;

    @Column( name = "idade", nullable = false )
    private Long idade;

    @Column( name = "nome", length = 255, nullable = false )
    private String nome;
}
