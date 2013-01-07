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
 * Time: 11:47:22 PM
 *
 */
package data.persist;

import javax.persistence.*;
import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;

/**
 * TODO Add comments (class description)
 *
 * @author Baboune
 * @since 14-May-2009
 */
@Entity
@NamedQueries(
        {
                @NamedQuery(
                        name = "BigCompany.findByName",
                        query = "SELECT c FROM BigCompany c WHERE c.name = :name"

                )

        }
)
@Table(name = "BIGCOMPANY",
        uniqueConstraints=@UniqueConstraint(columnNames="NAME"))
public class BigCompany implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;
    private String name = null;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company", fetch = FetchType.EAGER)
    private Set<SmallEmployee> employees = new HashSet<SmallEmployee>();

    @Version
    private int version;


    public BigCompany() {

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

    public Set<SmallEmployee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<SmallEmployee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "BigCompany{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", employees=" + employees +
                '}';
    }
}
