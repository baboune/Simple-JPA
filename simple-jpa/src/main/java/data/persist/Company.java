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
 * A JPA entity representing a Company.
 *
 * It is composed of a name, and a list of employees {@link Employee}.  The One To Many employee(s) relationship
 * is represented in this example as a bi-directional mapping (as indicated by the "mappedBy" attribute of
 * the "@OneToMany" annotation.
 *
 * @see Employee
 *
 * @author Baboune
 * @since 14-May-2009
 */
@Entity
@NamedQueries(
        {
                @NamedQuery(
                        name = "BigCompany.findByName",
                        query = "SELECT c FROM Company c WHERE c.name = :name"

                )

        }
)
@Table(name = "COMPANY",
        uniqueConstraints=@UniqueConstraint(columnNames="NAME"))
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;
    private String name = null;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company", fetch = FetchType.EAGER)
    private Set<Employee> employees = new HashSet<Employee>();

    @Version
    private int version;

    public Company() {
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

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", employees=" + employees +
                ", version=" + version +
                '}';
    }
}
