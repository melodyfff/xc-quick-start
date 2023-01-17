package com.xinchen.project.core.orm.jpa.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * // Hibernate主键策略生成
 * public DefaultIdentifierGeneratorFactory() {
 *         register( "uuid2", UUIDGenerator.class );
 *         register( "guid", GUIDGenerator.class );            // can be done with UUIDGenerator + strategy
 *         register( "uuid", UUIDHexGenerator.class );         // "deprecated" for new use
 *         register( "uuid.hex", UUIDHexGenerator.class );     // uuid.hex is deprecated
 *         register( "assigned", Assigned.class );
 *         register( "identity", IdentityGenerator.class );
 *         register( "select", SelectGenerator.class );
 *         register( "sequence", SequenceStyleGenerator.class );
 *         register( "seqhilo", SequenceHiLoGenerator.class );
 *         register( "increment", IncrementGenerator.class );
 *         register( "foreign", ForeignGenerator.class );
 *         register( "sequence-identity", SequenceIdentityGenerator.class );
 *         register( "enhanced-sequence", SequenceStyleGenerator.class );
 *         register( "enhanced-table", TableGenerator.class );
 *     }
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/1/17 21:33
 */
@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {

  @Id
  @GenericGenerator(name = "IDProvider", strategy = "com.xinchen.project.core.orm.jpa.IDProvider" )
  @GeneratedValue(generator = "IDProvider")
  protected Long id;
}
