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
 * Date: 14-Nov-2010
 * Time: 12:59:50
 */
package loader;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class TimeInterceptor {
    private final static Logger LOG = Logger.getLogger("TI");

    @AroundInvoke 
    public Object profile(InvocationContext invocation) throws Exception {
        long startTime = System.nanoTime();
        try {
            return invocation.proceed();
        } finally {
            double endTime = (System.nanoTime() - startTime) / 1000000d /* ms/ns */;
            if (LOG.isLoggable(Level.FINE)) {
                LOG.fine("TI - Method " + invocation.getMethod() + " took " + endTime + " (ms)");
            }
        }
    }
}
