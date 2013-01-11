/**
 * Copyright (c) Nicolas Seyvet, 2010.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 *
 * Nicolas Seyvet MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. ERICSSON SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *
 * User: Baboune
 * Date: 05/03/11
 * Time: 14:27
 */
package loader.vo;

import data.persist.Company;
import data.persist.Employee;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class CompanyVo {
    public Long id = null;
    public String name = null;

    public Set<EmployeeVo> employees = new HashSet<EmployeeVo>();

    public CompanyVo() {
    }


    public static CompanyVo toCompanyVo(Company bc) {
            if( bc == null){
                return null;
            }
            CompanyVo vo = new CompanyVo();
            vo.id = bc.getId();
            vo.name = bc.getName();

            if(bc.getEmployees() != null) {
                for(Employee se : bc.getEmployees()){
                    EmployeeVo evo = new EmployeeVo();
                    evo.id = se.getId();
                    evo.name = se.getName();
                    vo.employees.add((evo));
                }
            }
            return vo;
        }
}
