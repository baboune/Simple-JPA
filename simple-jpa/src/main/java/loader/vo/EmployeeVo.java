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
 * Time: 14:28
 */
package loader.vo;

/**
 *
 */
public class EmployeeVo {
    public Long id = null;
    public String name = null;

    public EmployeeVo() {

    }
    @Override
    public String toString() {
        return "EmployeeVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
