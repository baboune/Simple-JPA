Simple-JPA
==========

A simple Java Persistence API (JPA 1.x and 2.x) maven project to prototype things.  To build: 

    $ mvn install

This will generate a jar in the target directory "simple-JPA-0.1-SNAPSHOT.jar" which can be deployed in
any JavaEE application server.

By default, the simple jar will attempt to create two tables COMPANY, and EMPLOYEE at deployment time.
The persistence.xml file points to a JBOSS definition using datanucleus as a provider, and the JTA datasource looked up is:

    java:jboss/datasources/ExampleDS  

(Which is the default JBoss datasource using H2).

A second datasource is specified in the persistence.xml as a non JTA datasource, this removes warnings in DataNucleus when the database schema is created. In the specification/theory, any change to a schema should be made outside the scope of JTA, most persistence providers (OpenJPA/Hibernate) do not check for this. 

The non JTA datasource is:

    java:jboss/datasources/ExampleDS-NonJTA  

To add this datasource and enable it:

    $JBOSS_HOME/bin/jboss-cli.sh -c command="/subsystem=datasources/data-source=ExampleNONJTADS:add(jndi-name=\"java:jboss/datasources/ExampleNONJTADS\", use-java-context=true, driver-name=h2, connection-url=jdbc:h2:mem:test;DB_CLOSE_DELAY=-1, user-name=sa, password=sa, jta=false)"
    $JBOSS_HOME/bin/jboss-cli.sh -c command="/subsystem=datasources/data-source=ExampleNONJTADS:enable"

$JBOSS_HOME must point to the location where JBOSS is installed.

--------------

Here are some other possible persistence.xml using the same project and a different JPA provider (Hibernate, EclipseLink 
and OpenJPA) using MySQL and a data source located with a jndi lookup of "jdbc/bm":

    <persistence-unit name="BM" transaction-type="JTA">
        <provider>org.hibernate.ejb.HibernatePersistence</provider>
        <jta-data-source>jdbc/bm</jta-data-source>  <!-- This data source jndi must be defined in the JavaEE AS -->
        <class>data.persist.BigCompany</class>
        <class>pdata.persist.SmallEmployee</class>
        <properties>
            <property name="hibernate.dialect" value="org.hibernate.dialect.MySQLDialect"/>
            <property name="hibernate.hbm2ddl.auto" value="update"/>            
        </properties>
    </persistence-unit>


    <!-- Eclipse Link -->
    <persistence-unit name="BM" transaction-type="JTA">
        <provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
        <jta-data-source>jdbc/bm</jta-data-source> <!-- This data source jndi must be defined in the JavaEE AS -->
        <class>data.persist.BigCompany</class>
        <class>data.persist.Employee</class>
        <properties>
            <property name="eclipselink.ddl-generation" value="create-tables"/>
            <property name="eclipselink.ddl-generation.output-mode" value="database"/>
            <property name="eclipselink.weaving" value="static" />
            <property name="eclipselink.cache.type.BigCompany" value="SoftWeak"/>
            <property name="eclipselink.cache.type.SmallEmployee" value="SoftWeak"/>
            <property name="eclipselink.cache.size.default" value="5000"/>
        </properties>
    </persistence-unit>

    <!-- OpenJPA -->
    <persistence-unit name="BM" transaction-type="JTA">
        <provider>org.apache.openjpa.persistence.PersistenceProviderImpl</provider>
        <jta-data-source>jdbc/bm</jta-data-source> <!-- This data source jndi must be defined in the JavaEE AS -->
        <class>data.persist.BigCompany</class>
        <class>data.persist.Employee</class>
        <properties>
            <property name="openjpa.Log" value="SQL=TRACE"/>
            <property name="openjpa.jdbc.DBDictionary" value="mysql(DriverVendor=mysql)"/>
            <property name="openjpa.jdbc.MappingDefaults"
                      value="ForeignKeyDeleteAction=restrict, JoinForeignKeyDeleteAction=restrict"/>            
        </properties>
    </persistence-unit>
