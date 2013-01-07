/**
 /**
 * Copyright (c) Ericsson AB, 2007.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 *
 * ERICSSON MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. ERICSSON SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *
 * <p/>
 * User: Baboune
 * Date: 14-May-2009
 * Time: 11:49:07 PM
 *
 */
package data.persist;


import javax.persistence.*;
import java.io.Serializable;

/**
 * TODO Add comments (class description)
 *
 * @author Baboune
 * @since 14-May-2009
 */
@Entity
@Table(name="SMALLEMPLOYEE")
public class SmallEmployee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;
    private String name = null;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMP_ID", nullable = false)
    //@ForeignKey
    private BigCompany company = null;

    @Version
    private int version;

    public SmallEmployee() {
        
    }

    public BigCompany getCompany() {
        return company;
    }

    public void setCompany(BigCompany company) {
        this.company = company;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        if (o == null || getClass() != o.getClass()) return false;

        SmallEmployee employee = (SmallEmployee) o;

        return !(name != null ? !name.equals(employee.name) : employee.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }


    @Override
    public String toString() {
        return "SmallEmployee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
