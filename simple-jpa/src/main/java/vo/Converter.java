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
 * Time: 17:07
 */
package vo;


import persist.BigCompany;
import persist.SmallEmployee;

/**
 *
 */
public final class Converter {

    public static CompanyVo toCompanyVo(BigCompany bc) {
        if( bc == null){
            return null;
        }
        CompanyVo vo = new CompanyVo();
        vo.id = bc.getId();
        vo.name = bc.getName();

        if(bc.getEmployees() != null) {
            for(SmallEmployee se : bc.getEmployees()){
                EmployeeVo evo = new EmployeeVo();
                evo.id = se.getId();
                evo.name = se.getName();
                vo.employees.add((evo));
            }
        }
        return vo;
    }
}
